package model.client;


import controller.DMController;
import controller.ServerAndChannelController;
import model.other.MemberInfo;
import model.other.Message;
import model.other.ServerInfo;
import starter.ClientStarter;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ClientIn {

    private Socket socket;
    ObjectOutputStream fOut;
    static ObjectInputStream fIn;
    ClientOut cOut;
    Scanner scanner = new Scanner(System.in);

    public ClientIn(Socket socket, ObjectInputStream in, ObjectOutputStream out) throws IOException {
        this.socket = socket;
        fOut = out;
        fIn = in;
    }

    public static Message getMessage() {
        try {
            Message m = (Message) fIn.readObject();
            return m;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setcOut(ClientOut cOut) {
        this.cOut = cOut;
    }


    public void command(Message m) throws IOException {
        if (m.getMessage().startsWith("%%!getFile:::")) {
            getFile(m);
        } else if (m.getMessage().startsWith("%%!getPPic:::")) {
            getPPic(m);
        } else if (m.getContent() != null) {
            if (m.getMessage().startsWith("%%!downloadPic:::")) {
                downloadPPic(m);
            } else {
                saveFile(m);
            }
        }
    }

    public void getFile(Message m) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String filePath = m.getMessage().split(":::")[1];
                    String fileName = m.getMessage().split(":::")[2];
                    File f = new File(filePath);
                    FileInputStream fl = new FileInputStream(f);
                    byte[] content = new byte[(int) f.length()];
                    content = fl.readAllBytes();
                    fOut.writeObject(new Message(content, fileName, m.getAuthorToken()));
                    fOut.flush();
                } catch (FileNotFoundException e) {
                    System.out.println("this file not found.");
                    try {
                        fOut.writeObject(new Message("%%!not found"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getPPic(Message m) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String filePath = m.getMessage().split(":::")[1];
                    File f = new File(filePath);
                    FileInputStream fl = new FileInputStream(f);
                    byte[] content = new byte[(int) f.length()];
                    content = fl.readAllBytes();
                    fOut.writeObject(new Message(content, "profilepic", m.getAuthorToken()));
                    fOut.flush();
                } catch (FileNotFoundException e) {
                    System.out.println("this file not found.");
                    try {
                        fOut.writeObject(new Message("%%!not found"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void saveFile(Message m) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String fileName = String.copyValueOf(m.getMessage().toCharArray(), 3, m.getMessage().length() - 3);
                    byte[] temp = m.getContent();
                    File f = new File("downloads\\" + fileName);
                    FileOutputStream fO = new FileOutputStream(f);
                    fO.write(temp);
                    fO.flush();
                    fO.close();
                    System.out.println("downloaded.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void saveProfilePic(String picName) {
        try {
            File f = new File("Client\\profilePics\\" + picName);
            FileInputStream fl = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            try {
                Message m = Client.requestProfilePicOf(picName.split("_")[0]);
                String fileName = picName;
                byte[] temp = m.getContent();
                File f = new File("Client\\profilePics\\" + picName);
                f.createNewFile();
                FileOutputStream fO = new FileOutputStream(f);
                fO.write(temp);
                fO.flush();
                fO.close();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }

    public static void saveServerPic(String picName) {
        try {
            File f = new File("Client\\serverPics\\" + picName);
            FileInputStream fl = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            try {
                Message m = Client.requestServerPicOf(picName.split("_")[0]);
                String fileName = picName;
                byte[] temp = m.getContent();
                File f = new File("Client\\serverPics\\" + picName);
                f.createNewFile();
                FileOutputStream fO = new FileOutputStream(f);
                fO.write(temp);
                fO.flush();
                fO.close();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }

    public static void saveServerPicWithByteArray(byte[] pic, String picName){
        try {
            File f = new File("Client\\serverPics\\" + picName);
            FileInputStream fl = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            try {
                File f = new File("Client\\serverPics\\" + picName);
                f.createNewFile();
                FileOutputStream fO = new FileOutputStream(f);
                fO.write(pic);
                fO.flush();
                fO.close();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }

    public void downloadPPic(Message m) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] temp = m.getContent();
                    File f = new File("downloads\\profile pics\\" + m.getMessage().split(":::")[1] + ".jpg");
                    FileOutputStream fO = new FileOutputStream(f);
                    fO.write(temp);
                    fO.flush();
                    fO.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void gotoDM(DMController dmController) throws IOException, InterruptedException {
        while (true) {
            Message m = getMessage();
            if (m.getMessage().equals("%%!getOut")) {
                return;
            } else if (m.getContent() != null) {
                byte[] content = m.getContent();
                File f = new File("Client\\downloads\\" + m.getMessage());
                f.createNewFile();
                FileOutputStream fO = new FileOutputStream(f);
                fO.write(content);
                fO.flush();
                fO.close();
                dmController.setTempText("");
                continue;
            } else if (m.getMessage().equals("notSelf%%!isTyping")) {
                dmController.setTempText("is Typing...");
                continue;
            } else if (m.getMessage().equals("notSelf%%!isNotTyping")) {
                dmController.setTempText("");
                continue;
            }
            if (ClientStarter.stage.isShowing()) {
                dmController.addMessage(m);
            } else {
                ClientOut.sendCommand("%%!getOutOfChat");
                TimeUnit.MILLISECONDS.sleep(500);
                Client.logOut();
                return;
            }

        }
    }


    public static void gotoServer(ServerAndChannelController controller) throws IOException, InterruptedException {
        while (true) {
            Message m = getMessage();
            if (m.getMessage().equals("%%!getOutOfServer")) {
                return;
            }
            else if (m.getMessage().startsWith("%%!changeServerPic:::")){
                saveServerPicWithByteArray(m.getContent(), m.getMessage().split(":::")[1]);
                controller.setPicName(m.getMessage().split(":::")[1]);
            }
            //update information of a server
            else if(m.getMessage().startsWith("%%!updateInfosInServer:::")){
                System.out.println(m.getMessage());
                String[] temp = m.getMessage().split(":::");
                for(String s : temp){
                    System.out.println(s);
                }
                String[] informations;
                String membersTemp = temp[1];
                String textChannelsTemp = temp[2];
                String voiceChannelsTemp = temp[3];
                ArrayList<ServerInfo> allServers = new ArrayList<>();
                if (temp.length > 4) {
                    String allServersTemp = temp[4];
                    informations = allServersTemp.split(",");
                    for (String information : informations) {
                        String name = information.split("#")[0];
                        int id = Integer.parseInt(information.split("#")[1]);
                        String picName = information.split("#")[2];
                        if(picName.endsWith("0.jpg")){
                            picName = null;
                        }
                        allServers.add(new ServerInfo(name, picName, id));
                        if(picName != null) {
                            Client.downloadServerPicIfDontHave(picName);
                        }
                    }
                }


                ArrayList<String> voiceChannels = new ArrayList<>();
                if(!voiceChannelsTemp.equals("%")) {
                    informations = voiceChannelsTemp.split(",");
                    for (String information : informations) {
                        voiceChannels.add(information);
                    }
                }

                ArrayList<String> textChannels = new ArrayList<>();
                if(!textChannelsTemp.equals("%")) {
                    informations = textChannelsTemp.split(",");
                    for (String information : informations) {
                        textChannels.add(information);
                    }
                }

                ArrayList<MemberInfo> members = new ArrayList<>();
                informations = membersTemp.split(",");
                for (String information : informations) {
                    String name = information.split("-")[0];
                    String status = information.split("-")[1];
                    String profilePicName = information.split("-")[2];
                    if(profilePicName.endsWith("0.jpg")){
                        profilePicName = null;
                    }
                    members.add(new MemberInfo(name, status, profilePicName));
                    if(profilePicName != null) {
                        Client.downloadProfilePicIfDontHave(profilePicName);
                    }
                }
                controller.updateInfosFromClientIn(textChannels, voiceChannels, members, allServers);

            }
        }

    }
}
