package server.MenuesHanling;

import model.other.Message;
import server.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class InteractionWithUser {
    public static void write(Message message, User u) throws IOException {
        ObjectOutputStream fOut = u.getfOut();
        if (!u.getSocket().isClosed() && u.getSocket().isConnected()) {
            try {
                fOut.writeObject(message);
            } catch (Exception e) {
                System.out.println("someone left.");
                if(u.getMember() != null) {
                    u.getMember().setOffline();
                }
                u.stopThread();
            }
        }
    }

    public static Message read(User u) throws IOException, ClassNotFoundException {
        ObjectInputStream fIn = u.getfIn();
        if (!u.getSocket().isClosed() && u.getSocket().isConnected()) {
            try {
                return (Message) fIn.readObject();
            } catch (Exception e) {
                System.out.println("someone left.");
                if(u.getMember() != null) {
                    u.getMember().setOffline();
                }
                u.stopThread();
            }
        }
        return null;
    }

    public static Message read(User u, int authorToken) throws IOException, ClassNotFoundException {
        ObjectInputStream fIn = u.getfIn();
        if (!u.getSocket().isClosed() && u.getSocket().isConnected()) {
            try {
                return new Message(((Message) fIn.readObject()).getMessage(), authorToken);
            } catch (Exception e) {
                System.out.println("someone left.");
                if(u.getMember() != null) {
                    u.getMember().setOffline();
                }
                u.stopThread();
            }
        }
        return null;
    }

}
