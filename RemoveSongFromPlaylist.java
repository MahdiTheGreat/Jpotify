import javax.swing.*;
import java.io.Serializable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;

public class RemoveSongFromPlaylist extends JFrame  {

    public RemoveSongFromPlaylist(){}

    public String playlistNameToRemoveTheSongFrom(){
        String nameOfthePlaylistToAdd=JOptionPane.showInputDialog(this,"please enter the name of the playlist you want to remove this song from.");
        this.dispose();
        return nameOfthePlaylistToAdd;
    }


}