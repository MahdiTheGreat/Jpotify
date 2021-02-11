import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.JavaSoundAudioDevice;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Mp3PlayerMultiTools extends JPanel  {
    private JLabel lefTtime;
    private JLabel wholeTime;
    private JSlider jslider;
    private JPotifyGuiLogic jPotifyGuiLogic;
    private Mp3Player mp3Player;
    private JButton play;
    private JButton next;
    private JButton previous;
    private JButton addToPlaylist;
    private JButton removeSongfromplaylist;
    private JButton changeTheOrderOfSong;
    private ChangeTheOrderOfThePlaylist changeTheOrderOfThePlaylist;
    private RemoveSongFromPlaylist removeSongFromPlaylist;
    private AddSongToAPlaylist addSongToAPlaylist;
    private Song song;
    private boolean changeListnerResume;
    private JSlider volume;
    private JavaSoundAudioDevice audio;


    public Mp3PlayerMultiTools(JPotifyGuiLogic jPotifyGuiLogic)throws Exception {
        //System.out.println("passed flag");
        try {

            removeSongFromPlaylist=new RemoveSongFromPlaylist();
            removeSongfromplaylist=new JButton("remove from playlist");
            addSongToAPlaylist=new AddSongToAPlaylist();
            changeTheOrderOfThePlaylist=new ChangeTheOrderOfThePlaylist();
            //audio = (JavaSoundAudioDevice) FactoryRegistry.systemRegistry().createAudioDevice();
            this.jPotifyGuiLogic = jPotifyGuiLogic;
            jPotifyGuiLogic.setMp3PlayerMultiTools(this);
            this.setLayout(new FlowLayout());
            lefTtime = new JLabel("    ");
            jslider = new JSlider(JSlider.HORIZONTAL);
            jslider.setPreferredSize(new Dimension(400, 10));
            jslider.setValue(0);
            changeListnerResume = true;
            //System.out.println("passed flag");
            jslider.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    JSlider change = (JSlider) e.getSource();
                    int value = change.getValue();
                    //System.out.println("the value of slider is " + value);
                /*if(song!=null){
                    timeVisual(song.getSongLength().intValue(),getTime(value,song));
                }*/
                    if (!change.getValueIsAdjusting()) {
                        if (changeListnerResume) {
                            //int value = change.getValue();
                            mp3Player.setPassedframe(value);
                            System.out.println("the value of slider is " + value);
                            getTime(value, song);
                            mp3Player.activateSeektoo(value);
                        }
                    }
                }
            });
            //System.out.println("passed flag");
            volume = new JSlider(-50, 6, -37);
            volume.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    JSlider jSlider1 = (JSlider) e.getSource();
                    int value = jSlider1.getValue();
                    if (!jSlider1.getValueIsAdjusting()) {
                        audio.setLineGain((float) value);
                    }
                }
            });
            //System.out.println("passed flag");
            wholeTime = new JLabel("     ");
            play = new JButton("Play//pause");
            addToPlaylist = new JButton("add to playlist");
            next = new JButton("next");
            previous = new JButton("previous");
            changeTheOrderOfSong= new JButton("move song in a playlist");
            play.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (mp3Player.getIspaused()) mp3Player.unPause();
                    else mp3Player.pause();
                }
            });
           // System.out.println("passed flag");
            next.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String[] mode = jPotifyGuiLogic.getCentralScreen().getCentalScreenmode();
                        if (mode[0].equals("songs")) {
                            ArrayList<Song> songs = jPotifyGuiLogic.getDefaultPlaylist_songs();
                            int index = songs.indexOf(song);
                            if (index + 1 <= songs.size()) play(songs.get(index + 1));
                        }
                        if (mode[0].equals("albums")) {
                            //HashMap<String, Song> album = jPotifyGuiLogic.getAlbums().get(mode[1]);
                            ArrayList<Song> songs = jPotifyGuiLogic.getSongsOfAlbum(mode[1]);
                            int index = songs.indexOf(song);
                            if (index + 1 <= songs.size()) play(songs.get(index + 1));
                        }
                        if (mode[0].equals("playlists")) {
                            //HashMap<String, Song> playlist = jPotifyGuiLogic.getPlaylists().get(mode[1]);
                            ArrayList<Song> songs = jPotifyGuiLogic.getSongsOfAPlaylist(mode[1]);
                            int index = songs.indexOf(song);
                            if (index + 1 <= songs.size()) play(songs.get(index + 1));
                        }

                    } catch (Exception ex) {
                        System.out.println("exception in next button happend and is: "+ex);
                    }
                }
            });
            //System.out.println("passed flag");
            previous.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String[] mode = jPotifyGuiLogic.getCentralScreen().getCentalScreenmode();
                    if (mode[0].equals("songs")) {

                        ArrayList<Song> songs = jPotifyGuiLogic.getDefaultPlaylist_songs();
                        int index = songs.indexOf(song);
                        if (index - 1 >= 0) play(songs.get(index - 1));

                    }
                    if (mode[0].equals("albums")) {
                        //HashMap<String, Song> album = jPotifyGuiLogic.getAlbums().get(mode[1]);
                        ArrayList<Song> songs = jPotifyGuiLogic.getSongsOfAlbum(mode[1]);
                        int index = songs.indexOf(song);
                        if (index - 1 >= 0) play(songs.get(index - 1));
                    }
                    if (mode[0].equals("playlists")) {
                        //HashMap<String, Song> playlist = jPotifyGuiLogic.getPlaylists().get(mode[1]);
                        ArrayList<Song> songs = jPotifyGuiLogic.getSongsOfAPlaylist(mode[1]);
                        int index = songs.indexOf(song);
                        if (index + 1 <= songs.size()) play(songs.get(index - 1));
                    }
                    }catch (Exception ex){
                        System.out.println("exception in previous button happend and is: "+ex);
                    }
                }
            });
            //System.out.println("passed flag");
            removeSongfromplaylist.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(mp3Player!=null && mp3Player.getIsAlive()) {
                        String playlist = removeSongFromPlaylist.playlistNameToRemoveTheSongFrom();
                        jPotifyGuiLogic.removeSongfromPlaylist(playlist, song);
                    }

                }
            });
            addToPlaylist.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(mp3Player!=null && mp3Player.getIsAlive()) {
                        String playlist = addSongToAPlaylist.playlistNameToAddTheSongTo();
                        jPotifyGuiLogic.addSongToPlaylist(playlist, song);
                    }
                }
            });

            changeTheOrderOfSong.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(mp3Player!=null && mp3Player.getIsAlive()) {
                        if (jPotifyGuiLogic.getCentralScreen().getCentalScreenmode()[0].equals("playlists")) {
                            String input = changeTheOrderOfThePlaylist.ChangeTheOrderOfAsong();
                            jPotifyGuiLogic.ChangeTheOrder(input, song);
                        }
                    }
                }
            });
            //System.out.println("passed flag");
            //previous.setPreferredSize(new Dimension(20,10));
            volume.setPreferredSize(new Dimension(150, 10));
            this.add(wholeTime);
            this.add(previous);
            this.add(play);
            this.add(jslider);
            this.add(next);
            this.add(lefTtime);
            this.add(addToPlaylist);
            this.add(removeSongfromplaylist);
            this.add(changeTheOrderOfSong);
            this.add(volume);
            this.validate();
            jPotifyGuiLogic.getjFrame().revalidate();
            this.setVisible(true);
        }catch (Exception e){
            System.out.println("exception in mp3playermultitools constructor happend and is: "+e);
        }
        //System.out.println("passed flag");

    }

    public void timeVisual(int completeTime,int passedTime ){
        //System.out.println("went into timevisual");
        lefTtime.setText(minutify(completeTime-passedTime));
        wholeTime.setText(minutify(completeTime));
        /*changeListnerResume=false;
        jslider.setValue(jslider.getValue()+1);
        jslider.revalidate();
        changeListnerResume=true;
        */
        jslider.revalidate();
        this.revalidate();
        jPotifyGuiLogic.getjFrame().revalidate();

    }

    public String minutify(int seconds){

        int secondPortion=seconds%60;
        int minutes=(seconds-secondPortion)/60;
        String time;
        if(minutes>=10) time=minutes+":";
        else time="0"+minutes+":";
        if(secondPortion>=10)time=time+""+secondPortion;
        else time=time+"0"+secondPortion;
        return time;
    }

    public int getTime(int frame,Song song){
        /*System.out.println("the lenght of song is "+song.getSongLength().intValue());
        System.out.println("the number of frames of the song is+");
        System.out.println("the number of frame in gettime is "+frame);
        System.out.println("the value of fraction in gettime is "+frame/song.getFrames());
        float value=(frame/song.getFrames())*song.getSongLength();*/
        //System.out.println("the number of frames is "+song.getFrames());
        //int devidedvalue=frame;
        //System.out.println("the value of devidedvalue is "+ frame);
        double fractionvalue=(double)frame/(double)song.getFrames();
        //System.out.println("the value of fraction in gettime is "+fractionvalue);
        double value=(double)fractionvalue*(double)song.getSongLength();
        //System.out.println("the value in gettime is"+value);
        Long valueInlong=Math.round(value);
        //System.out.println("the value in gettime in long is"+valueInlong);
        return valueInlong.intValue();
       }

    public JSlider getJslider() {
        return jslider;
    }

    public void setJslider(int min,int max) {
        changeListnerResume=false;
        //jslider = new JSlider(JSlider.HORIZONTAL);
        jslider.setValue(0);
        jslider.setMinimum(min);
        jslider.setMaximum(max);
        /*jslider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider change=(JSlider)e.getSource();
                if(!change.getValueIsAdjusting()) {
                    if(changeListnerResume) {
                        int value = change.getValue();
                        System.out.println("the value of slider is " + value);
                        mp3Player.activateSeektoo(value);
                    }
                }
            }
        });
        */
        jslider.revalidate();
        changeListnerResume=true;
        this.revalidate();
        jPotifyGuiLogic.getjFrame().revalidate();
    }

    public void play(Song song) {
        try {
            //jsliderPreviousvalue=0;
            audio = (JavaSoundAudioDevice) FactoryRegistry.systemRegistry().createAudioDevice();
            this.song = song;
            setJslider(0, song.getFrames());
            if (mp3Player != null) {
                mp3Player.kill();
            }
            mp3Player = new Mp3Player(song, audio);
            mp3Player.start();
        }catch (JavaLayerException e){
            System.out.println("exception in play happend and is: "+e);
        }

    }

    public void setChangeListnerResume(boolean set){
        changeListnerResume=set;
    }



}

