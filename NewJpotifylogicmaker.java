import javax.swing.*;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class NewJpotifylogicmaker {
    public static void main(String[] args)throws Exception {
        ObjectOutputStream jpotifyLogicOutput;
        JFrame jFrame=new JFrame();
        JPotifyGuiLogic jPotifyGuiLogic=new JPotifyGuiLogic();
        jPotifyGuiLogic.setjFrame(jFrame);
        jPotifyGuiLogic.setFriendsData(new FriendsData());
        jPotifyGuiLogic.setUsername("mahdi");
        jpotifyLogicOutput=new ObjectOutputStream(new FileOutputStream("saveData.save"));
        jpotifyLogicOutput.writeObject(jPotifyGuiLogic);
    }
}
