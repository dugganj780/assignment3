package models;

import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends Model
{
  public String firstname;
  public String lastname;
  public String email;
  public String password;
  public String address;
  public double height;
  public double startingweight;

  @OneToMany(cascade = CascadeType.ALL)
  public List<Measurements> measurementsList = new ArrayList<Measurements>();

  public Member(String firstname, String lastname, String email, String password, String address, double height, double startingweight)
  {
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.password = password;
    this.address = address;
    this.height = height;
    this.startingweight = startingweight;
  }

  public void setFirstname(String firstname){this.firstname=firstname;}

  public void setLastname(String lastname){this.lastname=lastname;}

  public void setEmail(String email) {this.email = email;}

  public void setPassword(String password) {this.password = password;}

  public void setAddress(String address) {this.address = address;}

  public void setHeight(double height) {this.height = height;}

  public void setStartingweight(double startingweight) {this.startingweight = startingweight;}

  public static Member findByEmail(String email)
  {
    return find("email", email).first();
  }

  public boolean checkPassword(String password)
  {
    return this.password.equals(password);
  }

  public double calculateBMI(){
    double BMI = 0;
    double latestWeight = startingweight;

    if (measurementsList.size()!=0) {
      latestWeight = measurementsList.get(measurementsList.size()-1).getWeight();
      BMI = (latestWeight) / ((height / 100) * (height / 100));
      BMI = Math.round(BMI * 100);
      BMI = BMI / 100;
    }
    else{
      BMI = (startingweight) / ((height / 100) * (height / 100));
      BMI = Math.round(BMI * 100);
      BMI = BMI / 100;
    }

    return BMI;
  }

  public String categoryBMI(){
    String BMI ="";

    if(calculateBMI()<18.5){BMI="UNDERWEIGHT";}
    if((calculateBMI()>=18.5)&&(calculateBMI()<=24.9)){BMI="HEALTHY WEIGHT RANGE";}
    if((calculateBMI()>=25)&&(calculateBMI()<=29.9)){BMI="OVERWEIGHT";}
    if((calculateBMI()>=30)&&(calculateBMI()<=39.9)){BMI="OBESE";}
    if(calculateBMI()>=40){BMI="VERY SEVERELY OBESE";}

    return BMI;

  }

  public boolean isIdealBodyWeight(){
    boolean isIdealBodyWeight = false;
    double heightInInches;
    double heightRemainder;
    double idealBodyWeight;
    double latestWeight = startingweight;

    if(measurementsList.size()!=0){
      latestWeight = measurementsList.get(measurementsList.size()-1).getWeight();
    }

    heightInInches = height * 0.393701;
    heightRemainder = heightInInches - 60;
    if (heightRemainder<=0){
      idealBodyWeight = 45.5;
    }
    else{
      idealBodyWeight = 45.5 + (heightRemainder * 2.3);
    }

    if (latestWeight <= idealBodyWeight + 0.2 && latestWeight >= idealBodyWeight - 0.2) {
      isIdealBodyWeight = true;
    }
    return  isIdealBodyWeight;
  }

  public int numberOfAssessments(){return measurementsList.size();}

}
