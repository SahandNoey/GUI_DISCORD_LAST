package model.other;

public class MemberInfo {
    private String userNameWithToken;
    private String status;
    private String photoName;

    public MemberInfo(String userNameWithToken, String status, String photoName){
        this.userNameWithToken =  userNameWithToken;
        this.status = status;
        this.photoName = photoName;
    }

    public String getStatus() {
        return status;
    }

    public String getPhotoName() {
        if(photoName == null){
            return "default.jpg";
        }
        return photoName;
    }

    public String getUserNameWithToken() {
        return userNameWithToken;
    }
}
