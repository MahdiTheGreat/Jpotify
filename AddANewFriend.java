import javax.swing.*;
import java.io.Serializable;

public class AddANewFriend extends JFrame  {

    public AddANewFriend(){}

    public String [] addANewFriend(){
        String portNumberOfFriend=JOptionPane.showInputDialog(this,"please enter the portnumber of yourfiend");
        String hostAddress=JOptionPane.showInputDialog(this,"please enter the host address of yourfiend");
        this.dispose();
        String[] portAndHost=new String[2];
        portAndHost[0]=portNumberOfFriend;
        portAndHost[1]=hostAddress;
        return portAndHost;

    }

}
