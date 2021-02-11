
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.rmi.UnexpectedException;
import java.util.ArrayList;

public class Song implements Serializable {

    private JPotifyGuiLogic jPotifyGuiLogic;
    private volatile String artist;
    private volatile String song_name;
    private volatile String album_name;
    private volatile String fileAddress;
    private volatile Long songLength;
    private volatile int frames;
    private static ArrayList<String>albums;
    private static final Long SerialVersionUID=25L;
    //private Library library;

    public Song(File song,JPotifyGuiLogic jPotifyGuiLogic){

        Mp3File mp3File=null;
        fileAddress=song.getAbsolutePath();
        this.jPotifyGuiLogic=jPotifyGuiLogic;
        albums=new ArrayList<>();

        try {

            mp3File=new Mp3File(song);
            metaDataRead(mp3File,song);

        }catch (IOException|InvalidDataException|UnsupportedTagException e){
            System.out.println("exception in constructor of song: "+e);
        }

        frames=mp3File.getFrameCount();
        songLength=mp3File.getLengthInSeconds();

        }

        public void metaDataRead(Mp3File mp3,File song){

        FileInputStream in =null;
        int tagByteSize=128;
        byte sig[]=new byte[3];
        byte artist[]=new byte[30];
        byte song_name[]=new byte[30];
        byte album[]=new byte[30];
        int sizeInByte=0;

        if(mp3.hasId3v1Tag()) {

            try {

                in = new FileInputStream(song.getAbsolutePath());
                sizeInByte = in.available();
                in.skip(sizeInByte - tagByteSize);
                in.read(sig);
                in.read(song_name);
                in.read(artist);
                in.read(album);
                in.close();

            } catch (IOException e) {
                System.out.println("exception in metaDataRead: " + e);

            }

            setAlbum_name(new String(album));
            albums.add(album_name);
            setArtist(new String(artist));
            setSong_name(new String(song_name));

        }
        else{

            setAlbum_name(mp3.getId3v2Tag().getAlbum());
            albums.add(album_name);
            setArtist(mp3.getId3v2Tag().getArtist());
            setSong_name(mp3.getId3v2Tag().getTitle());

        }

        makeArtwork(mp3);

    }

    public void makeArtwork(Mp3File mp3) {

        try {
            ByteArrayInputStream artwork=new ByteArrayInputStream(mp3.getId3v2Tag().getAlbumImage());
            BufferedImage image=ImageIO.read(artwork);
            File output_file=new File("C:\\JPotify\\src\\songArtwork\\"+getSong_name()+".jpg");
            System.out.println(getSong_name());
            ImageIO.write(image,"jpg",output_file);

        }catch (IOException e){
            System.out.println("exception in makeArtwork: "+e);
        }


    }

    public String getAlbum_name(){
        return album_name;
    }

    public String getSong_name(){
        return song_name;
    }

    public String getArtist(){
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    /*public void addPlaylist(String playlistName){
        playlists.add(playlistName);
    }

    public ArrayList<String> getPlaylists() {
        return playlists;
    }*/

    public static ArrayList<String> getAlbums() {
        return albums;
    }

    public void play(){
        System.out.println("the song: " + getSong_name() + " is playing");
        jPotifyGuiLogic.getLibrary().getLibraryMetaData().addMetaDataToLibrary(this);
        jPotifyGuiLogic.refresh(this);
        if(jPotifyGuiLogic.getCentralScreen().getCentalScreenmode()[0].equals("songs"))jPotifyGuiLogic.getCentralScreen().songs_Button_action();
        //if(jPotifyGuiLogic.getCentralScreen().getCentalScreenmode()[0].equals("albums"))jPotifyGuiLogic.getCentralScreen().songs_Button_action();
        System.out.println("passed refresh and setup");
        jPotifyGuiLogic.getMp3PlayerMultiTools().play(this);
        }

    public String getFileAddress() {
        return fileAddress;
    }

    public int getFrames() {
        return frames;
    }

    public Long getSongLength() {
        return songLength;
    }

    public JPotifyGuiLogic getjPotifyGuiLogic() {
        return jPotifyGuiLogic;
    }
}





