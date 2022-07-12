package model.client;


import model.other.Message;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientOut{

    private Socket socket;
    private static ObjectOutputStream fOut;
    private ObjectInputStream fIn;

    public ClientOut(Socket socket,ObjectInputStream in, ObjectOutputStream out) throws IOException {
        this.socket = socket;
        fOut = out;
        fIn = in;
    }


    public static void sendCommand(String command){
        try {
            System.out.println(command);
            fOut.writeObject(new Message(command));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendFile(Message m) throws IOException {
        try {
            fOut.writeObject(m);
            fOut.flush();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
