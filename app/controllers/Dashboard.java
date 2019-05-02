package controllers;

import models.Assessment;
import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;

import java.util.List;


public class Dashboard extends Controller
{

  public static void index()
  {

    if(Accounts.getLoggedInMember() != null) {
      Logger.info("Rendering Member Dashboard");
      Member member = Accounts.getLoggedInMember();
      List<Assessment> assessmentlist = member.assessmentlist;
      double memberBMI = 0;
      String categoryBMI;
      boolean idealWeight;
      Assessment latest = member.latestAssessment();
      idealWeight = Utility.isIdealBodyWeight(member, latest);
      String idealWeightColor;
      if (idealWeight) {
        idealWeightColor = "green";
      } else
        idealWeightColor = "red";

      if (assessmentlist.size() != 0) {
        memberBMI = Utility.calculateBMI(latest, member);
      }
      categoryBMI = Utility.determineBMICategory(memberBMI);
      render("dashboard.html", member, memberBMI, idealWeight,categoryBMI, idealWeightColor, assessmentlist);
      }
    else {
      redirect("/dashboard");
    }
  }

  public static void trainerIndex() {

    Logger.info("Rendering Trainer Index");
    Trainer trainer = Accounts.getLoggedInTrainer();
    List<Member> members = Member.findAll();
    render("trainerindex.html",trainer, members);
  }

  public static void trainerDashboard(long id) {
    Logger.info("Rendering Trainer Dashboard");
    Member member = Member.findById(id);
    List<Assessment> assessmentlist = member.assessmentlist;
    double memberBMI = 0;
    String categoryBMI;
    boolean idealWeight;
    Assessment latest = member.latestAssessment();
    idealWeight = Utility.isIdealBodyWeight(member, latest);
    String idealWeightColor;
    if (idealWeight) {
      idealWeightColor = "green";
    } else
      idealWeightColor = "red";

    if (assessmentlist.size() != 0) {
      memberBMI = Utility.calculateBMI(latest, member);
    }
    categoryBMI = Utility.determineBMICategory(memberBMI);
    render("trainerdashboard.html", member, memberBMI, idealWeight,categoryBMI, idealWeightColor, assessmentlist);
  }



  public static void addAssessment(double weight, double chest, double thigh, double upperArm, double waist, double hips)
  {
    Member member = Accounts.getLoggedInMember();
    Assessment assessment = new Assessment(weight, chest, thigh, upperArm, waist, hips);
    assessment.save();
    member.assessmentlist.add(assessment);
    member.save();
    Logger.info("Adding Assessment" + weight + chest + thigh + upperArm + waist + hips);
    redirect("/dashboard");
  }

  public static void deleteAssessment(long id, long assessmentid)
  {
    Member member = Member.findById(id);
    Assessment assessment = Assessment.findById(assessmentid);
    member.assessmentlist.remove(assessment);
    member.save();
    assessment.delete();
    Logger.info("Deleting " + assessmentid);
    redirect("/dashboard");
  }

  public static void addComment(long id, long assessmentid, String comment)
  {
    Member member = Member.findById(id);
    Assessment assessment = Assessment.findById(assessmentid);
    assessment.setComment(comment);
    assessment.save();
    Logger.info("Adding Comment" + comment);
    redirect("/trainerdashboard/"+id);
  }


}
