package server;

import server.ServerChat.Serverr;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Member implements Serializable {
    @Serial
    private static final long serialVersionUID = 70020312;

    private String username;
    private String password;
    private final ArrayList<Integer> friendsTokens;
    private final HashMap<Integer, Integer> chatsId;
    private String email;
    private String phoneNumber;
    private String status;
    private final ArrayList<Integer> friendRequestsTokens;
    private final ArrayList<Integer> sentFriendRequestsToken;
    private final ArrayList<Integer> blocksTokens;
    transient private ArrayList<Serverr> servers;
    private final ArrayList<Integer> serversIDs;
    private boolean isOnline;
    private byte[] pic;
    private final int token;
    transient private static int a = 1;
    private int picNum = 0;
    private final ArrayList<Integer> blockedBy;

    public Member(String username, String password, String email){
        token = a;
        a++;
        this.password = password;
        this.username = username;
        this.email = email;
        servers = new ArrayList<>();
        friendsTokens = new ArrayList<>();
        friendRequestsTokens = new ArrayList<>();
        blocksTokens = new ArrayList<>();
        serversIDs = new ArrayList<>();
        chatsId = new HashMap<>();
        status = "Online";
        isOnline = false;
        sentFriendRequestsToken = new ArrayList<>();
        blockedBy = new ArrayList<>();
    }

    public static void setA(int n){
        a = n;
    }

    public void setPic(byte[] pic) throws IOException {
        this.pic = pic;
        picNum++;
        Server.saveMembers();
    }

    public byte[] getPic(){
        return pic;
    }

    public int getPicNum() {
        return picNum;
    }

    public String getEmail(){
        return email;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setPassword(String password) throws IOException {
        this.password = password;
        Server.saveMembers();
    }

    public void setPhoneNumber(String phoneNumber) throws IOException {
        this.phoneNumber = phoneNumber;
        Server.saveMembers();
    }


    public void setStatus(String status) throws IOException {
        this.status = status;
        Server.saveMembers();
    }

    public boolean haveProfilePic(){
        return pic != null;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void addFriendRequest(Member friend) throws IOException {
        friendRequestsTokens.add(friend.getToken());
        Server.saveMembers();
    }

    public int getToken() {
        return token;
    }

    public void addServerr(Serverr serverr) throws IOException {
        servers.add(serverr);
        serversIDs.add(serverr.getId());
        Server.saveMembers();
    }



    public void addServers(ArrayList<Serverr> allServers){
        servers = new ArrayList<>();
        for(int id : serversIDs){
            for(Serverr serverr : allServers){
                if(serverr.getId() == id){
                    servers.add(serverr);
                    break;
                }
            }
        }
    }

    public ArrayList<Serverr> getServers() {
        return servers;
    }

    public ArrayList<Integer> getFriends(){
        return friendsTokens;
    }

    public boolean isFriend(int token){
        return friendsTokens.contains(token);
    }

    public boolean isInFriendRequest(int token){
        return friendRequestsTokens.contains(token);
    }


    public boolean haveFriendRequestFrom(int n){
        if(friendRequestsTokens.contains(n)){
            return true;
        }
        return false;
    }

    public void acceptFriendRequest(Member m) throws IOException {
        Integer x = (Integer) (m.getToken());
        friendsTokens.add(x);
        friendRequestsTokens.remove(x);
        m.addFriend(this.token);
        Server.saveMembers();
    }

    public void rejectFriendRequest(Member m) throws IOException {
        Integer x = (Integer) (m.getToken());
        friendRequestsTokens.remove(x);
        m.removeSentFriendRequest(this.token);
        Server.saveMembers();
    }

    public void removeSentFriendRequest(Integer n){
        sentFriendRequestsToken.remove(n);
    }

    public void addFriend(int n) throws IOException {
        Integer x = (Integer) n;
        if(!friendsTokens.contains(x)){
            friendsTokens.add(x);
            friendRequestsTokens.remove(x);
            sentFriendRequestsToken.remove(x);
        }
        Server.saveMembers();
    }


    public void addChatId(int n, int chatId) throws IOException {
        chatsId.put(n, chatId);
        Server.saveMembers();
    }

    public Chat getChatWithToken(int token, ArrayList<Chat> chats){
        int n = chatsId.get(token);
        for(Chat chat : chats){
            if(chat.getId() == n){
                return chat;
            }
        }
        return null;
    }


    public void setOnline() throws IOException {
        isOnline = true;
        Server.saveMembers();
    }

    public void setOffline() throws IOException {
        isOnline = false;
        Server.saveMembers();
    }

    public String getStatus(){
        if(isOnline) {
            return status;
        }
        else{
            return "offline";
        }
    }

    public void block(int token) throws IOException {
        blocksTokens.add(token);
        friendsTokens.remove((Integer) token);
        Server.getMemberWithToken(token).blockBy(this);
        Server.saveMembers();
    }

    public void blockBy(Member m){
        blockedBy.add(m.getToken());
        friendsTokens.remove((Integer) m.getToken());
    }

    public void removeFriend(int token) throws IOException {
        friendsTokens.remove((Integer)token);
        Server.getMemberWithToken(token).removeFriend(this.token);
        Server.saveMembers();
    }

     public boolean isBlocked(int token){
         return blocksTokens.contains(token);
     }

     public void unblock(int token) throws IOException {
         blocksTokens.remove(token);
         Server.saveMembers();
     }


     public void deleteServerr(Serverr serverr) throws IOException {
        servers.remove(serverr);
         Server.saveMembers();
     }

    public String convertFriendsNamesToAnString(){
        StringBuilder str = new StringBuilder();
        for (int token : friendsTokens){
            Member m = Server.getMemberWithToken(token);
            str.append(m.getUsername()).append("#").append(m.getToken()).append("-").append(m.getStatus()).append(",");
        }
        if(str.length() > 0) {
            str.deleteCharAt(str.length() - 1);
        }
        return str.toString();
    }

    public String convertFriendsWithDMToAnString(){
        StringBuilder str = new StringBuilder();
        for (int token : chatsId.keySet()){
            Member m = Server.getMemberWithToken(token);
            str.append(m.getUsername()).append("#").append(m.getToken()).append("-").append(m.getStatus()).append(",");
        }
        if(str.length() > 0) {
            str.deleteCharAt(str.length() - 1);
        }
        return str.toString();
    }

    public void setUsername(String username) throws IOException {
        this.username = username;
        Server.saveMembers();
    }

    public void setEmail(String email) throws IOException {
        this.email = email;
        Server.saveMembers();
    }

    public String convertFriendsRequestsToAnString(){
        StringBuilder str = new StringBuilder();
        for (int token : friendRequestsTokens){
            Member m = Server.getMemberWithToken(token);
            str.append(m.getUsername()).append("#").append(m.getToken()).append("-").append(m.getStatus()).append(",");
        }
        if(str.length() > 0) {
            str.deleteCharAt(str.length() - 1);
        }
        return str.toString();
    }

    public String convertSentFriendsRequestsToAnString(){
        StringBuilder str = new StringBuilder();
        for (int token : sentFriendRequestsToken){
            Member m = Server.getMemberWithToken(token);
            str.append(m.getUsername()).append("#").append(m.getToken()).append("-").append(m.getStatus()).append(",");
        }
        if(str.length() > 0) {
            str.deleteCharAt(str.length() - 1);
        }
        return str.toString();
    }

    public String convertBlocksToAnString(){
        StringBuilder str = new StringBuilder();
        for (int token : blocksTokens){
            Member m = Server.getMemberWithToken(token);
            str.append(m.getUsername()).append("#").append(m.getToken()).append("-").append(m.getStatus()).append(",");
        }
        if(str.length() > 0) {
            str.deleteCharAt(str.length() - 1);
        }
        return str.toString();
    }

    public String getPhoneNumbetString(){
        if(phoneNumber == null){
            return "null";
        }
        return phoneNumber;
    }

    public void addSentFriendRequest(int token) throws IOException {
        sentFriendRequestsToken.add(token);
        Server.saveMembers();
    }



    public String convertServersToAnString(){
        StringBuilder str = new StringBuilder();
        for (Serverr serverr : servers){
            str.append(serverr.getName()).append("#").append(serverr.getId()).append(",");
        }
        if(str.length() > 0) {
            str.deleteCharAt(str.length() - 1);
        }
        return str.toString();
    }

    public String convertServersWithPicNameToAnString(){
        StringBuilder str = new StringBuilder();
        for (Serverr serverr : servers){
            str.append(serverr.getName()).append("#").append(serverr.getId()).append("#").append(serverr.getId()).append("_").append(serverr.getPicNum()).append(".jpg,");
        }
        if(str.length() > 0) {
            str.deleteCharAt(str.length() - 1);
        }
        return str.toString();
    }


    public Serverr getServerrWithId(int id){
        for(Serverr serverr : servers){
            if(serverr.getId() == id){
                return serverr;
            }
        }
        return null;
    }

    public boolean isInMembersOfChat(int n){
        if(chatsId.containsKey(n)){
            return true;
        }
        return false;
    }

    public Chat getChatWithName(int token, ArrayList<Chat> chats){
        int n = chatsId.get(token);
        for(Chat chat : chats){
            if(chat.getId() == n){
                return chat;
            }
        }
        return null;
    }
}
