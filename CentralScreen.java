import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.WrappedPlainView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/*class Instancevariable{
    String album_name;
    ArrayList<Song>album_songs;
}*/

public class CentralScreen extends JPanel {

    //private Instancevariable instancevariable;
    //private ArrayList<Song>album_songs;//this is for the album buttons
    //private String album_name;//this is for album buttons
    private JPotifyGuiLogic jPotifyGuiLogic;
    private String[]centalScreenmode;
    private RenamePlaylist renamePlaylist;
    private JScrollPane jScrollPane;


    public CentralScreen(JPotifyGuiLogic jPotifyGuiLogic){
        //instancevariable=new Instancevariable();
        renamePlaylist=new RenamePlaylist();
        //FlowLayout flowLayout=new FlowLayout();
        //this.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.setLayout(new GridLayout(5,0));
        this.jPotifyGuiLogic=jPotifyGuiLogic;
      this.jPotifyGuiLogic.setCentralScreen(this);
      centalScreenmode=new String[2];
      setVisible(true);
      jScrollPane=new JScrollPane(this);
      //this.add(jScrollPane);
     // jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      //jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
      jPotifyGuiLogic.getjFrame().revalidate();

    }

    public void playlist_was_deleted(){
        setScreenMode("playlist was deleted",null);
        this.removeAll();
        this.repaint();
        JLabel deletedMessage=new JLabel("playlist was deleted or renamed.");
        this.add(deletedMessage);
        refresh();


    }

