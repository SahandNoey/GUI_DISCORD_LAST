package model.other;

public class AdminInfo {
    private String userNameWithToken;
    private String status;
    private String photoName;
    private int access;

    public AdminInfo(String userNameWithToken, String status, String photoName, int access){
        this.userNameWithToken =  userNameWithToken;
        this.status = status;
        this.photoName = photoName;
        this.access = access;
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

    public boolean canCreateChanel(){
        return access % 2 == 0;
    }
    public boolean canDeleteChanel(){
        return access % 3 == 0;
    }
    public boolean canDeleteMember(){
        return access % 5 == 0;
    }
    public boolean canLimitMembers(){
        return access % 7 == 0;
    }
    public boolean canBanMembers(){
        return access % 11 == 0;
    }
    public boolean canChangeName(){
        return access % 13 == 0;
    }
    public boolean canCheckHistory(){
        return access % 17 == 0;
    }
    public boolean canPinMessage(){
        return access % 19 == 0;
    }


    public String getUserNameWithToken() {
        return userNameWithToken;
    }

}
