package controllers;

import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;

public class Accounts extends Controller {
    public static void signup() {
        render("signup.html");
    }

    public static void login() {
        render("login.html");
    }

    public static void register(String name, String email, String password, String address, String gender, Double height, Double startingWeight) {
        Logger.info("Registering new user " + email);
        Member member = new Member(name, email, password, address, gender, height, startingWeight);
        member.save();
        redirect("/");
    }


    public static void accountSettings() {
        Logger.info("Rendering Account Settings");
        Member member = Accounts.getLoggedInMember();
        render("accountsettings.html", member);
    }

    public static void update(String name, String email, String password, String address, String gender, Double height, Double startingWeight) {
        Logger.info("Updating user details ");
        Member member = Accounts.getLoggedInMember();
        member.setName(name);
        member.setEmail(email);
        member.setPassword(password);
        member.setAddress(address);
        member.setGender(gender);
        member.setHeight(height);
        member.setStartingWeight(startingWeight);
        member.save();
        render("accountsettings.html", member);
    }



    public static void authenticate(String email, String password) {
        Logger.info("Attempting to authenticate with " + email + ":" + password);
        if(Member.findByEmail(email)!=null){
            Member member = Member.findByEmail(email);
            if ((member != null) && (member.checkPassword(password) == true)) {
                Logger.info("Authentication successful member");
                session.put("logged_in_Memberid", member.id);
                redirect("/dashboard");
            }
        }
        else if(Trainer.findByEmail(email) !=null){
        Trainer trainer = Trainer.findByEmail(email);
        if (trainer.checkPassword(password) == true) {
            Logger.info("Authentication successful trainer");
            session.put("logged_in_Trainerid", trainer.id);
            redirect("/trainerindex");
        }
        }
        else
        {
            Logger.info("Authentication failed");
            redirect("/login");
        }
    }


    public static void logout() {
        session.clear();
        redirect("/");
    }

    public static Member getLoggedInMember() {
        Member member = null;
        if (session.contains("logged_in_Memberid")) {
            String memberId = session.get("logged_in_Memberid");
            member = Member.findById(Long.parseLong(memberId));
        } else {
            login();
        }
        return member;
    }

    public static Trainer getLoggedInTrainer() {
        Trainer trainer = null;
        if (session.contains("logged_in_Trainerid")) {
            String trainerId = session.get("logged_in_Trainerid");
            trainer = Trainer.findById(Long.parseLong(trainerId));
        } else {
            login();
        }
        return trainer;
    }
}





