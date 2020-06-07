package controllers;

import models.Measurements;
import play.Logger;
import play.mvc.Controller;

import java.util.List;

public class Admin extends Controller
{
  public static void index()
  {
    Logger.info("Rendering Admin");
    List<Measurements> measurementsList = Measurements.findAll();
    render("admin.html", measurementsList);
  }
}
