package server;

import server.MenuesHanling.FriendHandling;
import server.MenuesHanling.InteractionWithUser;
import server.MenuesHanling.ServerMenuHandling;
import server.MenuesHanling.Signing;
import model.other.Message;
import server.ServerChat.Serverr;
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
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void getCommands() throws IOException, ClassNotFoundException, InterruptedException {
        Message m = InteractionWithUser.read(this);
        //get profile pic of a member
        if(m.getMessage().startsWith("requestProfilePic:::")){
            requestProfilePic(m);
        }
        else if(m.getMessage().startsWith("requestServerPic:::")){
            requestServerPic(m);
        }

        //get user friends names and status
        else if(m.getMessage().equals("getFriendsNamesForFriendsMenu")){
            InteractionWithUser.write(new Message("%%!friendsNamesForFriendsMenu:::" + member.convertFriendsNamesToAnString()), this);
        }

        //get user friends names and status which have direct message
        else if(m.getMessage().equals("getFriendsWithDMForFriendsMenu")){
            InteractionWithUser.write(new Message("%%!friendsNamesForFriendsMenu:::" + member.convertFriendsWithDMToAnString()), this);
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

        //get Server pic of a server based of server id and number of profile changes
        else if(m.getMessage().startsWith("getServerPicNameOf:::")){
            int id = Integer.parseInt(m.getMessage().split(":::")[1]);
            Serverr serverr = member.getServerrWithId(id);
            if(serverr.getPic() != null) {
                InteractionWithUser.write(new Message(serverr.getId() + "_" + serverr.getPicNum() + ".jpg"), this);
            }
            else {
                InteractionWithUser.write(new Message("0"), this);
            }
        }

        //get self profile
        else if(m.getMessage().equals("myMemberInfo")){
            InteractionWithUser.write(new Message(member.getUsername() + "#" + member.getToken() + ":::" + member.getStatus() + ":::" + member.getEmail() + ":::" + member.getPassword() + ":::" + member.getPhoneNumbetString()), this);
        }

        //get a member profile
        else if(m.getMessage().startsWith("memberInfo")){
            Member member = Server.getMemberWithToken(Integer.parseInt(m.getMessage().split(":::")[1]));
            InteractionWithUser.write(new Message(member.getUsername() + "#" + member.getToken() + ":::" + member.getStatus() + ":::" + member.getEmail() + ":::" + member.getPassword() + ":::" + member.getPhoneNumbetString()), this);
        }

        //get a member profile objects
        else if(m.getMessage().startsWith("getMemberProfile")){
            getMemberProfile(m);
        }

        //check for user sign in
        else if (m.getMessage().startsWith("checkUserSignIn")) {
            Signing.signIn(server,this,m);
        }
        //check for user sign up
        else if (m.getMessage().startsWith("checkUserSignUp")) {
            Signing.signUp(server,this, m);
        }
        //check if there is no problem of sending friend request. if there is no problem send friend request and return "yes".
        else if(m.getMessage().startsWith("checkIfCanSendFriendRequestTo:::")){
            try {
                FriendHandling.sendFriendRequest(m.getMessage().split(":::")[1], this);
            }
            catch(Exception e){
                InteractionWithUser.write(new Message("invalid format"), this);
            }
        }
        //get user friend requests names and status
        else if(m.getMessage().startsWith("getFriendRequestForFriendsMenu")){
            InteractionWithUser.write(new Message("%%!friendsNamesForFriendsMenu:::" + member.convertFriendsRequestsToAnString()), this);
        }
        //get server text channels
        else if(m.getMessage().startsWith("getServerTextChannelNames:::")){
            Serverr serverr = Server.getServerrWithId(Integer.parseInt(m.getMessage().split(":::")[1]));
            InteractionWithUser.write(new Message("%%!serverMembersWithId:::" + serverr.convertTextChannelsNameToAnString()), this);
        }
        //get server voice channel
        else if(m.getMessage().startsWith("getServerVoiceChannelNames:::")){
            Serverr serverr = Server.getServerrWithId(Integer.parseInt(m.getMessage().split(":::")[1]));
            InteractionWithUser.write(new Message("%%!serverMembersWithId:::" + serverr.convertVoiceChannelsNameToAnString()), this);
        }

        //go to Server
        else if(m.getMessage().startsWith("goToServer:::")){
            goToServer(m.getMessage().split(":::")[1]);
        }


        //go to chat with someone
        else if(m.getMessage().startsWith("goToChatWith:::")){
            goToDMWith(m.getMessage().split(":::")[1]);
        }
        //get user servers name and pic name
        else if(m.getMessage().startsWith("getServersForMainMenu")){
            InteractionWithUser.write(new Message("%%!serversForMainMenu:::" + member.convertServersToAnString()), this);
        }
        //get server members name and status and pic name
        else if(m.getMessage().startsWith("getServerMembers:::")){
            Serverr serverr = Server.getServerrWithId(Integer.parseInt(m.getMessage().split(":::")[1]));
            InteractionWithUser.write(new Message("%%!serverMembers:::" + serverr.convertMembersToAnString()), this);
        }
        //get user sent friend requests names and status
        else if(m.getMessage().startsWith("getSentFriendRequestForFriendsMenu")){
            InteractionWithUser.write(new Message("%%!sentFriendsRequestsNamesForFriendsMenu:::" + member.convertSentFriendsRequestsToAnString()), this);
        }
        //get user blocks names and status
        else if(m.getMessage().startsWith("getBlocksForFriendsMenu")){
            InteractionWithUser.write(new Message("%%!blocksForFriendsMenu:::" + member.convertBlocksToAnString()), this);
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
        //accept friend request
        else if (m.getMessage().startsWith("acceptFriendRequest:::")) {
            acceptFriendRequest(m.getMessage().split(":::")[1]);
        }

        //reject friend request
        else if (m.getMessage().startsWith("rejectFriendRequest:::")) {
            rejectFriendRequest(m.getMessage().split(":::")[1]);
        }
        //cancel friend request
        else if (m.getMessage().startsWith("cancelFriendRequest:::")) {
            cancelFriendRequest(m.getMessage().split(":::")[1]);
        }
        //log out
        else if (m.getMessage().startsWith("%%!logout")) {
            member.setOffline();
        }

        //create a new serverr
        else if (m.getMessage().startsWith("createNewServer:::")) {
            if(m.getContent() != null){
                ServerMenuHandling.newServerWithPic(m.getContent(), m.getMessage().split(":::")[1], this);
            }
            else {
                ServerMenuHandling.newServer(m.getMessage().split(":::")[1], this);
            }
        }

        //change password
        else if (m.getMessage().startsWith("changePasswordTo:::")) {
            member.setPassword(m.getMessage().split(":::")[1]);
        }
        //update information of a server
        else if(m.getMessage().startsWith("%%!updateInfosInServer:::")){
            Serverr serverr = Server.getServerrWithId(Integer.parseInt(m.getMessage().split(":::")[1]));
            InteractionWithUser.write(new Message("%%!updateInfosInServer:::" + serverr.convertInfosTostring() + ":::"  + getMember().convertServersWithPicNameToAnString()), this);
        }
        //block a user
        else if(m.getMessage().startsWith("block:::")){
            getMember().block(Integer.parseInt(m.getMessage().split(":::")[1]));
        }
        //remove a friend
        else if(m.getMessage().startsWith("removeFriend:::")){
            getMember().removeFriend(Integer.parseInt(m.getMessage().split(":::")[1]));
        }

    }


    public void requestProfilePic(Message m) throws IOException {
        String token = m.getMessage().split(":::")[1];
        Member member;
        if(!token.equals("me")){
            member = server.getMemberWithToken(Integer.parseInt(token));
        }
        else{
            member = this.member;
        }
        if(member.haveProfilePic()){
            InteractionWithUser.write(new Message(member.getPic(), "%%!profilePic:::" + getUserName() + getMember().getPicNum()),this);
        }
        else {
            InteractionWithUser.write(new Message("%%!profilePic:::" + getUserName(),getMember().getToken()),this);
        }
    }


    public void requestServerPic(Message m) throws IOException {
        String id = m.getMessage().split(":::")[1];
        Serverr serverr = Server.getServerrWithId(Integer.parseInt(id));
        if(serverr.getPic() != null){
            InteractionWithUser.write(new Message(serverr.getPic(), "%%!ServerPic:::" + serverr.getName() + serverr.getPicNum()),this);
        }
        else {
            InteractionWithUser.write(new Message("%%!ServerPic:::" + serverr.getName(),serverr.getId()),this);
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
            Validation.phoneValidation(phone, server.getMembers());
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

    public void goToDMWith(String token) throws IOException, ClassNotFoundException, InterruptedException {
        int id = Integer.parseInt(token);
        FriendHandling.privateChat(id, this);
    }
    public void goToServer(String token) throws IOException, ClassNotFoundException {
        int id = Integer.parseInt(token);
        ServerMenuHandling.chosenServerrMenu(id, this);
    }


    public void getMemberProfile(Message m) throws IOException {
        String name = m.getMessage().split(":::")[1];
        Member friend = server.getMember(name);
        InteractionWithUser.write(new Message("%%!memberProfile:::" + friend.getUsername() + ":::" + friend.getToken() + ":::" + friend.getStatus() + ":::" + friend.getEmail()), this);
    }

    public void acceptFriendRequest(String nameWithToken) throws IOException {
        int token = Integer.parseInt(nameWithToken.split("#")[1]);
        if(!getMember().isFriend(token)){
            getMember().acceptFriendRequest(Server.getMemberWithToken(token));
        }
    }

    public void rejectFriendRequest(String nameWithToken) throws IOException {
        int token = Integer.parseInt(nameWithToken.split("#")[1]);
        if(!getMember().isFriend(token)){
            getMember().rejectFriendRequest(Server.getMemberWithToken(token));
        }
    }

    public void cancelFriendRequest(String nameWithToken) throws IOException {
        int token = Integer.parseInt(nameWithToken.split("#")[1]);
        if(!getMember().isFriend(token)){
            Server.getMemberWithToken(token).rejectFriendRequest(getMember());
        }
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
