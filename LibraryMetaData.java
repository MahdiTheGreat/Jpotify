import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.Serializable;

public class LibraryMetaData extends JPanel {

    private JPotifyGuiLogic jPotifyGuiLogic;


    public  LibraryMetaData(JPotifyGuiLogic jPotifyGuiLogic){
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.jPotifyGuiLogic=jPotifyGuiLogic;
        this.validate();
        jPotifyGuiLogic.getjFrame().revalidate();

    }

    public void resetMetadataInLibrary(){

        this.removeAll();
        this.repaint();
        JLabel jLabel=new JLabel(new ImageIcon("C:\\JPotify\\src\\songArtwork\\Blank.jpg"));
        jLabel.setPreferredSize(new Dimension(100,100));
        this.add(jLabel);
        this.add(new JLabel("artist:"));
        this.add(new JLabel("song:"));
        this.add(new JLabel("album:"));
        this.revalidate();
        jPotifyGuiLogic.getjFrame().revalidate();

    }

    public void addMetaDataToLibrary(Song song) {
        this.removeAll();
        this.repaint();
        JLabel jlabel=new JLabel(jPotifyGuiLogic.getSongImageIcon(song));
        jlabel.setPreferredSize(new Dimension(100,100));
        this.add(jlabel);
        this.add(new JLabel("artist:" + song.getArtist()));
        this.add(new JLabel("song:" + song.getSong_name()));
        this.add(new JLabel("album:" + song.getAlbum_name()));
        this.revalidate();
        jPotifyGuiLogic.getjFrame().revalidate();

    }


    }


