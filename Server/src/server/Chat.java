package server;

import server.MenuesHanling.InteractionWithUser;
import model.other.Message;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Chat implements Serializable {
    transient private ArrayList<Member> members;
    private ArrayList<Integer> membersTokens;
    private ArrayList<Message> messages;
    transient private HashSet<User> inChat;
    private int id;
    private String firstMessage;
    private int pinnedMessageIndex = -1;
    private ArrayList<Message> files;
    private transient ArrayList<Member> isTyping;
    private ArrayList<String> limitedMemberNames;
    transient private static int a = 1;
    private static final long serialVersionUID = 70020325;

    //constructor for private chat
    public Chat(ArrayList<Member> members) throws IOException {
        this.members = members;
        membersTokens = new ArrayList<>();
        for(Member m : members){
            membersTokens.add(m.getToken());
        }
        messages = new ArrayList<>();
        inChat = new HashSet<>();
        files = new ArrayList<>();
        isTyping = new ArrayList<>();
        id = a;
        a++;
        Server.addNewChat(this);
        Server.saveChats();
    }

    public void addMember(Member member) throws IOException {
        membersTokens.add(member.getToken());
        this.members.add(member);
        Server.saveChats();
    }

    //constructor for channels chat
    public Chat(String firstMessage){
        this.membersTokens = new ArrayList<>();
        this.members = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.inChat = new HashSet<>();
        this.firstMessage = firstMessage;
        isTyping = new ArrayList<>();
        limitedMemberNames = new ArrayList<>();
    }

    public static void setA(int b){
        a = b;
    }

    public int getId() {
        return id;
    }

    public void addNewMessage(Message m, User u) throws IOException {
        if(!m.getMessage().startsWith("notSelf%%!")) {
            messages.add(m);
            if (m.getContent() == null) {
                for (User temp : inChat) {
                    InteractionWithUser.write(m, temp);
                }
            }
        }else{
            if (m.getContent() == null) {
                for (User temp : inChat) {
                    if(temp != u) {
                        InteractionWithUser.write(m, temp);
                    }
                }
            }
        }

        Server.saveChats();
    }

    public void addNewFile(Message message) throws IOException {
        files.add(message);
        Server.saveChats();
    }

    public Message getFileWithName(String name){
        for(Message m : files){
            if(m.getMessage().equals(name) && m.getContent() != null){
                return m;
            }
        }
        return null;
    }


    public void addWelcomeMessage(String name) throws IOException {
        messages.add(new Message("new member (" + name + ") joined.\n" + firstMessage));
        Server.saveChats();
    }

    public ArrayList<Integer> getMembersTokens() {
        return membersTokens;
    }


    public void newInChatMember(User u) throws IOException {
        if(membersTokens.contains(u.getMember().getToken()) && !inChat.contains(u)){
            inChat.add(u);
            if(messages.size() <= 15) {
                for (Message message : messages) {
                    InteractionWithUser.write(message, u);
                }
            }
            else{
                for(int i = 0; i < 15; i++){
                    Message temp = messages.get(messages.size() - 15 + i);
                    InteractionWithUser.write(temp, u);
                }
            }
        }
    }

    public void removeInChatMember(User u){
        inChat.remove(u);
    }
    public void removeInChatMember(Member member){
        for(User u : inChat){
            if(u.getUserName().equals(member.getUsername())){
                inChat.remove(u);
                return;
            }
        }
    }

    public void initialize(ArrayList<Member> allMembers){
        members = new ArrayList<>();
        inChat = new HashSet<>();
        isTyping = new ArrayList<>();
        for(int token : membersTokens){
            for(Member m : allMembers){
                this.members.add(Server.getMemberWithToken(token));
            }
        }
    }

    public void setMembersTokens(ArrayList<Integer> membersTokens) {
        this.membersTokens = membersTokens;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setPinnedMessage(int pinnedMessageIndex) throws IOException {
        this.pinnedMessageIndex = pinnedMessageIndex;
        Server.saveChats();
    }

    public Message getPinnedMessage() {
        if(pinnedMessageIndex == -1){
            return null;
        }
        return messages.get(pinnedMessageIndex);
    }

    public void addToLimitedList(String username){
        if(!limitedMemberNames.contains(username)){
            limitedMemberNames.add(username);
        }
    }

    public void removeFromLimitedList(String username){
        limitedMemberNames.remove(username);
    }

    public boolean isInLimitedList(String username){
        return limitedMemberNames.contains(username);
    }
}
