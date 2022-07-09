package model.client;

import controller.FriendsController;
import controller.SignInController;
import controller.MainController;
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
                            socket = new Socket("127.0.0.1", 7878);
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

    public static void changeScene(Scene scene) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stage.setScene(scene);
                stage.show();
            }
        });
    }

    public static String checkUserSignUp(String username, String password, String email) {
        cOut.sendCommand("checkUserSignUp:::" + username + ":::" + password + ":::" + email);
        String s = cIn.getMessage().getMessage();
        return s.split(":")[1];
    }

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

    public static void skip() {
        cOut.sendCommand("skip");
    }

    public static boolean checkUserProfilePic(String path) {
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

    public static Message requestProfilePic(){
        cOut.sendCommand("requestProfilePic:::me");
        Message m = cIn.getMessage();
        return m;
    }

    public static void downloadProfilePicIfDontHave(String picName){
        ClientIn.saveProfilePic(picName);
    }

    public static ArrayList<MemberInfo> getFriendsForFriendsMenu() {
        cOut.sendCommand("getFriendsNamesForFriendsMenu");
        Message m = cIn.getMessage();
        ArrayList<MemberInfo> res = new ArrayList<>();
        String[] temp = m.getMessage().split(":::");
        ArrayList<String> informations;
        if (temp.length > 1) {
            informations = (ArrayList<String>) Arrays.stream(temp[1].split(",")).toList();
            for (String information : informations) {
                String name = information.split("-")[0];
                String status = information.split("-")[1];
                String profilePicName = getProfilePicNameOf(name);
                res.add(new MemberInfo(name, status, profilePicName));
                Client.downloadProfilePicIfDontHave(profilePicName);
            }
        }
        return res;
    }

    public static ArrayList<MemberInfo> getFriendRequestForFriendsMenu() {
        cOut.sendCommand("getFriendRequestForFriendsMenu");
        Message m = cIn.getMessage();
        ArrayList<MemberInfo> res = new ArrayList<>();
        String[] temp = m.getMessage().split(":::");
        ArrayList<String> informations;
        if (temp.length > 1) {
            informations = (ArrayList<String>) Arrays.stream(temp[1].split(",")).toList();
            for (String information : informations) {
                String name = information.split("-")[0];
                String status = information.split("-")[1];
                String profilePicName = getProfilePicNameOf(name);
                res.add(new MemberInfo(name, status, profilePicName));
                Client.downloadProfilePicIfDontHave(profilePicName);
            }
        }
        return res;
    }

    public static String checkIfCanSendFriendRequestTo(String name){
        cOut.sendCommand("checkIfCanSendFriendRequestTo:::" + name);
        Message m = cIn.getMessage();
        return m.getMessage();
    }

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

    public static MemberInfo getMyMemberInfo(){
        cOut.sendCommand("myMemberInfo");
        String[] s = cIn.getMessage().getMessage().split(":::");
        String nameWithToken = s[0];
        String status = s[1];
        String email = s[2];
        String password = s[3];
        String phoneNumber = s[4];
        String picName = getProfilePicNameOf(s[0]);
        MemberInfo memberInfo = new MemberInfo(s[0], s[1], picName);
        memberInfo.setEmail(email);
        memberInfo.setPassword(password);
        if(!phoneNumber.equals("null")){
            memberInfo.setPhoneNumber(phoneNumber);
        }
        return memberInfo;
    }

}