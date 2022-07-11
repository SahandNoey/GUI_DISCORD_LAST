package model.client;


import controller.DMController;
import model.other.MemberInfo;
import model.other.Message;
import starter.ClientStarter;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ClientIn{

    private Socket socket;
    ObjectOutputStream fOut;
    static ObjectInputStream fIn;
    ClientOut cOut;
    Scanner scanner = new Scanner(System.in);

    public ClientIn(Socket socket,ObjectInputStream in, ObjectOutputStream out) throws IOException {
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

    public void setcOut(ClientOut cOut){
        this.cOut = cOut;
    }


    public void command(Message m) throws IOException {
        if(m.getMessage().startsWith("%%!getFile:::")) {
            getFile(m);
        }
        else if(m.getMessage().startsWith("%%!getPPic:::")){
            getPPic(m);
        }
        else if(m.getContent() != null){
            if(m.getMessage().startsWith("%%!downloadPic:::")){
                downloadPPic(m);
            }
            else {
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
                }catch (FileNotFoundException e){
                    System.out.println("this file not found.");
                    try {
                        fOut.writeObject(new Message("%%!not found"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }catch (Exception e) {
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
                }catch (FileNotFoundException e){
                    System.out.println("this file not found.");
                    try {
                        fOut.writeObject(new Message("%%!not found"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }catch (Exception e) {
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
                        String fileName = String.copyValueOf(m.getMessage().toCharArray(),3,m.getMessage().length() - 3);
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

    public static void saveProfilePic(String picName){
        try {
            File f = new File("Client\\profilePics\\" + picName);
            FileInputStream fl = new FileInputStream(f);
        }catch (FileNotFoundException e) {
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

    public static void saveServerPic(String picName){
        try {
            File f = new File("Client\\serverPics\\" + picName);
            FileInputStream fl = new FileInputStream(f);
        }catch (FileNotFoundException e) {
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
        while(true){
            Message m = getMessage();
            if(m.getMessage().equals("%%!getOut")){
                return;
            }
            else if(m.getContent() != null){
                byte[] content = m.getContent();
                System.out.println(m.getMessage());
                File f = new File("Client\\downloads\\" + m.getMessage());
                f.createNewFile();
                FileOutputStream fO = new FileOutputStream(f);
                fO.write(content);
                fO.flush();
                fO.close();
                dmController.setTempText("");
                continue;
            }
            else if(m.getMessage().equals("notSelf%%!isTyping")){
                dmController.setTempText("is Typing...");
                continue;
            }
            else if(m.getMessage().equals("notSelf%%!isNotTyping")){
                dmController.setTempText("");
                continue;
            }
            if(ClientStarter.stage.isShowing()) {
                dmController.addMessage(m);
            }
            else{
                ClientOut.sendCommand("%%!getOutOfChat");
                TimeUnit.MILLISECONDS.sleep(500);
                Client.logOut();
                return;
            }

        }
    }


}
