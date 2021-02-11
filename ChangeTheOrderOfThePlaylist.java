import javax.swing.*;
import java.io.Serializable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;

public class ChangeTheOrderOfThePlaylist extends JFrame  {

    public ChangeTheOrderOfThePlaylist(){}

    public String ChangeTheOrderOfAsong(){

        String futureindexString=JOptionPane.showInputDialog(this,"which place to move the song to?(enter number between 1 and maximum place)");
        this.dispose();
        return futureindexString;
    }


}
