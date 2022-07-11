package server.MenuesHanling;

import server.*;
import model.other.Message;

import java.io.IOException;
import java.util.ArrayList;

public class FriendHandling {

    public static void privateChat(int friendToken, User u) throws IOException, ClassNotFoundException {
//        if (friend.isBlocked(u.getMember().getToken())) {
//            InteractionWithUser.write(new Message("this user has blocked you."), u);
//            return User.allMenues.FRIENDS;
//        }
//        if (u.getMember().isBlocked(friend.getToken())) {
//            InteractionWithUser.write(new Message("you have blocked this user."), u);
//            return User.allMenues.FRIENDS;
//        }

//        ArrayList<Chat> chats = u.getServer().getChats();
        Chat target = null;
        Member friend = Server.getMemberWithToken(friendToken);
        if (u.getMember().isInMembersOfChat(friend.getToken())) {
            target = u.getMember().getChatWithName(friend.getToken(), u.getServer().getChats());
        } else {
            ArrayList<Member> mms = new ArrayList<>();
            mms.add(u.getMember());
            mms.add(friend);
            target = new Chat(mms);
            u.getServer().addNewChat(target);
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
                    return;
                }
            }
            if(m.getContent() != null){
                target.addNewFile(m);
                target.addNewMessage(new Message("%%!file:::" + m.getMessage(), u.getMember().getToken()));
                continue;
            }
            target.addNewMessage(new Message(m.getMessage(), u.getMember().getToken()));
//            switch (InChatFuncs.getInChatChoice(target, u)) {
//                case 1:
//                    target.isTyping(u);
//                    target.addNewMessage(InteractionWithUser.read(u, u.getUserName()));
//                    target.endTyping(u);
//                    break;
//                case 2:
//                    InChatFuncs.reactToAMessageIn(u,target.getMessages());
//                    break;
//                case 3 :
//                    Message temp = target.getPinnedMessage();
//                    if(temp == null){
//                        InteractionWithUser.write(new Message("there is no pinned message."),u);
//                    }
//                    else{
//                        InteractionWithUser.write(temp, u);
//                    }
//                    break;
//                case 4 :
//                    InChatFuncs.sendFile(u,target);
//                    break;
//                case 5 :
//                    InChatFuncs.downloadFile(u,target);
//                    break;
//                case 6:
//                    target.removeInChatMember(u);
//                    return User.allMenues.FRIENDS;
//            }
        }
    }

    public static int showProfileAndChoose(Member friend, User u) throws IOException {
        String matn = "";
        if (u.getMember().isBlocked(friend.getToken())) {
            matn = friend.profile() + "\n1.profile pic\n2.Private chat\n3.Unblock\n4.Back";
        } else {
            matn = friend.profile() + "\n1.profile pic\n2.Private chat\n3.Block\n4.Back";
        }
        return InteractionWithUser.getChoice(1, 4, matn, u.getfOut(), u.getfIn(), u);
    }

    public static void blockUnblock(Member friend, User u) throws IOException {
        if (u.getMember().isBlocked(friend.getToken())) {
            u.getMember().unblock(friend.getToken());
        } else {
            u.getMember().block(friend.getToken());
        }
    }

    public static void sendFriendRequest(String nameWithTag, User u) throws IOException, ClassNotFoundException {
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


    public static User.allMenues recievedFriendRequests(User u) throws IOException {
        int n = u.getMember().friendRequestsSize() + 1;
        int choice = InteractionWithUser.getChoice(1, n, u.getMember().showFriendRequests() + "\n" + n + ".back", u.getfOut(), u.getfIn(), u);
        if (choice == n) {
            return User.allMenues.FRIENDREQUESTS;
        }
        u.getMember().acceptFriendRequest(Server.getMemberWithToken(u.getMember().friendRequestWithNumber(choice - 1)));
        InteractionWithUser.write(new Message("friend added."), u);
        return User.allMenues.FRIENDREQUESTS;
    }
}
