package server.ServerChat;

import server.Chat;
import server.Member;
import server.Server;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Serverr implements Serializable {
    private int id;
    transient private static int a = 0;
    private String name;
    private int ownerToken;
    private HashMap<Integer, Naghsh> adminsTokens;
    private ArrayList<Integer> membersTokens;
    private ArrayList<Channel> channels;
    private byte[] pic;
    private int picNum;

    @Serial
    private static final long serialVersionUID = 70020330;

    public Serverr(String name ,int ownerToken) throws IOException {
        id = a;
        a++;
        this.name = name;
        this.ownerToken = ownerToken;
        this.adminsTokens = new HashMap<>();
        this.membersTokens = new ArrayList<>();
        this.channels = new ArrayList<>();
        adminsTokens.put(ownerToken, new Naghsh("Owner",true,true,true,true,true,true,true,true));
        picNum = 0;
        Server.addServerr(this);
        Server.saveServers();
    }

    public void setPic(byte[] pic) throws IOException {
        this.pic = pic;
        picNum++;
        Server.saveServers();
    }

    public String convertTextChannelsNameToAnString(){
        StringBuilder str = new StringBuilder();
        for (Channel channel : channels){
            if (channel instanceof TextChannel) {
                str.append(channel.getName()).append(",");
            }
        }
        if(str.length() > 0) {
            str.deleteCharAt(str.length() - 1);
        }
        else{
            str.append("%");
        }
        return str.toString();
    }

    public String convertMembersToAnString(){
        StringBuilder str = new StringBuilder();
        for (int token : membersTokens){
            Member m = Server.getMemberWithToken(token);
            str.append(m.getUsername()).append("#").append(m.getToken()).append("-").append(m.getStatus()).append(",");
        }
        if(str.length() > 0) {
            str.deleteCharAt(str.length() - 1);
        }
        return str.toString();
    }

    public String convertMembersWithPicNameToAnString(){
        StringBuilder str = new StringBuilder();
        for (int token : membersTokens){
            Member m = Server.getMemberWithToken(token);
            str.append(m.getUsername()).append("#").append(m.getToken()).append("-").append(m.getStatus()).append("-").append(m.getToken()).append("_").append(m.getPicNum()).append(".jpg,");
        }
        if(str.length() > 0) {
            str.deleteCharAt(str.length() - 1);
        }
        return str.toString();
    }

    public String convertVoiceChannelsNameToAnString(){
        StringBuilder str = new StringBuilder();
        for (Channel channel : channels){
            if (channel instanceof VoiceChannel) {
                str.append(channel.getName()).append(",");
            }
        }
        if(str.length() > 0) {
            str.deleteCharAt(str.length() - 1);
        }
        else{
            str.append("%");
        }
        return str.toString();
    }

    public static void setA(int n){
        a = n;
    }
    public int getId() {
        return id;
    }

    public ArrayList<Channel> getChannels(){
        return channels;
    }


    public String getName() {
        return name;
    }


    public int getOwnerToken() {
        return ownerToken;
    }

    public void addChannel(Channel channel) throws IOException {
        channels.add(channel);
        Server.saveServers();
    }

    public  boolean isInServerr(Integer token){
        if(membersTokens.contains(token)){
            return true;
        }
        return false;
    }

    public void addMember(Member member) throws IOException {
        membersTokens.add(member.getToken());
        member.addServerr(this);
        for(Channel channel : channels){
            channel.addMember(member);
            channel.sendWelcomeMessage(member.getUsername());
        }
        Server.saveServers();
    }

    public ArrayList<Integer> getMembersTokens() {
        return membersTokens;
    }

    public void addAdmin(int token,String name,boolean createChannel, boolean deleteChannel, boolean deleteMember,boolean limitMembers, boolean banMembers,boolean changeName, boolean historyChecking,boolean pinMessage) throws IOException {
        adminsTokens.remove(token);
        adminsTokens.put(token, new Naghsh(name,createChannel, deleteChannel, deleteMember, limitMembers, banMembers, changeName, historyChecking, pinMessage));
        Server.saveServers();
    }

    public Naghsh getNaghshOf(String name){
        return adminsTokens.get(name);
    }

    public boolean isAdmin(String name){
        return adminsTokens.containsKey(name);
    }

    public void setName(String name) throws IOException {
        this.name = name;
        Server.saveServers();
    }

    public void deleteChannel(Channel channel) throws IOException {
        channels.remove(channel);
        Server.saveServers();
    }

    public void deleteMember(Member member) throws IOException {
        membersTokens.remove(member.getToken());
        for(Channel channel : channels){
            if(channel instanceof TextChannel){
                ((TextChannel) channel).deleteMember(member);
            }
        }
        Server.saveServers();
        member.deleteServerr(this);
    }

    public byte[] getPic() {
        return pic;
    }

    public int getPicNum() {
        return picNum;
    }

    public String convertAdminsToString(){
        StringBuilder str = new StringBuilder();
        for (int token : adminsTokens.keySet()){
            Member m = Server.getMemberWithToken(token);
            str.append(m.getUsername()).append("#").append(m.getToken()).append("-").append(m.getStatus()).append("-").append(m.getToken()).append("_").append(m.getPicNum()).append(".jpg").append("-").append(adminsTokens.get(token).getAcessability()).append(",");
        }
        if(str.length() > 0) {
            str.deleteCharAt(str.length() - 1);
        }
        else{
            str.append("%");
        }
        return str.toString();
    }

    public String convertInfosTostring(){
        StringBuilder str = new StringBuilder(ownerToken);
        str.append(":::");
        str.append(convertMembersWithPicNameToAnString()).append(":::");
        str.append(convertAdminsToString()).append(":::");
        str.append(convertTextChannelsNameToAnString()).append(":::");
        str.append(convertVoiceChannelsNameToAnString());
        return str.toString();
    }

    public Chat getTextChannelWithName(String name){
        for (Channel channel : channels){
            if(channel instanceof TextChannel){
                if(name.equals(channel.getName())){
                    return ((TextChannel) channel).getChat();
                }
            }
        }
        return null;
    }
}
