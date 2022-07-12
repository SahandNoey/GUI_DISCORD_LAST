package server.MenuesHanling;

import server.*;
import model.other.Message;

import java.io.IOException;
import java.util.ArrayList;

public class FriendHandling {

    public static void privateChat(int friendToken, User u) throws IOException, ClassNotFoundException {
        Chat target = null;
        Member friend = Server.getMemberWithToken(friendToken);
        if (u.getMember().isInMembersOfChat(friend.getToken())) {
            target = u.getMember().getChatWithName(friend.getToken(), u.getServer().getChats());
        } else {
            ArrayList<Member> mms = new ArrayList<>();
            mms.add(u.getMember());
            mms.add(friend);
            target = new Chat(mms);
            u.getMember().addChatId(friend.getToken(), target.getId());
            friend.addChatId(u.getMember().getToken(), target.getId());
        }
        target.newInChatMember(u);


        while (true) {
            Message m = InteractionWithUser.read(u);
            if(m.getMessage().startsWith("%%!")) {
                if (m.getMessage().startsWith("%%!getOutOfChat")) {
                    target.removeInChatMember(u);
                    InteractionWithUser.write(new Message("%%!getOut"), u);
                    return;
                }
                else if(m.getMessage().startsWith("%%!getFile:::")){
                    Message file = target.getFileWithName(m.getMessage().split(":::")[2]);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                InteractionWithUser.write(file, u);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    continue;
                }
                else if(m.getMessage().equals("%%!isTyping")){
                    target.addNewMessage(new Message("notSelf%%!isTyping"), u);
                    continue;
                }
                else if(m.getMessage().equals("%%!isNotTyping")){
                    target.addNewMessage(new Message("notSelf%%!isNotTyping"), u);
                    continue;
                }
            }
            if(m.getContent() != null){
                target.addNewFile(m);
                target.addNewMessage(new Message("%%!file:::" + m.getMessage(), u.getMember().getToken()), u);
                continue;
            }
            target.addNewMessage(new Message(m.getMessage(), u.getMember().getToken()), u);
//          InChatFuncs.reactToAMessageIn(u,target.getMessages());
//                case 3 :
//                    Message temp = target.getPinnedMessage();
//                    if(temp == null){
//                        InteractionWithUser.write(new Message("there is no pinned message."),u);
//                    }
//                    else{
//                        InteractionWithUser.write(temp, u);

//                    break;
        }
    }


    public static void sendFriendRequest(String nameWithTag, User u) throws IOException{
        String name = "";
        int token = 0;
        try {
            name = nameWithTag.split("#")[0];
            token = Integer.parseInt(nameWithTag.split("#")[1]);
        }catch (Exception e){
            InteractionWithUser.write(new Message("invalid format"), u);
            return ;
        }
        Member m;
        m = Server.getMemberWithToken(token);
        if(m == null){
            InteractionWithUser.write(new Message("invalid username"), u);
            return ;
        }
        if(!m.getUsername().equals(name)){
            InteractionWithUser.write(new Message("invalid username"), u);
            return ;
        }
        if (m.equals(u.getMember())) {
            InteractionWithUser.write(new Message("yot cant send friend request to yourself."), u);
        } else {
            if (!m.isFriend(u.getMember().getToken())) {
                if (!u.getMember().isInFriendRequest(m.getToken())) {
                    if (!m.haveFriendRequestFrom(u.getMember().getToken())) {
                        m.addFriendRequest(u.getMember());
                        u.getMember().addSentFriendRequest(token);
                    }
                    InteractionWithUser.write(new Message("yes"), u);
                } else {
                    InteractionWithUser.write(new Message("you have a friend request from this  user."), u);
                }
            } else {
                InteractionWithUser.write(new Message("you are friends."), u);
            }
        }
    }
}
