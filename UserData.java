import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

public class UserData implements Serializable {
     private String username;
     private boolean isOnline;
     private HashMap<String,byte[]>sharedplaylistfiles;
     private ArrayList<String>namesOfsongsOfSharedPlaylist;
     private transient JPotifyGuiLogic jPotifyGuiLogic;
    private static final Long SerialVersionUID=27L;

    public UserData(JPotifyGuiLogic jPotifyGuiLogic){
        this.jPotifyGuiLogic=jPotifyGuiLogic;
        sharedplaylistfiles=new HashMap<>();
        namesOfsongsOfSharedPlaylist=new ArrayList<>();
        username=jPotifyGuiLogic.getUsername();
        isOnline=true;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getIsOnline(){
        return isOnline;
    }

    public void setIsOnline(boolean isOnline){
        this.isOnline=isOnline;
    }

    public void bytifySharedPlaylist(){
        try {
            ArrayList<Song>sharedPlaylistSongs=jPotifyGuiLogic.getSongsOfAPlaylist("sharedPlaylist");
            FileInputStream testfile=new FileInputStream("test.mp3");
            byte []testbyte= testfile.readAllBytes();
            sharedplaylistfiles.put("test",testbyte);
            namesOfsongsOfSharedPlaylist.add("test");
            for(int i=0;i<sharedPlaylistSongs.size();i++){
                Song song=sharedPlaylistSongs.get(i);
                FileInputStream fileInputStream=new FileInputStream(song.getFileAddress());
                byte []filebyte= fileInputStream.readAllBytes();
                sharedplaylistfiles.put(song.getSong_name(),filebyte);
                namesOfsongsOfSharedPlaylist.add(song.getSong_name());

            }
        }catch (IOException e){
            System.out.println("execption aquird in bytifysharedplaylist: "+e);
        }

    }

    public ArrayList<String> getNamesOfsongsOfSharedPlaylist() {
        return namesOfsongsOfSharedPlaylist;
    }

    public HashMap<String, byte[]> getSharedplaylistfiles() {
        return sharedplaylistfiles;
    }
}
