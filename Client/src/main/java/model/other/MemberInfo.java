package model.other;

public class MemberInfo {
    private String userNameWithToken;
    private String status;
    private String photoName;
    private String email;
    private String password;
    private String phoneNumber;

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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
