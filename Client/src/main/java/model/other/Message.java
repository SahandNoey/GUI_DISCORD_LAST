package model.other;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;

public class Message implements Serializable {
    private String message;
    private int authorToken;
    private Date date;
    private HashMap<String, String> reactionMemberNames;
    private byte[] content;

    private static final long serialVersionUID = 70020800;

    public Message(String message){
        this.message = message;
        this.reactionMemberNames = new HashMap<>();
        this.date = new Date(LocalDateTime.now());
    }


    public Message(String message,int authorToken){
        this.message = message;
        this.date = new Date(LocalDateTime.now());
        this.authorToken = authorToken;
        reactionMemberNames = new HashMap<>();
    }

    public Message(byte[] content, String message, int authorToken){
        this.content = content;
        this.message = message;
        this.authorToken = authorToken;
        this.date = new Date(LocalDateTime.now());
    }
    public Message(byte[] content, String message){
        this.content = content;
        this.message = message;
        this.authorToken = 0;
        this.date = new Date(LocalDateTime.now());
    }



    public String getMessage() {
        return message;
    }

    public int compareTime(Message m){
        return this.date.compare(m.getDate());
    }

    public Date getDate() {
        return date;
    }

    public int getAuthorToken() {
        return authorToken;
    }

    public void react(String usename, String reaction){
        reactionMemberNames.remove(usename);
        reactionMemberNames.put(usename,reaction);
    }

    public String reactionsToString(){
        int like = 0,dislike = 0, laugh = 0;
        if(reactionMemberNames.values() != null) {
            for (String reaction : reactionMemberNames.values()) {
                if (reaction.equals("like")) {
                    like++;
                }
                if (reaction.equals("dislike")) {
                    dislike++;
                }
                if (reaction.equals("laugh")) {
                    laugh++;
                }
            }
        }
        return "" + like + " likes" + "    " + dislike + " dislikes" + "    " + laugh + " laughs";
    }
    public byte[] getContent(){
        return content;
    }
}
