package controllers;

import models.Measurements;
import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;

import java.util.List;

public class Dashboard extends Controller
{
  public static void index()
  {
    Logger.info("Rendering Dashboard");
    Member member = Accounts.getLoggedInMember();
    List<Measurements> measurementsList = member.measurementsList;
    render("dashboard.html", member, measurementsList);
  }

  public static void trainerIndex()
  {
    Logger.info("Rendering Trainer Dashboard");
    Trainer trainer = Accounts.getLoggedInTrainer();
    List<Member> members = Member.findAll();
    render("trainerdashboard.html", members, trainer);
  }

  public static void trainermemberviewIndex(Long id)
  {
    Logger.info("Rendering Trainer Member View Dashboard");
    Trainer trainer = Accounts.getLoggedInTrainer();
    Member member = Member.findById(id);
    List<Measurements>measurementsList = member.measurementsList;
    render("trainermemberview.html", member, trainer, measurementsList);
  }

  public static void addMeasurements(double weight, double chest, double thigh, double upperArm, double waist, double hips)
  {
    Member member = Accounts.getLoggedInMember();
    Measurements measurements = new Measurements(weight, chest, thigh, upperArm, waist, hips);
    member.measurementsList.add(measurements);
    member.save();
    Logger.info("Adding Measurements" + weight + chest + thigh + upperArm + waist + hips);
    redirect("/dashboard");
  }

  public static void addComment(String comment, Long measurementsid){

    Measurements measurements = Measurements.findById(measurementsid);
    measurements.comment = comment;
    measurements.save();
    Logger.info("Adding comment "+ comment);
    redirect("/trainerdashboard");

  }

  public static void deleteMeasurements(Long id, Long measurementsid)
  {
    Member member = Member.findById(id);
    Measurements measurements = Measurements.findById(measurementsid);
    member.measurementsList.remove(measurements);
    member.save();
    measurements.delete();
    Logger.info("Deleting " + measurements.weight + measurements.chest + measurements.thigh + measurements.upperArm + measurements.waist + measurements.hips);
    redirect("/dashboard");
  }

}
