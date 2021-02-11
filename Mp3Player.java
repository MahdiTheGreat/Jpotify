


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javazoom.jl.decoder.JavaLayerException;

import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.JavaSoundAudioDevice;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.awt.*;
import java.io.*;


public class Mp3Player extends Thread {

    private AdvancedPlayer advancedPlayer;
    private Song song;
    private boolean isPaused;
    private int passedframe;
    private boolean isAlive;
    private FileInputStream mp3file;
    private Mp3PlayerMultiTools mp3PlayerMultiTools;
    private boolean seektoo;
    private int seekframe;
    private JavaSoundAudioDevice audio;

    public Mp3Player(Song song, JavaSoundAudioDevice audio) {
        this.audio=audio;
        mp3PlayerMultiTools=song.getjPotifyGuiLogic().getMp3PlayerMultiTools();
        passedframe=0;
        this.song=song;
        mp3PlayerMultiTools.timeVisual(song.getSongLength().intValue(),0);
        try {
           mp3file =new FileInputStream(song.getFileAddress());
           advancedPlayer =new AdvancedPlayer(mp3file,audio);

        } catch ( IOException |JavaLayerException e) {
            System.out.println("exception in Constructor of mp3Player : " + e);
        }

        }

    public void run(){
        isAlive=true;
        System.out.println("enter the run method");
       try {
            while(advancedPlayer.play(1) && isAlive ){

                //System.out.println("went into the frame");

                if(isPaused){
                    synchronized (advancedPlayer){
                        advancedPlayer.wait();
                        System.out.println("is paused");
                    }
                }

                moveSlider();

                if(passedframe>=50 && passedframe%50==0) {

                   mp3PlayerMultiTools.timeVisual(song.getSongLength().intValue(), mp3PlayerMultiTools.getTime(passedframe,song));
                   //System.out.println( mp3PlayerMultiTools.getTime(passedframe,song));

                }
                 passedframe++;

                if(seektoo){
                    seekTo(seekframe);
                    seektoo=false;
                }



            }
            System.out.println("thread has died on its own");

            isAlive=false;

        }catch (JavaLayerException |InterruptedException e){
            System.out.println("exception in run method of mp3player thread : " + e);
        }


    }

    public void moveSlider(){
        mp3PlayerMultiTools.setChangeListnerResume(false);
        mp3PlayerMultiTools.getJslider().setValue(mp3PlayerMultiTools.getJslider().getValue()+1);
        mp3PlayerMultiTools.getJslider().revalidate();
        mp3PlayerMultiTools.setChangeListnerResume(true);
    }

    public void unPause() {
        if(isPaused) isPaused = false;
        synchronized (advancedPlayer){
            advancedPlayer.notifyAll();
        }
    }

    public void pause(){
        isPaused=true;
    }

    public void seekTo(int frame){


        System.out.println("went into seek to");
        try {


            synchronized(advancedPlayer) {

                advancedPlayer.close();
                mp3file.close();
                mp3file = new FileInputStream(song.getFileAddress());
                advancedPlayer = new AdvancedPlayer(mp3file,FactoryRegistry.systemRegistry().createAudioDevice());

                advancedPlayer.play(frame, frame + 1);

            }





        }catch (JavaLayerException |FileNotFoundException e){
            System.out.println("exception in SeekTo method  : " + e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("came out of seek to");


    }


    public boolean getIsAlive(){
        return isAlive;
    }

    public void setIsAlive(boolean status){
        isAlive=status;
    }

    public boolean getIspaused(){
        return isPaused;
    }

    public void activateSeektoo(int frame) {
        seekframe=frame;
        seektoo=true;
    }

    /*public void setSong(Song song) {
        isAlive=false;
        this.song=song;
        if(advancedPlayer!=null)advancedPlayer.close();
        try {
            this.advancedPlayer=new AdvancedPlayer(new FileInputStream(song.getFileAddress()),FactoryRegistry.systemRegistry().createAudioDevice());
        }catch (IOException |JavaLayerException e){
            System.out.println("exception in setSong method  : " + e);
        }
        isAlive=true;
        run();


    }
    */

    public void kill(){
        isAlive=false;


    }

    public void setPassedframe(int value){
        passedframe=value;
    }

    public int getPassedframe() {
        return passedframe;
    }
}
