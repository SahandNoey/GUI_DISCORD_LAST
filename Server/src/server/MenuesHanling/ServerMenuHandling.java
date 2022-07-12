package server.MenuesHanling;

import server.*;
import server.ServerChat.Channel;
import server.ServerChat.Serverr;
import server.ServerChat.TextChannel;
import server.ServerChat.VoiceChannel;
import model.other.Message;

import java.io.IOException;
import java.util.ArrayList;

public class ServerMenuHandling {

    public static void newServerWithPic(byte[] pic, String name, User u) throws IOException, ClassNotFoundException {
        Serverr serverr = new Serverr(name, u.getMember().getToken());
        serverr.addMember(u.getMember());
        if(pic != null){
            serverr.setPic(pic);
        }
    }

    public static void newServer(String name, User u) throws IOException, ClassNotFoundException {
        Serverr serverr = new Serverr(name, u.getMember().getToken());
        serverr.addMember(u.getMember());
    }


    public static void chosenServerrMenu(int id,User u) throws IOException, ClassNotFoundException {
        Serverr serverr = Server.getServerrWithId(id);
        //todo:
        while (true){
            Message m = InteractionWithUser.read(u);
            if(m.getMessage().equals("%%!getOutOfServer")){
                InteractionWithUser.write(m, u);
                return;
            }
            //change server name
            else if (m.getMessage().startsWith("changeServerName:::")) {
                serverr.setName(m.getMessage().split(":::")[1]);
            }
            //change server pic
            else if(m.getMessage().equals("%%!changeServerPic")){
                serverr.setPic(m.getContent());
                InteractionWithUser.write(new Message(m.getContent(), m.getMessage() + ":::" + serverr.getId() + "_" + serverr.getPicNum() + ".jpg"), u);
            }
            else if(m.getMessage().startsWith("getServersForMainMenu")){
                InteractionWithUser.write(new Message("%%!serversForMainMenu:::" + u.getMember().convertServersToAnString()), u);
            }
            //update information of a server
            else if(m.getMessage().startsWith("%%!updateInfosInServer:::")){
                serverr = Server.getServerrWithId(Integer.parseInt(m.getMessage().split(":::")[1]));
                InteractionWithUser.write(new Message("%%!updateInfosInServer:::" + serverr.convertInfosTostring() + ":::" + u.getMember().convertServersWithPicNameToAnString()), u);
            }
            //create text channel
            else if(m.getMessage().startsWith("%%!createNewTextChannel:::")){
                Serverr serverr1 = Server.getServerrWithId(Integer.parseInt(m.getMessage().split(":::")[1]));
                serverr1.addChannel(new TextChannel(m.getMessage().split(":::")[2], serverr1));
            }

            //create voice channel
            else if(m.getMessage().startsWith("%%!createNewVoiceChannel:::")){
                Serverr serverr1 = Server.getServerrWithId(Integer.parseInt(m.getMessage().split(":::")[1]));
                serverr1.addChannel(new VoiceChannel(m.getMessage().split(":::")[2], serverr1));
            }


            else if(m.getMessage().startsWith("goToTextChannel:::")){
                InteractionWithUser.write(m, u);
                String channelName = m.getMessage().split(":::")[2];
                int serverId = Integer.parseInt(m.getMessage().split(":::")[1]);
                Chat target = Server.getServerrWithId(id).getTextChannelWithName(channelName);
                target.newInChatMember(u);
                while (true) {
                    Message m1 = InteractionWithUser.read(u);
                    if (m1.getMessage().startsWith("%%!")) {
                        if (m1.getMessage().startsWith("%%!getOutOfChannel")) {
                            target.removeInChatMember(u);
                            InteractionWithUser.write(new Message("%%!getOutOfChannel"), u);
                            break;
                        }
                        //create text channel
                        else if(m1.getMessage().startsWith("%%!createNewTextChannel:::")){
                            Serverr serverr1 = Server.getServerrWithId(Integer.parseInt(m1.getMessage().split(":::")[1]));
                            serverr1.addChannel(new TextChannel(m1.getMessage().split(":::")[2], serverr1));
                        }

                        //create voice channel
                        else if(m1.getMessage().startsWith("%%!createNewVoiceChannel:::")){
                            Serverr serverr1 = Server.getServerrWithId(Integer.parseInt(m1.getMessage().split(":::")[1]));
                            serverr1.addChannel(new VoiceChannel(m1.getMessage().split(":::")[2], serverr1));
                        }


                        else if(m1.getMessage().startsWith("%%!getOutOfServer")){
                            target.removeInChatMember(u);
                            InteractionWithUser.write(new Message("%%!getOutOfServer"), u);
                            return;
                        }
                        else if (m1.getMessage().startsWith("%%!getFile:::")) {
                            Message file = target.getFileWithName(m1.getMessage().split(":::")[2]);
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
                        } else if (m1.getMessage().equals("%%!isTyping")) {
                            target.addNewMessage(new Message("notSelf%%!isTyping"), u);
                            continue;
                        } else if (m1.getMessage().equals("%%!isNotTyping")) {
                            target.addNewMessage(new Message("notSelf%%!isNotTyping"), u);
                            continue;
                        }
                    }
                    if (m1.getContent() != null) {
                        target.addNewFile(m1);
                        target.addNewMessage(new Message("%%!file:::" + m1.getMessage(), u.getMember().getToken()), u);
                        continue;
                    }
                    target.addNewMessage(new Message(m1.getMessage(), u.getMember().getToken()), u);
                }
            }
        }
    }

//    public static vpod adminsMenu(User u, Serverr serverr) throws IOException, ClassNotFoundException {
//        switch (choice){
//            case 1 :
//                System.out.println(u.getUserName());
//                if(serverr.getNaghshOf(u.getUserName()).canCreateChanel()) {
//                    createNewChannel(u, serverr);
//                }
//                else {
//                    InteractionWithUser.write(new Message("you cant do this."), u);
//                }
//                break;
//            case 2 :
//                if(serverr.getNaghshOf(u.getUserName()).canChangeName()) {
//                    changeServerName(u, serverr);
//                }
//                else {
//                    InteractionWithUser.write(new Message("you cant do this."), u);
//                }
//                break;
//            case 3 :
//                if(serverr.getOwnerToken() == u.getMember().getToken()) {
//                    addNewAdmin(u, serverr);
//                }
//                else {
//                    InteractionWithUser.write(new Message("you cant do this."), u);
//                }
//                break;
//            case 4 :
//                if(serverr.getNaghshOf(u.getUserName()).canDeleteChanel()) {
//                    deleteChannel(u, serverr);
//                }
//                else {
//                    InteractionWithUser.write(new Message("you cant do this."), u);
//                }
//                break;
//            case 5 :
//                if(serverr.getNaghshOf(u.getUserName()).canDeleteMember()) {
//                    deleteMember(u, serverr);
//                }
//                else {
//                    InteractionWithUser.write(new Message("you cant do this."), u);
//                }
//                break;
//            case 6 :
//                if(serverr.getNaghshOf(u.getUserName()).canLimitMembers()) {
//                    limitMember(u, serverr);
//                }
//                else {
//                    InteractionWithUser.write(new Message("you cant do this."), u);
//                }
//                break;
//            case 7 :
//                if(serverr.getNaghshOf(u.getUserName()).canBanMembers()) {
//                    banMember(u, serverr);
//                }
//                else {
//                    InteractionWithUser.write(new Message("you cant do this."), u);
//                }
//                break;
//            case 8 :
//                if(serverr.getNaghshOf(u.getUserName()).canCheckHistory()) {
//                    checkHistory(u, serverr);
//                }
//                else {
//                    InteractionWithUser.write(new Message("you cant do this."), u);
//                }
//                break;
//            case 9 :
//                if(serverr.getNaghshOf(u.getUserName()).canPinMessage()) {
//                    //InChatFuncs.pinMessage(u, serverr);
//                }
//                else {
//                    InteractionWithUser.write(new Message("you cant do this."), u);
//                }
//                break;
//            case 10 :
//                if(serverr.getOwnerToken() == u.getMember().getToken()) {
//                    ArrayList<Integer> membersTokens = serverr.getMembersTokens();
//                    for(Integer memberToken : membersTokens){
//                        Server.getMemberWithToken(memberToken).deleteServerr(serverr);
//                    }
//                    u.getServer().getServers().remove(serverr);
//                }
//                else {
//                    InteractionWithUser.write(new Message("you cant do this."), u);
//                }
//                break;
//            case 11 :
//               // return User.allMenues.SERVERS;
//        }
//        //return User.allMenues.SERVERS;
//    }


//    public static User.allMenues changeServerName(User u, Serverr serverr) throws IOException, ClassNotFoundException {
//        InteractionWithUser.write(new Message("enter name : (nothing for cancel)"),u);
//        String name = InteractionWithUser.read(u).getMessage();
//        if(name == null || name.equals("")){
//            return User.allMenues.SERVERS;
//        }
//        serverr.setName(name);
//        return User.allMenues.MAIN;
//    }



//    public static User.allMenues deleteMember(User u, Serverr serverr) throws IOException, ClassNotFoundException {
//        InteractionWithUser.write(new Message(serverr.membersToString(u.getServer())), u);
//        int n = serverr.membersSize();
//        InteractionWithUser.write(new Message("" + (n + 1) + "back"), u);
//        int choice = InteractionWithUser.getChoice(1, n + 1, "", u.getfOut(), u.getfIn(), u);
//        if (choice == n + 1) {
//            return adminsMenu(u, serverr);
//        }
//        Member member = Server.getMemberWithToken(serverr.getMemberTokenWithIndex(choice - 1));
//        serverr.deleteMember(member);
//        return User.allMenues.MAIN;
//    }

//    public static User.allMenues limitMember(User u, Serverr serverr) throws IOException, ClassNotFoundException {
//        InteractionWithUser.write(new Message(serverr.membersToString(u.getServer())), u);
//        int n = serverr.membersSize();
//        InteractionWithUser.write(new Message("" + (n + 1) + ".back"), u);
//        int choice = InteractionWithUser.getChoice(1, n + 1, "", u.getfOut(), u.getfIn(), u);
//        if (choice == n + 1) {
//            return adminsMenu(u, serverr);
//        }
//        Member member = Server.getMemberWithToken(serverr.getMemberTokenWithIndex(choice - 1));
//
//
//        InteractionWithUser.write(new Message(serverr.channelsToString()), u);
//        n = serverr.channelsSize();
//        InteractionWithUser.write(new Message("" + (n + 1) + ".back"), u);
//        choice = InteractionWithUser.getChoice(1, n + 1, "", u.getfOut(), u.getfIn(), u);
//        if (choice == n + 1) {
//            return adminsMenu(u, serverr);
//        }
//        Channel channel = serverr.getChannelWithIndex(choice - 1);
//        Chat target = ((TextChannel) channel).getChat();
//        InteractionWithUser.write(new Message("add to or remove from ban list\n1.add\n2.remove\n3.back : "),u);
//        choice = InteractionWithUser.getChoice(1,3,"",u.getfOut(), u.getfIn(),u);
//        if(choice == 3){
//            return adminsMenu(u,serverr);
//        }
//        if(choice == 1){
//            target.addToLimitedList(member.getUsername());
//        }
//        else{
//            target.removeFromLimitedList(member.getUsername());
//        }
//        return adminsMenu(u,serverr);
//    }

//    public static User.allMenues banMember(User u, Serverr serverr) throws IOException, ClassNotFoundException {
//        InteractionWithUser.write(new Message(serverr.membersToString(u.getServer())), u);
//        int n = serverr.membersSize();
//        InteractionWithUser.write(new Message("" + (n + 1) + "back"), u);
//        int choice = InteractionWithUser.getChoice(1, n + 1, "", u.getfOut(), u.getfIn(), u);
//        if (choice == n + 1) {
//            return adminsMenu(u, serverr);
//        }
//        Member member = Server.getMemberWithToken(serverr.getMemberTokenWithIndex(choice - 1));
//        InteractionWithUser.write(new Message("add to or remove from ban list\n1.add\n2.remove\n3.back : "),u);
//        choice = InteractionWithUser.getChoice(1,3,"",u.getfOut(), u.getfIn(),u);
//        if(choice == 3){
//            return adminsMenu(u,serverr);
//        }
//        for(Channel c : serverr.getChannels()){
//            if(c instanceof TextChannel) {
//                Chat target = ((TextChannel)c).getChat();
//                if(choice == 1){
//                    target.addToLimitedList(member.getUsername());
//                }
//                else{
//                    target.removeFromLimitedList(member.getUsername());
//                }
//            }
//        }
//
//        return adminsMenu(u,serverr);
//    }

//    public static User.allMenues createNewChannel(User u, Serverr serverr) throws IOException, ClassNotFoundException {
//        InteractionWithUser.write(new Message("name : "), u);
//        String name = InteractionWithUser.read(u).getMessage();
//        int choice = InteractionWithUser.getChoice(1, 2, "1.text channel\n2.voice channel", u.getfOut(), u.getfIn(), u);
//        if (choice == 1) {
//            InteractionWithUser.write(new Message("welcome message :"), u);
//            String welcomeMessage = InteractionWithUser.read(u).getMessage();
//            TextChannel t = new TextChannel(name,serverr,welcomeMessage);
//            t.addMember(u.getMember());
//            serverr.addChannel(t);
//        } else {
//            serverr.addChannel(new VoiceChannel(name, serverr));
//        }
//        return User.allMenues.SERVERS;
//    }


//    public static User.allMenues addNewMemberToServerr(User u, Serverr serverr) throws IOException, ClassNotFoundException {
//        InteractionWithUser.write(new Message("enter user username"), u);
//        Integer token = Integer.parseInt(InteractionWithUser.read(u).getMessage());
//        Member m = Server.getMemberWithToken(token);
//        if (m != null) {
//            if (m.equals(u.getMember())) {
//                InteractionWithUser.write(new Message("yot cant add yourself to server."), u);
//            } else {
//                if (serverr.isInServerr(token)) {
//                    InteractionWithUser.write(new Message("this user is in this server."), u);
//                } else {
//                    serverr.addMember(m);
//                }
//            }
//        } else {
//            InteractionWithUser.write(new Message("there is no user with this username."), u);
//        }
//        return User.allMenues.MAIN;
//    }



//    public static User.allMenues addNewAdmin(User u, Serverr serverr) throws IOException, ClassNotFoundException {
//        InteractionWithUser.write(new Message("enter user username"), u);
//        Integer token = Integer.parseInt(InteractionWithUser.read(u).getMessage());
//        if (serverr.isInServerr(token)) {
//            InteractionWithUser.write(new Message("name of this acessibility:"), u);
//            String naghshName = InteractionWithUser.read(u).getMessage();
//            boolean createChannel = false, deleteChannel = false, deleteMembers = false, limitMembers = false, banMembers = false, changeName = false, checkHistory = false, pinMessage = false;
//            InteractionWithUser.write(new Message("can create channel ? (1.yes, 2.no)"), u);
//            int choice = InteractionWithUser.getChoice(1, 2, "", u.getfOut(), u.getfIn(), u);
//            if (choice == 1) {
//                createChannel = true;
//            }
//
//            InteractionWithUser.write(new Message("can delete channel ? (1.yes, 2.no)"), u);
//            choice = InteractionWithUser.getChoice(1, 2, "", u.getfOut(), u.getfIn(), u);
//            if (choice == 1) {
//                deleteChannel = true;
//            }
//            InteractionWithUser.write(new Message("can delete member ? (1.yes, 2.no)"), u);
//            choice = InteractionWithUser.getChoice(1, 2, "", u.getfOut(), u.getfIn(), u);
//            if (choice == 1) {
//                deleteMembers = true;
//            }
//
//            InteractionWithUser.write(new Message("can limit member ? (1.yes, 2.no)"), u);
//            choice = InteractionWithUser.getChoice(1, 2, "", u.getfOut(), u.getfIn(), u);
//            if (choice == 1) {
//                limitMembers = true;
//            }
//            InteractionWithUser.write(new Message("can ban member ? (1.yes, 2.no)"), u);
//            choice = InteractionWithUser.getChoice(1, 2, "", u.getfOut(), u.getfIn(), u);
//            if (choice == 1) {
//                banMembers = true;
//            }
//            InteractionWithUser.write(new Message("can change server name ? (1.yes, 2.no)"), u);
//            choice = InteractionWithUser.getChoice(1, 2, "", u.getfOut(), u.getfIn(), u);
//            if (choice == 1) {
//                changeName = true;
//            }
//            InteractionWithUser.write(new Message("can check history ? (1.yes, 2.no)"), u);
//            choice = InteractionWithUser.getChoice(1, 2, "", u.getfOut(), u.getfIn(), u);
//            if (choice == 1) {
//                checkHistory = true;
//            }
//            InteractionWithUser.write(new Message("can pin messsage ? (1.yes, 2.no)"), u);
//            choice = InteractionWithUser.getChoice(1, 2, "", u.getfOut(), u.getfIn(), u);
//            if (choice == 1) {
//                pinMessage = true;
//            }
//            serverr.addAdmin(token,naghshName,createChannel,deleteChannel,deleteMembers,limitMembers,banMembers,changeName,checkHistory,pinMessage);
//
//        } else {
//            InteractionWithUser.write(new Message("there is no user with this username in this serverr."), u);
//        }
//        return User.allMenues.MAIN;
//    }

}
