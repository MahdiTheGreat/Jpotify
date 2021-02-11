import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class FriendsData implements Serializable {
    /*private ArrayList<Integer>friendsPorts;
    private ArrayList<String>friendsHosts;
    */
    private HashMap<Integer,String>friendsUsernames;
    private ArrayList<Integer>friendsPorts;
    private HashMap<Integer,String>friendsHosts;
    private transient JPotifyGuiLogic jPotifyGuiLogic;
    private static final Long SerialVersionUID=26L;

    public FriendsData(){
        friendsPorts=new ArrayList<>();
        friendsHosts=new HashMap<>();
        friendsUsernames=new HashMap<>();

    }

    public ArrayList<Integer> getFriendsPorts() {
        return friendsPorts;
    }

    public HashMap<Integer, String> getFriendsUsernames() {
        return friendsUsernames;
    }

    public HashMap<Integer, String> getFriendsHosts() {
        return friendsHosts;
    }

    public void addFriendusername(int port, String username){
        if(!friendsPorts.contains(port))friendsPorts.add(port);
        friendsUsernames.put(port,username);

    }

    public void addFriendHostAddress(int port,String Host){
        if(!friendsPorts.contains(port))friendsPorts.add(port);
        friendsHosts.put(port,Host);
    }


    /*public void removeFriend(int port, String host, String username){
        if(friendsPorts.contains(port))friendsPorts.remove(port);
        if(friendsHosts.containsKey(port))friendsHosts.remove(port);
        if(fr)


    }*/

    public void setjPotifyGuiLogic(JPotifyGuiLogic jPotifyGuiLogic) {
        this.jPotifyGuiLogic = jPotifyGuiLogic;
    }

    public ArrayList<String> getUsernames(){
        ArrayList<String>usernames=new ArrayList<>();
        for(int i=0;i<friendsPorts.size();i++){
            if(friendsUsernames.containsKey(friendsPorts.get(i)))usernames.add(friendsUsernames.get(i));
        }
        return usernames;
    }
}
