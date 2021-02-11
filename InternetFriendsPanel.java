import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InternetFriendsPanel extends JPanel {
    private JButton addfriend;
    private DefaultListModel<InternetFriendProfilePanel>listModel;
    private JList<InternetFriendProfilePanel>jlist;
    private JScrollPane friendScroll;
    private AddANewFriend addANewFriend;
    private JPotifyGuiLogic jPotifyGuiLogic;
    private JButton setData;
    private SetUserData setUserData;
    public InternetFriendsPanel(JPotifyGuiLogic jPotifyGuiLogic){
        setUserData=new SetUserData();
        setData=new JButton("setData");
        setData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[]usernameAndPort=setUserData.setUserData();
                int port=0;
                try {

                    jPotifyGuiLogic.setUsername(usernameAndPort[0]);
                    port=Integer.parseInt(usernameAndPort[1]);
                    jPotifyGuiLogic.setPort(port);

                }catch (NumberFormatException ex){
                    System.out.println("exception in add friend happend and is: "+ex);

                }
            }
        });
        this.jPotifyGuiLogic=jPotifyGuiLogic;
        addANewFriend=new AddANewFriend();
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        addfriend=new JButton("add friend");
        addfriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] portAndHost=addANewFriend.addANewFriend();
                int port=0;
                try {
                    port=Integer.parseInt(portAndHost[0]);
                    jPotifyGuiLogic.getFriendsData().addFriendHostAddress(port,portAndHost[1]);
                }catch (NumberFormatException ex){
                    System.out.println("exception in add friend happend and is: "+ex);

                }

            }
        });
        listModel=new DefaultListModel<>();
        jlist=new JList<>();
        jlist.setVisibleRowCount(1);
        jlist.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        friendScroll = new JScrollPane(jlist);
        this.add(jlist);
        this.add(friendScroll);
        this.add(addfriend);
        this.add(setData);
        jlist.setVisible(true);
        friendScroll.setVisible(true);
        this.setVisible(true);
        jPotifyGuiLogic.getjFrame().revalidate();


    }
    public void setUpfriends(){
        ArrayList<String >usernames=jPotifyGuiLogic.getFriendsData().getUsernames();
        for(int i=0;i<usernames.size();i++){

            InternetFriendProfilePanel internetFriendProfilePanel=new InternetFriendProfilePanel(jPotifyGuiLogic);
            internetFriendProfilePanel.setUsername(usernames.get(i));
            listModel.addElement(internetFriendProfilePanel);

        }
        jPotifyGuiLogic.getjFrame().revalidate();

    }

    public void setSatusofFriend(int port,String status){
        int index=-1;
        for(int i=0;i<jPotifyGuiLogic.getFriendsData().getFriendsPorts().get(i);i++){
            if(jPotifyGuiLogic.getFriendsData().getFriendsPorts().get(i)==port)index=i;
        }
        if(index==-1);
        else listModel.get(index).setStatus(status);


    }

}
