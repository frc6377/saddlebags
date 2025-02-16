// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package utilities;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;

/** Add your docs here. */
public class HowdyMM {
  private MotionMagicConfigs motionMagicConfigs = new MotionMagicConfigs();

  public HowdyMM() {}

  public HowdyMM(AngularVelocity velocity, AngularAcceleration acceleration) {
    setAcceleration(acceleration);
    setCruiseVelocity(velocity);
  }

  public HowdyMM(AngularVelocity velocity, AngularAcceleration acceleration, double jerk) {
    setAcceleration(acceleration);
    setCruiseVelocity(velocity);
    setJerk(jerk);
  }

  public HowdyMM(
    AngularVelocity velocity,
      AngularAcceleration acceleration,
      double jerk,
      double kV,
      double kA) {
    setAcceleration(acceleration);
    setCruiseVelocity(velocity);
    setJerk(jerk);
    setExpoKV(kV);
    setExpoKA(kA);
  }

  public void setCruiseVelocity(AngularVelocity velocity) {
    motionMagicConfigs.withMotionMagicCruiseVelocity(velocity);
  }

  public void setAcceleration(AngularAcceleration acceleration) {
    motionMagicConfigs.withMotionMagicAcceleration(acceleration);
  }

  public void setJerk(double jerk) {
    motionMagicConfigs.withMotionMagicJerk(jerk);
  }

  public void setExpoKV(double kV) {
    motionMagicConfigs.withMotionMagicExpo_kV(kV);
  }

  public void setExpoKA(double kA) {
    motionMagicConfigs.withMotionMagicExpo_kA(kA);
  }

  public MotionMagicConfigs getMotionMagicConfigs() {
    return motionMagicConfigs;
  }
}
