// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package utilities;

import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Millimeters;

import au.grapplerobotics.LaserCan;
import au.grapplerobotics.interfaces.LaserCanInterface.Measurement;
import com.playingwithfusion.TimeOfFlight;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Robot;

/** Add your docs here. */
public class TOFSensorSimple {
  private TimeOfFlight TOFSensor;
  private LaserCan LazerCan;
  private Distance threshold;
  private int id;

  public static enum TOFType {
    PW_FUSION,
    LASER_CAN
  }

  private TOFType TOF_Type;

  // For Sim
  private Distance simDistance = Inches.zero();

  public TOFSensorSimple(int ID, Distance threshold, TOFType TOF_Type) {
    id = ID;
    this.TOF_Type = TOF_Type;

    if (TOF_Type == TOFType.LASER_CAN) {
      LazerCan = new LaserCan(this.id);
    } else {
      TOFSensor = new TimeOfFlight(this.id);
    }

    this.threshold = threshold;
  }

  public void setSimDistance(Distance newDist) {
    if (Robot.isReal()) {
      DriverStation.reportWarning(
          "You are trying to set the distance of a real TOF. Please only do this in Simulation.",
          null);
    }
    simDistance = newDist;
  }

  public int getID() {
    return id;
  }

  public Distance getDistance() {
    if (Robot.isSimulation()) return simDistance;
    if (TOF_Type == TOFType.LASER_CAN) {
      Measurement TOFMeasurement = LazerCan.getMeasurement();
      if (TOFMeasurement == null) {
        System.out.println("Laser Can " + id + " Has no measurement!!!!");
        return Millimeters.of(0);
      }
      return Millimeters.of(TOFMeasurement.distance_mm);
    } else {
      return Millimeters.of(TOFSensor.getRange());
    }
  }

  public boolean getBeamBroke() {
    return getDistance().lt(threshold);
  }

  public Trigger getBeamBrokenTrigger() {
    return new Trigger(this::getBeamBroke);
  }

  public void blink() {
    if (Robot.isSimulation())
      DriverStation.reportWarning("You are blinking the TOF with ID of " + id, null);
    if (TOF_Type == TOFType.PW_FUSION) {
      TOFSensor.identifySensor();
    } else {
      DriverStation.reportError(
          "You are trying to blink a non Playing With Fusion TOF Sensor!!", null);
    }
  }
}
