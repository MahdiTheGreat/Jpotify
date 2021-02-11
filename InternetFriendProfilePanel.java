import javax.swing.*;
import java.awt.*;

public class InternetFriendProfilePanel extends JPanel {
    private JLabel username;
    private JLabel status;
    private JLabel lastSongListnedto;
    private JLabel lastActive;
    private JButton downloadSharedPlaylist;
    private AddANewFriend addANewFriend;
    private JPotifyGuiLogic jPotifyGuiLogic;

    public InternetFriendProfilePanel(JPotifyGuiLogic jPotifyGuiLogic){
        this.jPotifyGuiLogic=jPotifyGuiLogic;
        downloadSharedPlaylist=new JButton("shared Playlist");
        this.setLayout(new BorderLayout(1,1));
        username=new JLabel("unknown");
        status=new JLabel("offline");
        lastSongListnedto=new JLabel("unknown");
        lastActive=new JLabel("unknown");
        this.add(username,BorderLayout.CENTER);
        this.add(status,BorderLayout.EAST);
        this.add(lastSongListnedto,BorderLayout.NORTH);
        this.add(lastActive,BorderLayout.SOUTH);
        this.add(downloadSharedPlaylist,BorderLayout.WEST);
        this.revalidate();
        this.setVisible(true);
        jPotifyGuiLogic.getjFrame().revalidate();

    }

    public void setUsername(String username) {
       this.username.setText(username);
    }

    public void setStatus(String status){
        this.status.setText(status);
        this.status.revalidate();
        jPotifyGuiLogic.getjFrame().revalidate();
    }

    public void setLastActive(String lastActive){
        this.lastActive.setText(lastActive);
        this.lastActive.revalidate();
        jPotifyGuiLogic.getjFrame().revalidate();
    }


    public void setLastSongListnedto(String lastSongListnedto){

        this.lastSongListnedto.setText(lastSongListnedto);
        this.lastSongListnedto.revalidate();
        jPotifyGuiLogic.getjFrame().revalidate();
    }
}
