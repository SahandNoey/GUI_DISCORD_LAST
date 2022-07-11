package model.other;

public class Status {
    private String status;
    private String color;

    public Status(String status, String color){
        this.status = status;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public String getStatus() {
        return status;
    }
}