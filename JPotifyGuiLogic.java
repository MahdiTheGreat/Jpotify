import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class JPotifyGuiLogic implements Serializable {

    private HashMap<String,Song>defaultPlaylist;
    private ArrayList<String>playlist_names;
    private ArrayList<Song>defaultPlaylist_songs;
    private HashMap<String,HashMap<String,Song>>playlists;
    private transient Library library;
    private transient CentralScreen centralScreen;
    private ArrayList<String>albums_names;
    private HashMap<String,HashMap<String,Song>>albums;
    private transient JFrame jFrame;
    private transient Mp3PlayerMultiTools mp3PlayerMultiTools;
    private HashMap<String,ArrayList<Song>>playlistsSongOrder;
    private String username;
    private int port;
    private FriendsData friendsData;
    private JLabel usernameLabel;
    private static final Long SerialVersionUID=25L;

    public JPotifyGuiLogic(/*JFrame jframe*/){

        usernameLabel=new JLabel();
        playlistsSongOrder=new HashMap<>();
        defaultPlaylist=new HashMap<>();
        playlists=new HashMap<>();
        playlist_names=new ArrayList<>();
        albums_names=new ArrayList<>();
        albums=new HashMap<>();
        defaultPlaylist_songs=new ArrayList<>();
        //addPlaylist("favorites");
        //this.jFrame=jframe;
        addPlaylist("favorites");
        addPlaylist("sharedPlaylist");

    }

    public void addPlaylist(String playlist_name){
        if(!playlists.containsKey(playlist_name)) {
            HashMap<String, Song> playlist = new HashMap<>();
            playlists.put(playlist_name, playlist);
            playlist_names.add(playlist_name);
            playlistsSongOrder.put(playlist_name,new ArrayList<>());
        }
        if(library!=null && jFrame!=null)library.refresh();


    }

    public void renamePlaylist(String newplaylistname){

        if(centralScreen.getCentalScreenmode()[0].equals("playlists")) {
            HashMap<String, Song> playlist = playlists.get(centralScreen.getCentalScreenmode()[1]);

            playlists.remove(centralScreen.getCentalScreenmode()[1]);
            playlists.put(newplaylistname,playlist);
            playlist_names.add(playlist_names.indexOf(centralScreen.getCentalScreenmode()[1]),newplaylistname);
            ArrayList<Song>songofoldplaylist=playlistsSongOrder.get(centralScreen.getCentalScreenmode()[1]);
            playlistsSongOrder.remove(centralScreen.getCentalScreenmode()[1]);
            playlistsSongOrder.put(newplaylistname,songofoldplaylist);
            library.renamePlaylist(centralScreen.getCentalScreenmode()[1],newplaylistname);
            centralScreen.playlist_was_deleted();

        }
        library.renamePlaylist(centralScreen.getCentalScreenmode()[0],newplaylistname);
    }

    public void deletePlaylist(String playlist_name){

        if(playlists.containsKey(playlist_name)){

            playlists.remove(playlist_name);
            playlist_names.remove(playlist_name);
            playlistsSongOrder.remove(playlist_name);
            library.deletePlaylist(playlist_name);

        }
        library.refresh();

    }

    public void addSongToPlaylist(String playlistName,Song song){

        HashMap<String,Song>playlist=playlists.get(playlistName);
        playlist.put(song.getSong_name(),song);
        ArrayList<Song>playlistSongorder=playlistsSongOrder.get(playlistName);
        playlistSongorder.add(song);
        if(centralScreen.getCentalScreenmode()[0].equals("playlists")){
            centralScreen.songs_Button_action(getSongsOfAPlaylist(centralScreen.getCentalScreenmode()[1]));
        }

    }

    public void removeSongfromPlaylist(String playlistName,Song song){

        HashMap<String,Song>playlist=playlists.get(playlistName);
        if(playlist.containsKey(song.getSong_name())){
            playlist.remove(song.getSong_name());
        }

        ArrayList<Song>playlistSongorder=playlistsSongOrder.get(playlistName);
        if(playlistSongorder.contains(song)) {
            playlistSongorder.remove(song);
        }

        if(centralScreen.getCentalScreenmode()[0].equals("playlists")){
            centralScreen.songs_Button_action(getSongsOfAPlaylist(centralScreen.getCentalScreenmode()[1]));
        }

    }

    /*public void changeTheOrderOfASong(int currentSongIndex,int futureSongIndex ,String playlistName )  {
        ArrayList<Song>playlistSongorder=playlistsSongOrder.get(playlistName);
        Song songOrderTemp=playlistSongorder.get(currentSongIndex);
        playlistSongorder.remove(currentSongIndex);
        playlistSongorder.add(futureSongIndex,songOrderTemp);

    }
    */

    public ArrayList<Song> getSongsOfAPlaylist(String playlistName){
    return playlistsSongOrder.get(playlistName);
    }

    public HashMap<String, HashMap<String, Song>> getPlaylists() {
        return playlists;
    }

    public ArrayList<String> playlistsOfSong(Song song){
        ArrayList<String>playlists_of_song=new ArrayList<>();
        for(int i=0;i<playlist_names.size();i++){
            if(playlists.containsKey(playlist_names.get(i))){
                ArrayList<Song>songs_of_playlist=getSongsOfAPlaylist(playlist_names.get(i));
                if(songs_of_playlist.contains(song))playlists_of_song.add(playlist_names.get(i));
            }
        }
        return playlists_of_song;
    }

    public HashMap<String, Song> getDefaultPlaylist() {
        return defaultPlaylist;
    }


    public void addSong(Song song){
        defaultPlaylist.put(song.getSong_name(),song);
        defaultPlaylist_songs.add(song);
        //addAlbum(song.getAlbum_name());
        makeAlbumAndaddSongToAlbum(song);

    }

    public ArrayList<Song> getDefaultPlaylist_songs() {
        return defaultPlaylist_songs;
    }

    private void refreshSongs(Song song){
        defaultPlaylist_songs.remove(song);
        defaultPlaylist_songs.add(0,song);
        centralScreen.refresh();
        }

        private void refreshPlaylist(String playlistname){
        playlist_names.remove(playlistname);
        playlist_names.add(0,playlistname);
        centralScreen.refresh();
    }

    public void refreshAlbums(String albumName){
        albums_names.remove(albumName);
        albums_names.add(0,albumName);
        centralScreen.refresh();
    }

    public void refresh(Song song){

        System.out.println("the song: " + song.getSong_name() + " is playing");
        refreshSongs(song);
        refreshAlbums(song.getAlbum_name());
        ArrayList<String>playlists_of_song=playlistsOfSong(song);
        for (int i = playlists_of_song.size()-1; i >= 0; i--) {

            if(playlists.containsKey(playlists_of_song.get(i))) {
                refreshPlaylist(playlists_of_song.get(i));
            }

        }

    }

    public void setCentralScreen(CentralScreen centralScreen) {
        this.centralScreen = centralScreen;
    }

    public CentralScreen getCentralScreen() {
        return centralScreen;
    }

    public void setLibrary(Library library) {
        this.library = library;
        }

    public Library getLibrary() {
        return library;
    }

    public HashMap<String, HashMap<String, Song>> getAlbums() {
        return albums;
    }

    /*public void addAlbum(String albumName){
        if(!albums_names.contains(albumName)){
            albums.put(albumName,new HashMap<>());
            albums_names.add(albumName);
        }

    }*/

    public void makeAlbumAndaddSongToAlbum(Song song){

        if(albums.containsKey(song.getAlbum_name())){
            HashMap<String,Song>album=albums.get(song.getAlbum_name());
            album.put(song.getSong_name(),song);
        }
        else {
            HashMap<String, Song> album = new HashMap<>();
            albums_names.add(song.getAlbum_name());
            album.put(song.getSong_name(), song);
            albums.put(song.getAlbum_name(), album);
        }

    }

    public ArrayList<String> getAlbums_names() {
        return albums_names;
    }

    private int randomNumberInRange(int max,int min){
        if(min==max && min==0)return 0;
        else return (int)((Math.random()*((max-min)+1))+min);

    }

    public ImageIcon getSongImageIcon(Song song){
        return new ImageIcon("C:\\JPotify\\src\\songArtwork\\"+song.getSong_name()+".jpg");
    }

    public ArrayList<Song> getSongsOfAlbum(String album_name){

        ArrayList<Song>album_songs=new ArrayList<>();

        for(int k=0;k<defaultPlaylist_songs.size();k++){

            if(defaultPlaylist_songs.get(k).getAlbum_name().equals(album_name))album_songs.add(defaultPlaylist_songs.get(k));

        }
        return album_songs;
    }

    public ImageIcon getRandomImageIconforAlbum(String album){

        ArrayList<Song>album_songs=getSongsOfAlbum(album);
        int random=randomNumberInRange(album_songs.size()-1,0);
        return getSongImageIcon(album_songs.get(random));

    }

    public JFrame getjFrame() {
        return jFrame;
    }

    public void setMp3PlayerMultiTools(Mp3PlayerMultiTools mp3PlayerMultiTools) {
        this.mp3PlayerMultiTools = mp3PlayerMultiTools;
    }

    public Mp3PlayerMultiTools getMp3PlayerMultiTools() {
        return mp3PlayerMultiTools;
    }

    private void moveAnElementThroughArray(int currentindex,int futureindex,String playlistName){

        ArrayList<Song>array=playlistsSongOrder.get(playlistName);
        Song temp=array.get(currentindex);
        if(futureindex>currentindex && futureindex<array.size()){
                while(!array.get(futureindex).equals(temp)){
                    if(currentindex+1<array.size()) {
                        Collections.swap(array,currentindex,currentindex+1);
                        currentindex++;
                    }
                }
            }
            if(futureindex<currentindex && futureindex>=0){
                while(array.get(futureindex)!=temp){
                    if(currentindex-1>=0) {
                        Collections.swap(array,currentindex,currentindex-1);
                        currentindex--;
                    }
                }
            }

        }

        public void ChangeTheOrder(String futureIndex,Song song) {
            int funtureIndexNumber;
            if (centralScreen.getCentalScreenmode()[0].equals("playlists")) {
                String playlistname = centralScreen.getCentalScreenmode()[1];
                ArrayList<Song> playlistSongs = playlistsSongOrder.get(playlistname);
                int currentindex = playlistSongs.indexOf(song);
                try {
                    funtureIndexNumber = Integer.parseInt(futureIndex)-1;
                    moveAnElementThroughArray(currentindex, funtureIndexNumber, playlistname);
                } catch (NumberFormatException e) {
                    System.out.println("an exeception aquired in changeTheOrder :" + e);
                }
                if(centralScreen.getCentalScreenmode()[0].equals("playlists")){
                    centralScreen.songs_Button_action(getSongsOfAPlaylist(centralScreen.getCentalScreenmode()[1]));
                }

            }
        }

    public void setjFrame(JFrame jFrame) {
        this.jFrame = jFrame;
        jFrame.add(usernameLabel,BorderLayout.NORTH);
        jFrame.revalidate();
    }

    public void setUsername(String username) {
        this.username = username;
        usernameLabel.setText(username);
        usernameLabel.revalidate();
        jFrame.revalidate();
    }

    public String getUsername() {
        return username;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setFriendsData(FriendsData friendsData) {
        this.friendsData = friendsData;
    }

    public FriendsData getFriendsData() {
        return friendsData;
    }

    public JLabel getUsernameLabel(){
        return usernameLabel;
    }

    /*public ArrayList<Song>getSongsOfplaylist(HashMap<String,Song>playlist){

        ArrayList<Song>playlistsongs=new ArrayList<>();

        for(int k=0;k<defaultPlaylist_songs.size();k++){

            if(playlist.containsKey(defaultPlaylist_songs.get(k).getSong_name()))playlistsongs.add(defaultPlaylist_songs.get(k));

        }
        return playlistsongs;

    }*/


}





