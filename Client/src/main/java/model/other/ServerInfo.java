package model.other;

public class ServerInfo {
    private String name;
    private String picName;
    private int id;

    public ServerInfo(String name, String picName, int id){
        this.name = name;
        this.picName = picName;
        this.id = id;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getName() {
        return name;
    }

    public String getPicName() {
        if(picName != null) {
            return picName;
        }
        return "default.jpg";
    }
}
