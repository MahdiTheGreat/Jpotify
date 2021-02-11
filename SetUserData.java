import javax.swing.*;

public class SetUserData extends JFrame{

    public SetUserData(){}

    public String []setUserData(){
        String username=JOptionPane.showInputDialog(this,"please enter your username.");
        String port=JOptionPane.showInputDialog(this,"please enter your portnumber.");
        String[]usernameAndPort=new String[2];
        usernameAndPort[0]=username;
        usernameAndPort[1]=port;
        return usernameAndPort;
    }
}
