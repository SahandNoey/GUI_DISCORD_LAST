package server;

import server.MenuesHanling.FriendHandling;
import server.MenuesHanling.InteractionWithUser;
import server.MenuesHanling.Signing;
import model.other.Message;
import server.ValidationPackage.Validation;
import server.ValidationPackage.invalidPasswordFormatException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class User implements Runnable {
    private Socket socket;
    private ObjectOutputStream fOut;
    private ObjectInputStream fIn;
    private Server server;
    private Member member;
    private Thread thread;


    public enum allMenues{SIGN, MAIN, PROFILE, FRIENDS, FRIENDREQUESTS, SERVERS, SETTING, Music};


    public User(Socket socket, Server server) throws IOException, ClassNotFoundException {
        this.socket = socket;
        this.server = server;
        fOut = new ObjectOutputStream(socket.getOutputStream());
        fIn = new ObjectInputStream(socket.getInputStream());
    }

    public void stopThread() throws IOException {
        try {
            member.setOffline();
        }
        catch (Exception ignored){

        }
        thread.stop();
    }

    public Thread getThread() {
        server.removeUser(this);
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public void run(){
        while (true) {
            try {
                getCommands();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void getCommands() throws IOException, ClassNotFoundException {
        Message m = InteractionWithUser.read(this);
        //get profile pic of a member
        if(m.getMessage().startsWith("requestProfilePic:::")){
            requestProfilePic(m);
        }

        //get user friends names and status
        else if(m.getMessage().equals("getFriendsNamesForFriendsMenu")){
            InteractionWithUser.write(new Message("%%!friendsNamesForFriendsMenu:::" + member.convertFriendsNamesToAnString()), this);
        }

        //get profile pic of a member based of user token and number of profile changes
        else if(m.getMessage().startsWith("getProfilePicNameOf:::")){
            int token = Integer.parseInt(m.getMessage().split(":::")[1].split("#")[1]);
            Member member = Server.getMemberWithToken(token);
            if(member.getPic() != null) {
                InteractionWithUser.write(new Message(member.getToken() + "_" + member.getPicNum() + ".jpg"), this);
            }
            else {
                InteractionWithUser.write(new Message("0"), this);
            }
        }

        //get self profile
        else if(m.getMessage().equals("myMemberInfo")){
            InteractionWithUser.write(new Message(member.getUsername() + "#" + member.getToken() + ":::" + member.getStatus() + ":::" + member.getEmail() + ":::" + member.getPassword() + member.getPhoneNumbetString()), this);
        }

        //get a member profile objects
        else if(m.getMessage().startsWith("getMemberProfile")){
            getMemberProfile(m);
        }

        //check for user sign in
        else if (m.getMessage().startsWith("checkUserSignIn")) {
            Member member = new Member(null, null, null);
            Signing.signIn(server,member,this,m);
        }
        //check for user sign up
        else if (m.getMessage().startsWith("checkUserSignUp")) {
            Member member = new Member(null, null, null);
            Signing.signUp(server,member,this, m);
        }
        //check if there is no problem of sending friend request
        else if(m.getMessage().startsWith("checkIfCanSendFriendRequestTo:::")){
            FriendHandling.sendFriendRequest(m.getMessage().split(":::")[1],this);
        }
        //get user friend requests names and status
        else if(m.getMessage().startsWith("getFriendRequestForFriendsMenu")){
            InteractionWithUser.write(new Message("%%!friendsNamesForFriendsMenu:::" + member.convertFriendsRequestsToAnString()), this);
        }
        //check password format and change it if it's valid. return "1" if there is no problem and return "0" if there is a problem
        else if(m.getMessage().startsWith("changePassword:::")){
            passwordChange(m.getMessage().split(":::")[1]);
        }
        //check email format and change it if it's valid. return "1" if there is no problem and return error message if there is a problem
        else if(m.getMessage().startsWith("changeEmail:::")){
            emailChange(m.getMessage().split(":::")[1]);
        }
        //check phone number format and change it if it's valid. return "1" if there is no problem and return error message if there is a problem
        else if(m.getMessage().startsWith("changePhoneNumber:::")){
            phoneChange(m.getMessage().split(":::")[1]);
        }
        //check username format and change it if it's valid. return "1" if there is no problem and return error message if there is a problem
        else if(m.getMessage().startsWith("changeUsername:::")){
            usernameChange(m.getMessage().split(":::")[1]);
        }
        //change status
        else if(m.getMessage().startsWith("changeStatus:::")){
            statusChange(m.getMessage().split(":::")[1]);
        }
        //change profile pic
        else if (m.getMessage().equals("profilepic")) {
            member.setPic(m.getContent());
        }
    }


    public void requestProfilePic(Message m) throws IOException {
        String name = m.getMessage().split(":::")[1];
        Member member;
        if(!name.equals("me")){
            member = server.getMember(name);
        }
        else{
            member = this.member;
        }
        if(member.haveProfilePic()){
            InteractionWithUser.write(new Message(member.getPic(), "%%!profilePic:::" + getUserName() + getMember().getPicNum()),this);
        }
        else {
            InteractionWithUser.write(new Message("%%!profilePic:::" + getUserName(),getUserName()),this);
        }
    }

    public void passwordChange(String password) throws IOException {
        try{
            Validation.passValidation(password);
            member.setPassword(password);
            InteractionWithUser.write(new Message("1"), this);
        } catch (Exception e) {
            InteractionWithUser.write(new Message("0"),  this);
        }
    }


    public void emailChange(String email) throws IOException {
        try{
            Validation.emailValidation(email, server.getMembers());
            member.setEmail(email);
            InteractionWithUser.write(new Message("1"), this);
        } catch (Exception e) {
            InteractionWithUser.write(new Message(e.getMessage()),  this);
        }
    }

    public void phoneChange(String phone) throws IOException {
        try{
            Validation.emailValidation(phone, server.getMembers());
            member.setPhoneNumber(phone);
            InteractionWithUser.write(new Message("1"), this);
        } catch (Exception e) {
            InteractionWithUser.write(new Message(e.getMessage()),  this);
        }
    }

    public void usernameChange(String username) throws IOException {
        try{
            Validation.usernameValidation(username);
            member.setUsername(username);
            InteractionWithUser.write(new Message("1"), this);
        } catch (Exception e) {
            InteractionWithUser.write(new Message(e.getMessage()),  this);
        }
    }

    public void statusChange(String status) throws IOException {
        member.setStatus(status);
    }


    public void getMemberProfile(Message m) throws IOException {
        String name = m.getMessage().split(":::")[1];
        Member friend = server.getMember(name);
        InteractionWithUser.write(new Message("%%!memberProfile:::" + friend.getUsername() + ":::" + friend.getToken() + ":::" + friend.getStatus() + ":::" + friend.getEmail()), this);
    }



    public void setMember(Member member) {
        this.member = member;
    }

    public String getUserName() {
        return member.getUsername();
    }

    public String getPassword() {
        return member.getPassword();
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectOutputStream getfOut() {
        return fOut;
    }

    public Member getMember() {
        return member;
    }

    public ObjectInputStream getfIn() {
        return fIn;
    }

    public Server getServer() {
        return server;
    }

}
