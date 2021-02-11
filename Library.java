

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Library extends JPanel  {

    private JButton new_playlist;
    private JButton add_to_library;
    private JButton playlists;
    private JButton albums;
    private JLabel library_label;
    private JPotifyGuiLogic jPotifyGuiLogic;
    private JList <String>jList;
    private DefaultListModel<String>listModel;
    private JScrollPane playlists_scroll;
    private LibraryMetaData libraryMetaData;


    public Library(JPotifyGuiLogic jPotifyGuiLogic) {
        jPotifyGuiLogic.setLibrary(this);
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        listModel = new DefaultListModel<>();
        jList = new JList<>(listModel);
        playlists_scroll = new JScrollPane(jList);
        jList.setVisibleRowCount(1);
        jList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.jPotifyGuiLogic = jPotifyGuiLogic;
        this.jPotifyGuiLogic.setLibrary(this);
        library_label = new JLabel("Library");
        library_label.setBackground(Color.pink);
        add_to_library = new JButton("add to library");
        JButton songs = new JButton("songs");
        albums = new JButton("albums");
        playlists = new JButton("playlists");
        playlists.setBackground(Color.magenta);
        new_playlist = new JButton("new playlist");
        libraryMetaData=new LibraryMetaData(jPotifyGuiLogic);
        libraryMetaData.resetMetadataInLibrary();
        this.add(library_label);
        this.add(add_to_library);
        this.add(songs);
        this.add(albums);
        this.add(playlists_scroll);
        this.add(new_playlist);
        this.add(libraryMetaData);

        new_playlist.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playlist_Name = new Playlist_name().playlistName();
                jPotifyGuiLogic.addPlaylist(playlist_Name);
                listModel.addElement(playlist_Name);
            }
        });

        add_to_library.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                fileChooser.showOpenDialog(null);
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                File selected_file=fileChooser.getSelectedFile();
                if(selected_file!=null) jPotifyGuiLogic.addSong(new Song(selected_file,jPotifyGuiLogic));
            }
        });

        jList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String SelectedValue=jList.getSelectedValue();
                System.out.println("the selected value in jlist in library is : "+SelectedValue);
                //HashMap<String,Song>playlist=jPotifyGuiLogic.getPlaylists().get(SelectedValue);
                ArrayList<Song>songs=jPotifyGuiLogic.getSongsOfAPlaylist(SelectedValue);
                //if(songs.size()>0) {
                    refresh();
                    jPotifyGuiLogic.getCentralScreen().setScreenMode("playlists",SelectedValue);
                    System.out.println("the selcedvalue in playlists is "+SelectedValue);
                    jPotifyGuiLogic.getCentralScreen().songs_Button_action(songs);




                //}

            }

        });

        songs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jPotifyGuiLogic.getCentralScreen().songs_Button_action();
            }
        });

        albums.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                jPotifyGuiLogic.getCentralScreen().albums_Button_action();
            }
        });

        jList.validate();
        playlists_scroll.validate();
        this.validate();
        jPotifyGuiLogic.getjFrame().revalidate();
        this.setVisible(true);

    }

    public LibraryMetaData getLibraryMetaData() {
        return libraryMetaData;
    }

    public void refresh(){
        jList.revalidate();
        playlists_scroll.revalidate();
        this.revalidate();
        jPotifyGuiLogic.getjFrame().revalidate();
    }

    public void removePlaylist(String playlistname){
        listModel.removeElement(playlistname);
        refresh();
    }

    public void renamePlaylist(String oldplaylistname,String newPlaylistname){
        int oldplaylistnameindex=listModel.indexOf(oldplaylistname);
        listModel.add(oldplaylistnameindex,newPlaylistname);
        if(listModel.contains(oldplaylistname))listModel.removeElement(oldplaylistname);
        refresh();
    }

    public void setup(){
       int sizeArray=jPotifyGuiLogic.getPlaylists().size();
       Object []objects=jPotifyGuiLogic.getPlaylists().keySet().toArray();
       String []playlistnames=new String[sizeArray];
       for(int i=0;i<sizeArray;i++){
           playlistnames[i]=(String)objects[i];
       }
       for(int i=0;i<sizeArray;i++){
           listModel.addElement(playlistnames[i]);
       }
       refresh();

    }

    public void deletePlaylist(String playlistname){
        if(listModel.contains(playlistname))listModel.removeElement(playlistname);
        refresh();
    }
}

