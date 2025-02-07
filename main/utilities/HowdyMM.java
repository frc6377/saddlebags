// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package utilities;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.function.Consumer;

/** Add your docs here. */
@SuppressWarnings("unused")
public class HowdyMM {
  private double kVel;
  private double kAcc;
  private double kJerk;

  private Consumer<Double> consumerVel;
  private Consumer<Double> consumerAcc;
  private Consumer<Double> consumerJerk;

  private TunableNumber tuneVel;
  private TunableNumber tuneAcc;
  private TunableNumber tuneJerk;

  private static final MotionMagicConfigs motionMagicConfigs = new MotionMagicConfigs();

  public HowdyMM(double Vel, double Acc, double Jerk) {
    setVel(Vel);
    setAcc(Acc);
    setJerk(Jerk);
  }

  public void setVel(double Vel) {
    kVel = Vel;
    motionMagicConfigs.withMotionMagicCruiseVelocity(kVel);
  }

  public void setAcc(double Acc) {
    kAcc = Acc;
    motionMagicConfigs.withMotionMagicAcceleration(kAcc);
  }

  public void setJerk(double Jerk) {
    kJerk = Jerk;
    motionMagicConfigs.withMotionMagicJerk(kJerk);
  }

  public MotionMagicConfigs geMotionMagicConfigs() {
    return motionMagicConfigs;
  }

  public void createTunableNumbers(String name, Subsystem subsystem) {
    consumerVel = (value) -> setVel(value);
    consumerAcc = (value) -> setAcc(value);
    consumerJerk = (value) -> setJerk(value);

    tuneVel = new TunableNumber(name + "/Vel", kVel, consumerVel, subsystem);
    tuneAcc = new TunableNumber(name + "/Acc", kAcc, consumerAcc, subsystem);
    tuneJerk = new TunableNumber(name + "/Jerk", kJerk, consumerJerk, subsystem);
  }
}