    public void songs_Button_action (){
        this.removeAll();
        this.repaint();
        this.setLayout(new GridLayout(5,0));
        /*jScrollPane=new JScrollPane(this);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        /*DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> jList = new JList<>(listModel);
            JScrollPane jScrollPane1 = new JScrollPane(jList);
            jList.setVisibleRowCount(1);
            jList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            this.add(jList);
            this.add(jScrollPane1);
            */
            for (int i = 0; i < jPotifyGuiLogic.getDefaultPlaylist_songs().size(); i++) {
                Song song = jPotifyGuiLogic.getDefaultPlaylist_songs().get(i);
                JButton songbutton=new JButton(song.getSong_name());
                songbutton.setPreferredSize(new Dimension(150, 150));
                songbutton.setMaximumSize(new Dimension(150,150));
                songbutton.setMinimumSize(new Dimension(150,150));
                songbutton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton button=(JButton)e.getSource();
                        setScreenMode("songs",null);
                        jPotifyGuiLogic.getDefaultPlaylist().get(button.getText()).play();
                    }
                });
                this.add(songbutton);
                }
            /*jList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    String SelectedValue=jList.getSelectedValue();
                    setScreenMode("songs",null);
                    jPotifyGuiLogic.getDefaultPlaylist().get(SelectedValue).play();

                }
                });
                */

        refresh();


    }

    public void songs_Button_action(ArrayList<Song>songs ) {
        this.removeAll();
        this.repaint();
        this.setLayout(new GridLayout(5,0));

        if(centalScreenmode[0].equals("playlists") && !centalScreenmode[1].equals("favorites") && !centalScreenmode[1].equals("sharedPlaylist")){
            JButton deletePlaylist=new JButton("delete playlist");
            JButton renameplalist=new JButton("rename playlist");
            deletePlaylist.setPreferredSize(new Dimension(150, 150));
            deletePlaylist.setMaximumSize(new Dimension(150,150));
            deletePlaylist.setMinimumSize(new Dimension(150,150));
            renameplalist.setPreferredSize(new Dimension(150, 150));
            renameplalist.setMaximumSize(new Dimension(150,150));
            renameplalist.setMinimumSize(new Dimension(150,150));
            //deletePlaylist.setText("delete playlist");
            //renameplalist.setText("rename playlist");
            this.add(deletePlaylist);
            this.add(renameplalist);
            /*listModel.addElement("delete playlist");
            listModel.addElement("rename playlist");*/
            refresh();

        }
        /*DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> jList = new JList<>(listModel);
        JScrollPane jScrollPane = new JScrollPane(jList);
        jList.setVisibleRowCount(1);
        jList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.add(jList);
        this.add(jScrollPane);*/
        for (int i = 0; i < songs.size(); i++) {
            Song song = jPotifyGuiLogic.getDefaultPlaylist_songs().get(i);
            JButton songbutton=new JButton(song.getSong_name());
            songbutton.setPreferredSize(new Dimension(150, 150));
            songbutton.setMaximumSize(new Dimension(150,150));
            songbutton.setMinimumSize(new Dimension(150,150));
            songbutton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton button=(JButton)e.getSource();
                    //String buttonext = button.getText();
                    if (button.getText().equals("delete playlist")) {
                        System.out.println("went in delete playlist");
                        jPotifyGuiLogic.deletePlaylist(centalScreenmode[1]);
                        playlist_was_deleted();
                        refresh();
                    }else if(button.getText().equals("rename playlist")){
                        System.out.println("went in delete playlist");
                        String newplaylistname=renamePlaylist.renamePlaylist();
                        jPotifyGuiLogic.renamePlaylist(newplaylistname);
                        refresh();
                    }else {
                        jPotifyGuiLogic.getDefaultPlaylist().get(button.getText()).play();
                        refresh();
                    }
                }
            });
            this.add(songbutton);

        }
        /*jList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String SelectedValue=jList.getSelectedValue();
                if (SelectedValue.equals("delete playlist")) {
                    jPotifyGuiLogic.deletePlaylist(centalScreenmode[1]);
                    playlist_was_deleted();
                    refresh();
                }
                else if(SelectedValue.equals("rename playlist")){
                    String newplaylistname=renamePlaylist.renamePlaylist();
                    jPotifyGuiLogic.renamePlaylist(newplaylistname);
                    refresh();
                }
                else {
                    jPotifyGuiLogic.getDefaultPlaylist().get(SelectedValue).play();
                    refresh();
                }
                }


        });*/


        refresh();

        }



            public void albums_Button_action () {
                this.removeAll();
                this.repaint();
                this.setLayout(new FlowLayout(FlowLayout.RIGHT));
                /*HashMap<String, HashMap<String, Song>> albums = jPotifyGuiLogic.getAlbums();
                ArrayList<String> albums_names = jPotifyGuiLogic.getAlbums_names();*/
                HashMap<String, Song> album;
                //album_name=null;
                for ( int i = 0; i < jPotifyGuiLogic.getAlbums_names().size(); i++) {
                    if (jPotifyGuiLogic.getAlbums().containsKey(jPotifyGuiLogic.getAlbums_names().get(i))) {
                        String albumName=jPotifyGuiLogic.getAlbums_names().get(i);
                        JButton album_button = new JButton(jPotifyGuiLogic.getRandomImageIconforAlbum(albumName));
                        album_button.setText(albumName);
                        album_button.setPreferredSize(new Dimension(150, 150));
                        album_button.setMaximumSize(new Dimension(150,150));
                        album_button.setMinimumSize(new Dimension(150,150));
                        /*album_button.setHorizontalTextPosition(AbstractButton.CENTER);
                        album_button.setVerticalTextPosition(AbstractButton.BOTTOM);*/
                        System.out.println("the album name before action listeber is "+albumName);
                        album_button.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JButton source=(JButton)e.getSource();
                                setScreenMode("albums",source.getText());
                                System.out.println("the album name in actionlistner is "+source.getText());
                                songs_Button_action(jPotifyGuiLogic.getSongsOfAlbum(source.getText()));
                                refresh();

                            }
                        });
                        this.add(album_button);


                    }
                }

                refresh();


            }

            public void setScreenMode (String one, String two){
                centalScreenmode[0] = one;
                centalScreenmode[1] = two;
                refresh();

            }

            public String[] getCentalScreenmode () {
                return centalScreenmode;
            }

            public void refresh(){
                revalidate();
                jPotifyGuiLogic.getjFrame().revalidate();
            }

}



