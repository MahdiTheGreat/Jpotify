import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;

public class Playlist_name extends JFrame  {

    public Playlist_name(){}

    public String playlistName(){
        String playListName=JOptionPane.showInputDialog(this,"please enter the name");
        this.dispose();
        return playListName;
        }

}
