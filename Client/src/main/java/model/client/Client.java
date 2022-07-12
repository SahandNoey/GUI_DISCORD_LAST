package model.client;

import controller.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import model.other.MemberInfo;
import model.other.Message;
import model.other.ServerInfo;


public class Client {

    private static ObjectOutputStream fOut;
    private static ObjectInputStream fIn;
    private Socket socket;
    private static ClientIn cIn;
    private static ClientOut cOut;
    public static SignInController controller;
    public static MainController mainController;
    public static FriendsController friendsController;
    static Stage stage;

    public void start(Stage stage) {
        controller = new SignInController();
        Client.stage = stage;
        new Thread(new Runnable() {
            @Override
            public void run() {
                fOut = null;
                fIn = null;
                try {
                    socket = new Socket("127.0.0.1", 7878);

                } catch (IOException e) {
                    System.out.println("server not connected.\nwaiting for connection...");
                    while (true) {
                        try {
                            TimeUnit.SECONDS.sleep(3);
                            socket = new Socket("127.0.0.1", 7880);
                            System.out.println("connected.");
                            break;
                        } catch (ConnectException ignored) {

                        } catch (InterruptedException | IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                try {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Parent p = null;
                            try {
                                p = FXMLLoader.load(getClass().getResource("/fxml/signIn.fxml"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Scene scene2 = new Scene(p);
                            stage.setScene(scene2);
                            stage.show();
                        }
                    });
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signIn.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    changeScene(scene);
                    controller = loader.getController();
                    fOut = new ObjectOutputStream(socket.getOutputStream());
                    fIn = new ObjectInputStream(socket.getInputStream());
                    cIn = new ClientIn(socket, fIn, fOut);
                    cOut = new ClientOut(socket, fIn, fOut);
                    cIn.setcOut(cOut);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }


    //check if there is a member with this email and password
    public static boolean checkUserSignIn(String email, String password) {
        cOut.sendCommand("checkUserSignIn:::" + email + ":::" + password);
        String s = cIn.getMessage().getMessage();
        if(Integer.parseInt(s.split(":")[1]) == 1){
            return true;
        }
        else{
            return false;
        }
    }

    //change the scene
    public static void changeScene(Scene scene) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stage.setScene(scene);
                stage.show();
            }
        });
    }

    //check if user can sign up with this informations
    public static String checkUserSignUp(String username, String password, String email) {
        cOut.sendCommand("checkUserSignUp:::" + username + ":::" + password + ":::" + email);
        String s = cIn.getMessage().getMessage();
        return s.split(":")[1];
    }

    //check if the phone number is valid
    public static boolean checkUserPhoneNumber(String phonenumber) {
        cOut.sendCommand("checkUserPhoneNumber:::" + phonenumber);
        String s = cIn.getMessage().getMessage();
        if(Integer.parseInt(s.split(":")[1]) == 1){
            return true;
        }
        else{
            return false;
        }
    }

    //skip if server is waiting for a message but we want to skip.
    public static void skip() {
        cOut.sendCommand("skip");
    }

    //check if the profile pic path is valid. if it's valid change user profile pic.
    public static boolean setUserProfilePic(String path) {
        if (!path.endsWith("jpg")) {
            return false;
        }
        try {
            File f = new File(path);
            FileInputStream fl = new FileInputStream(f);
            byte[] content = new byte[(int) f.length()];
            content = fl.readAllBytes();
            fOut.writeObject(new Message(content, "profilepic"));
            fOut.flush();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static byte[] setServerPic(File f) {
        try {
            FileInputStream fl = new FileInputStream(f);
            byte[] content = new byte[(int) f.length()];
            content = fl.readAllBytes();
            fOut.writeObject(new Message(content, "serverpic"));
            fOut.flush();
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //request profile pic of a member and return a message containing content
    public static Message requestProfilePicOf(String token){
        cOut.sendCommand("requestProfilePic:::" + token);
        Message m = cIn.getMessage();
        return m;
    }

    public static Message requestServerPicOf(String id){
        cOut.sendCommand("requestServerPic:::" + id);
        Message m = cIn.getMessage();
        return m;
    }


    //dwonload Profile pic of a member if its not in profilePics folder
    public static void downloadProfilePicIfDontHave(String picName){
        ClientIn.saveProfilePic(picName);
    }
    //dwonload Server pic of a server if its not in serverPics folder
    public static void downloadServerPicIfDontHave(String picName){
        ClientIn.saveServerPic(picName);
    }

    //return name and token and status and profile pic name of friends of the member
    public static ArrayList<MemberInfo> getFriendsForFriendsMenu() {
        cOut.sendCommand("getFriendsNamesForFriendsMenu");
        Message m = cIn.getMessage();
        ArrayList<MemberInfo> res = new ArrayList<>();
        String[] temp = m.getMessage().split(":::");
        if (temp.length > 1) {
            String[] informations = temp[1].split(",");
            for (String information : informations) {
                String name = information.split("-")[0];
                String status = information.split("-")[1];
                String profilePicName = getProfilePicNameOf(name);
                res.add(new MemberInfo(name, status, profilePicName));
                if(profilePicName != null) {
                    Client.downloadProfilePicIfDontHave(profilePicName);
                }
            }
        }
        return res;
    }

    //return name and token and status and profile pic name of friends of the member
    public static ArrayList<MemberInfo> getServerMembers(int id) {
        cOut.sendCommand("getServerMembers:::" + id);
        Message m = cIn.getMessage();
        ArrayList<MemberInfo> res = new ArrayList<>();
        String[] temp = m.getMessage().split(":::");
        if (temp.length > 1) {
            String[] informations = temp[1].split(",");
            for (String information : informations) {
                String name = information.split("-")[0];
                String status = information.split("-")[1];
                String profilePicName = getProfilePicNameOf(name);
                res.add(new MemberInfo(name, status, profilePicName));
                if(profilePicName != null) {
                    Client.downloadProfilePicIfDontHave(profilePicName);
                }
            }
        }
        return res;
    }



    public static ArrayList<String> getServerTextChannelNames(int id){
        cOut.sendCommand("getServerTextChannelNames:::" + id);
        Message m = cIn.getMessage();
        ArrayList<String> res = new ArrayList<>();
        String[] temp = m.getMessage().split(":::");
        if(temp.length > 1){
            if(!temp[1].equals("%")) {
                String[] informations = temp[1].split(",");
                for (String information : informations) {
                    res.add(information);
                }
            }
        }
        return res;
    }

    public static ArrayList<String> getServerVoiceChannelNames(int id){
        cOut.sendCommand("getServerVoiceChannelNames:::" + id);
        Message m = cIn.getMessage();
        ArrayList<String> res = new ArrayList<>();
        String[] temp = m.getMessage().split(":::");
        if(temp.length > 1){
            if(!temp[1].equals("%")) {
                String[] informations = temp[1].split(",");
                for (String information : informations) {
                    res.add(information);
                }
            }
        }
        return res;
    }

    public static ArrayList<MemberInfo> getFriendsWithDMForFriendsMenu() {
        cOut.sendCommand("getFriendsWithDMForFriendsMenu");
        Message m = cIn.getMessage();
        ArrayList<MemberInfo> res = new ArrayList<>();
        String[] temp = m.getMessage().split(":::");
        if (temp.length > 1) {
            String[] informations = temp[1].split(",");
            for (String information : informations) {
                String name = information.split("-")[0];
                String status = information.split("-")[1];
                String profilePicName = getProfilePicNameOf(name);
                res.add(new MemberInfo(name, status, profilePicName));
                if(profilePicName != null) {
                    Client.downloadProfilePicIfDontHave(profilePicName);
                }
            }
        }
        return res;
    }

    //return name and token and status and profile pic name of friend requests of the member
    public static ArrayList<MemberInfo> getFriendRequestForFriendsMenu() {
        cOut.sendCommand("getFriendRequestForFriendsMenu");
        Message m = cIn.getMessage();
        ArrayList<MemberInfo> res = new ArrayList<>();
        String[] temp = m.getMessage().split(":::");
        if (temp.length > 1) {
            String[] informations = temp[1].split(",");
            for (String information : informations) {
                String name = information.split("-")[0];
                String status = information.split("-")[1];
                String profilePicName = getProfilePicNameOf(name);
                res.add(new MemberInfo(name, status, profilePicName));
                if(profilePicName != null) {
                    Client.downloadProfilePicIfDontHave(profilePicName);
                }
            }
        }
        return res;
    }

    //return name and token and status and profile pic name of sent friend requests of the member
    public static ArrayList<MemberInfo> getSentFriendRequestForFriendsMenu() {
        cOut.sendCommand("getSentFriendRequestForFriendsMenu");
        Message m = cIn.getMessage();
        ArrayList<MemberInfo> res = new ArrayList<>();
        String[] temp = m.getMessage().split(":::");
        if (temp.length > 1) {
            String[] informations = temp[1].split(",");
            for (String information : informations) {
                String name = information.split("-")[0];
                String status = information.split("-")[1];
                String profilePicName = getProfilePicNameOf(name);
                res.add(new MemberInfo(name, status, profilePicName));
                if(profilePicName != null) {
                    Client.downloadProfilePicIfDontHave(profilePicName);
                }
            }
        }
        return res;
    }

    //return name and token and status and profile pic name of blocks of the member
    public static ArrayList<MemberInfo> getBlocksForFriendsMenu() {
        cOut.sendCommand("getBlocksForFriendsMenu");
        Message m = cIn.getMessage();
        ArrayList<MemberInfo> res = new ArrayList<>();
        String[] temp = m.getMessage().split(":::");
        if (temp.length > 1) {
            String[] informations = temp[1].split(",");
            for (String information : informations) {
                String name = information.split("-")[0];
                String status = information.split("-")[1];
                String profilePicName = getProfilePicNameOf(name);
                res.add(new MemberInfo(name, status, profilePicName));
                if(profilePicName != null) {
                    Client.downloadProfilePicIfDontHave(profilePicName);
                }
            }
        }
        return res;
    }

    //check if user can send friend request to given name with token string . return "yes" if there is no problem and return error message if there is a problem
    public static String checkIfCanSendFriendRequestTo(String name){
        cOut.sendCommand("checkIfCanSendFriendRequestTo:::" + name);
        Message m = cIn.getMessage();
        return m.getMessage();
    }

    //get profile pic of a member based of user token and number of profile changes
    public static String getProfilePicNameOf(String nameWithToken){
        cOut.sendCommand("getProfilePicNameOf:::" + nameWithToken);
        String res = cIn.getMessage().getMessage();
        if(res.equals("0")){
            return null;
        }
        else{
            return res;
        }
    }

    //get self profile
    public static MemberInfo getMyMemberInfo(){
        cOut.sendCommand("myMemberInfo");
        String[] s = cIn.getMessage().getMessage().split(":::");
        String nameWithToken = s[0];
        String status = s[1];
        String email = s[2];
        String password = s[3];
        String phoneNumber = s[4];
        String picName = getProfilePicNameOf(nameWithToken);
        MemberInfo memberInfo = new MemberInfo(nameWithToken, status, picName);
        memberInfo.setEmail(email);
        memberInfo.setPassword(password);
        if(picName != null) {
            Client.downloadProfilePicIfDontHave(picName);
        }
        if(!phoneNumber.equals("null")){
            memberInfo.setPhoneNumber(phoneNumber);
        }
        return memberInfo;
    }

    //get a member profile
    public static MemberInfo getInfoOfToken(int token){
        cOut.sendCommand("memberInfo:::" + token);
        String[] s = cIn.getMessage().getMessage().split(":::");
        String nameWithToken = s[0];
        String status = s[1];
        String email = s[2];
        String password = s[3];
        String phoneNumber = s[4];
        String picName = getProfilePicNameOf(nameWithToken);
        MemberInfo memberInfo = new MemberInfo(nameWithToken, status, picName);
        memberInfo.setEmail(email);
        memberInfo.setPassword(password);
        if(picName != null) {
            Client.downloadProfilePicIfDontHave(picName);
        }
        if(!phoneNumber.equals("null")){
            memberInfo.setPhoneNumber(phoneNumber);
        }
        return memberInfo;
    }




    //check if the given password is valid and change it if its valid.
    public static boolean changePassword(String password){
        if(password == null){
            return false;
        }
        if(password.equals("")){
            return false;
        }
        cOut.sendCommand("changePassword:::" + password);
        String res = cIn.getMessage().getMessage();
        if(res.equals("1")){
            return true;
        }
        return false;
    }

    //check if the given email is valid. return "1" if there is no problem and return error message if there is a problem
    public static String changeEmail(String email){
        if(email == null){
            return "email can't be null.";
        }
        if(email.equals("")){
            return "email can't be null.";
        }
        cOut.sendCommand("changeEmail:::" + email);
        String res = cIn.getMessage().getMessage();
        return res;
    }

    //check if the given phone number is valid. return "1" if there is no problem and return error message if there is a problem
    public static String changePhoneNumber(String phone){
        if(phone == null){
            return "phone number can't be null.";
        }
        if(phone.equals("")){
            return "phone number can't be null.";
        }
        cOut.sendCommand("changePhoneNumber:::" + phone);
        String res = cIn.getMessage().getMessage();
        return res;
    }

    //check if the given username is valid. return "1" if there is no problem and return error message if there is a problem
    public static String changeUsername(String username){
        if(username == null){
            return "Username can't be null.";
        }
        if(username.equals("")){
            return "Username can't be null.";
        }
        cOut.sendCommand("changeUsername:::" + username);
        String res = cIn.getMessage().getMessage();
        return res;
    }

    //change status
    public static void changeStatus(String status){
        cOut.sendCommand("changeStatus:::" + status);
    }

    //change profile pic. return true if path is valid
    public static boolean changeProfilePic(String path){
        return setUserProfilePic(path);
    }

    public static ArrayList<ServerInfo> getServersForMainMenu(){
        cOut.sendCommand("getServersForMainMenu");
        Message m = cIn.getMessage();
        ArrayList<ServerInfo> res = new ArrayList<>();
        String[] temp = m.getMessage().split(":::");
        if (temp.length > 1) {
            String[] informations = temp[1].split(",");
            for (String information : informations) {
                String name = information.split("#")[0];
                int id = Integer.parseInt(information.split("#")[1]);
                String picName = getServerPicNameOf(id);
                res.add(new ServerInfo(name, picName, id));
                if(picName != null) {
                    Client.downloadServerPicIfDontHave(picName);
                }
            }
        }
        return res;
    }

    //get profile pic of a member based of user token and number of profile changes
    public static String getServerPicNameOf(int id){
        cOut.sendCommand("getServerPicNameOf:::" + id);
        String res = cIn.getMessage().getMessage();
        if(res.equals("0")){
            return null;
        }
        else{
            return res;
        }
    }

    public static void gotoDMWith(DMController dmController, int token) throws IOException, InterruptedException {
        cOut.sendCommand("goToChatWith:::" + token);
        cIn.gotoDM(dmController);
    }

    public static void acceptFriendRequest(String nameWithToken){
        ClientOut.sendCommand("acceptFriendRequest:::" + nameWithToken);
    }

    public static void rejectFriendRequest(String nameWithToken){
        ClientOut.sendCommand("rejectFriendRequest:::" + nameWithToken);
    }

    public static void cancelFriendRequest(String nameWithToken){
        ClientOut.sendCommand("cancelFriendRequest:::" + nameWithToken);
    }
    public static void logOut(){
        ClientOut.sendCommand("%%!logout");
    }

    public static void createNewServer(File file, String name) throws IOException {
        if(file != null) {
            ClientOut.sendFile(new Message(setServerPic(file), "createNewServer:::" + name));
        }
        else{
            ClientOut.sendCommand("createNewServer:::" + name);
        }
    }

    public static void gotoServer(ServerAndChannelController controller, int id) throws IOException, InterruptedException {
        cOut.sendCommand("goToServer:::" + id);
        cIn.gotoServer(controller);
    }

    public static void changeServerName(String name){
        ClientOut.sendCommand("changeServerName:::" + name);
    }

    public static void changeServerPic(File file) throws IOException {
        ClientOut.sendFile(new Message(setServerPic(file), "%%!changeServerPic"));
    }

    public static void serverLogout(){
        ClientOut.sendCommand("%%!getOutOfServer");
    }

    public static void updateInfosInServer(int id){
        ClientOut.sendCommand("%%!updateInfosInServer:::" + id);
    }

    public static void block(int token){
        ClientOut.sendCommand("block:::" + token);
    }

    public static void removeFriend(int token){
        ClientOut.sendCommand("removeFriend:::" + token);
    }

    public static void goToTextChannel(String channelName, int serverId, ServerAndChannelController controller) throws IOException, InterruptedException {
        cOut.sendCommand("goToTextChannel:::" + serverId + ":::" + channelName);
        cIn.gotoTextChannel(controller);
    }

    public static void channelLogout(){
        ClientOut.sendCommand("%%!getOutOfChannel");
    }


}