// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package utilities;

import static edu.wpi.first.units.Units.Millimeters;

import com.playingwithfusion.TimeOfFlight;
import edu.wpi.first.hal.SimBoolean;
import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.hal.SimDevice.Direction;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Robot;

/** Add your docs here. */
public class TOFSensorSimple {
  private static ShuffleboardTab sensorTab = Shuffleboard.getTab("sensors");
  private TimeOfFlight sensor;
  private Distance threshold;
  private int id;
  private SimDevice sim;
  private SimBoolean simBeam;

  public TOFSensorSimple(int ID, Distance threshold) {
    this.id = ID;
    sensor = new TimeOfFlight(this.id);
    if (!Robot.isCompetition) {
      sensorTab.addString(
          "tof sensor " + this.id + " measurement", () -> getDistance().toShortString());
      sensorTab.addBoolean("tof sensor " + this.id + " broken", this::isBeamBroke);
    }
    this.threshold = threshold;
    if (Robot.isSimulation()) {
      sim = SimDevice.create("TOF", id);
      simBeam = sim.createBoolean("BeamBroken", Direction.kBidir, false);
    }
  }

  public int getID() {
    return this.id;
  }

  public Distance getDistance() {
    return Millimeters.of(this.sensor.getRange());
  }

  public boolean isBeamBroke() {
    if (Robot.isSimulation()) {
      return simBeam.get();
    }
    return getDistance().lt(this.threshold);
  }

  public Trigger beamBroken() {
    return new Trigger(this::isBeamBroke);
  }

  public void blink() {
    sensor.identifySensor();
  }
}
