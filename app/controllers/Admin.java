package controllers;

import models.Assessment;
import models.Member;
import play.Logger;
import play.mvc.Controller;

import java.util.List;

public class Admin extends Controller
{
  public static void index(Long id)
  {
    Member members = Member.findById(id);
    Logger.info ("Member id = " + id);
    render("dashboard.html", members);
  }
}
