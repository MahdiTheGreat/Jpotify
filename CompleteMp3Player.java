import javax.swing.*;
import javax.xml.catalog.Catalog;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

public class CompleteMp3Player {

    private JPotifyGuiLogic jPotifyGuiLogic;
    private CentralScreen centralScreen;
    private Library library;
    private Mp3PlayerMultiTools mp3PlayerMultiTools;
    private ObjectOutputStream jpotifyLogicOutput;
    private ObjectInputStream jpotifyLogicInput;
    private JFrame jFrame;
    private InternetFriendsPanel internetFriendsPanel;

    public CompleteMp3Player(){
        /* jPotifyGuiLogic=loadData();
        if(jPotifyGuiLogic==null)jPotifyGuiLogic=new JPotifyGuiLogic();
        if (jPotifyGuiLogic.getFriendsData() != null) {
            jPotifyGuiLogic.getFriendsData().setjPotifyGuiLogic(jPotifyGuiLogic);
        } else jPotifyGuiLogic.setFriendsData(new FriendsData());

        library = new Library(jPotifyGuiLogic);
        Setup();
        jFrame = new JFrame();
            jFrame.setLayout(new BorderLayout(5, 5));
            jPotifyGuiLogic.setjFrame(jFrame);*/

        /*jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jFrame.setUndecorated(true);
        */
        Setup();
        centralScreen = new CentralScreen(jPotifyGuiLogic);

            try {
                mp3PlayerMultiTools = new Mp3PlayerMultiTools(jPotifyGuiLogic);
            } catch (Exception e) {
                System.out.println("the exception aquird in mp3playerMultitools constructor in completemp3 and is: " + e);
            }
            internetFriendsPanel = new InternetFriendsPanel(jPotifyGuiLogic);
            jFrame.add(library, BorderLayout.WEST);
            jFrame.add(centralScreen, BorderLayout.CENTER);
            jFrame.add(mp3PlayerMultiTools, BorderLayout.SOUTH);
            jFrame.add(internetFriendsPanel, BorderLayout.EAST);
            jFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    saveData();
                    System.exit(0);


                }
            });
        }



        public void Activate(){
        jFrame.setVisible(true);
    }

    public void saveData() {
        try {
            jpotifyLogicOutput=new ObjectOutputStream(new FileOutputStream("saveData.save"));
            jpotifyLogicOutput.writeObject(jPotifyGuiLogic);
        } catch (IOException e) {
            System.out.println("an exception aquired in savaData :" + e);
        }

    }

    public JPotifyGuiLogic  loadData(){
        JPotifyGuiLogic jPotifyGuiLogic=null;
        try{
            jpotifyLogicInput=new ObjectInputStream(new FileInputStream("saveData.save"));
            jPotifyGuiLogic=(JPotifyGuiLogic)jpotifyLogicInput.readObject();
            //jpotifyLogicOutput=new ObjectOutputStream(new FileOutputStream("saveData.save"));
        }
        catch ( IOException|ClassNotFoundException e){
            System.out.println("the exception aquird in completeMp3Player and is: "+e);
            return jPotifyGuiLogic;


        }
        return jPotifyGuiLogic;
    }

    public void Setup(){
        jFrame = new JFrame();
        //jFrame.setPreferredSize(new Dimension(15000,15000));
        //jFrame.setLocale();
        jFrame.setLayout(new BorderLayout(5, 5));
        jPotifyGuiLogic=loadData();
        if(jPotifyGuiLogic==null)jPotifyGuiLogic=new JPotifyGuiLogic();
        System.out.println("the username is "+jPotifyGuiLogic.getUsername());
        jPotifyGuiLogic.setjFrame(jFrame);
        if (jPotifyGuiLogic.getFriendsData() != null) {
            jPotifyGuiLogic.getFriendsData().setjPotifyGuiLogic(jPotifyGuiLogic);

        }else {
            FriendsData friendsData=new FriendsData();
            jPotifyGuiLogic.setFriendsData(friendsData);
            friendsData.setjPotifyGuiLogic(jPotifyGuiLogic);
        }
        library = new Library(jPotifyGuiLogic);
        library.setup();


    }



}
