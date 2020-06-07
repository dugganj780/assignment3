package models;

import play.db.jpa.Model;

import javax.persistence.Entity;

@Entity
public class Measurements extends Model
{
  public double weight;
  public double chest;
  public double thigh;
  public double upperArm;
  public double waist;
  public double hips;
  public String comment;


  public Measurements(double weight, double chest, double thigh, double upperArm, double waist, double hips)
  {
    this.weight = weight;
    this.chest = chest;
    this.thigh = thigh;
    this.upperArm = upperArm;
    this.waist = waist;
    this.hips = hips;



  }

  public double getWeight(){
    return weight;
  }


}
