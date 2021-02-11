import javax.swing.*;
import java.io.Serializable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;

public class AddSongToAPlaylist extends JFrame {

    public AddSongToAPlaylist(){}

        public String playlistNameToAddTheSongTo(){
            String nameOfthePlaylistToAdd=JOptionPane.showInputDialog(this,"please enter the name of the playlist .");
            this.dispose();
            return nameOfthePlaylistToAdd;
        }


}
