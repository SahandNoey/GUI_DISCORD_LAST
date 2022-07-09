package server.MenuesHanling;

import server.*;
import server.ValidationPackage.Validation;
import model.other.Message;

import java.io.IOException;

public class Signing {

    public static void signIn(Server server, Member member, User u, Message m) throws IOException, ClassNotFoundException {
        String[] objcts = m.getMessage().split(":::");
        String email = objcts[1];
        String password = objcts[2];
        if (server.isValidMember(email, password)) {
            member = server.logIn(u, email);
            u.setMember(member);
            member.setOnline();
            InteractionWithUser.write(new Message("%%!getCheckUserSignInResult:1"), u);
        } else {
            InteractionWithUser.write(new Message("%%!getCheckUserSignInResult:0"), u);
        }
    }

    //send 1 for okay and error text for error
    public static void signUp(Server server, Member member, User u, Message m) throws IOException, ClassNotFoundException {
        boolean flag = false;
        String[] objcts = m.getMessage().split(":::");
        String username = objcts[1];
        String password = objcts[2];
        String email = objcts[3];
        try {
            Validation.usernameValidation(username);
            Validation.emailValidation(email, server.getMembers());
            Validation.passValidation(password);
            member = new Member(username, password, email);
            u.setMember(member);
            member.setOnline();
            server.addNewMember(member);
            flag = true;
            InteractionWithUser.write(new Message("%%!getCheckUserSignUpResult:1"), u);
        } catch (Exception e) {
            InteractionWithUser.write(new Message("%%!getCheckUserSignUpResult:" + e.getMessage()), u);
        }
        if (flag) {
            while (true) {
                m = InteractionWithUser.read(u);
                if (m.getMessage().startsWith("checkUserPhoneNumber")) {
                    String phonenumber = m.getMessage().split(":::")[1];
                    try {
                        Validation.phoneValidation(phonenumber, server.getMembers());
                        InteractionWithUser.write(new Message("%%!getCheckUserPhoneNumber:1"), u);
                        member.setPhoneNumber(phonenumber);
                        break;
                    } catch (Exception e) {
                        InteractionWithUser.write(new Message("%%!getCheckUserPhoneNumber:0"), u);
                    }
                } else if (m.getMessage().equals("skip")) {
                    break;
                }
            }
            while (true) {
                m = InteractionWithUser.read(u);
                if (m.getMessage().equals("profilepic")) {
                    member.setPic(m.getContent());
                    break;
                }
                else if(m.getMessage().equals("skip")){
                    break;
                }
            }
        }
    }

}
