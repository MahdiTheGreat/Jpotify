import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class RenamePlaylist extends JFrame  {

    public RenamePlaylist(){}

    public String renamePlaylist(){
        String newplaylistname=JOptionPane.showInputDialog(this,"please enter the new name for the playlist.");
        this.dispose();
        return newplaylistname;
    }


}
