// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package utilities;

import com.ctre.phoenix6.configs.SlotConfigs;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.function.Consumer;

/** Add your docs here. */
@SuppressWarnings("unused")
public class HowdyPID {
  private double kP;
  private double kI;
  private double kD;
  private double kS;
  private double kV;
  private double kA;
  private double kG;

  private int slotIndex; // should be 0, 1, or 2

  private Consumer<Double> consumerP;
  private Consumer<Double> consumerI;
  private Consumer<Double> consumerD;
  private Consumer<Double> consumerS;
  private Consumer<Double> consumerV;
  private Consumer<Double> consumerA;
  private Consumer<Double> consumerG;

  private TunableNumber tuneP;
  private TunableNumber tuneI;
  private TunableNumber tuneD;
  private TunableNumber tuneS;
  private TunableNumber tuneV;
  private TunableNumber tuneA;
  private TunableNumber tuneG;

  private static final SlotConfigs slotConfigs = new SlotConfigs();

  public HowdyPID(
      int slotID, double kP, double kI, double kD, double kS, double kV, double kA, double kG) {
    slotConfigs.SlotNumber = slotID;
    setKP(kP);
    setKI(kI);
    setKD(kD);
    setKS(kS);
    setKV(kV);
    setKA(kA);
    setKG(kG);
  }

  public void setKP(double kP) {
    this.kP = kP;
    slotConfigs.withKP(kP);
  }

  public void setKI(double kI) {
    this.kI = kP;
    slotConfigs.withKI(kI);
  }

  public void setKD(double kD) {
    this.kD = kD;
    slotConfigs.withKD(kD);
  }

  public void setKS(double kS) {
    this.kS = kS;
    slotConfigs.withKS(kS);
  }

  public void setKV(double kV) {
    this.kV = kV;
    slotConfigs.withKV(kV);
  }

  public void setKA(double kA) {
    this.kA = kA;
    slotConfigs.withKA(kA);
  }

  public void setKG(double kG) {
    this.kG = kG;
    slotConfigs.withKG(kG);
  }

  public void setStaticFeedforwardSign(StaticFeedforwardSignValue staticFeedforwardSignValue) {
    slotConfigs.withStaticFeedforwardSign(staticFeedforwardSignValue);
  }

  public SlotConfigs geSlotConfigs() {
    return slotConfigs;
  }

  public void createTunableNumbers(String name, Subsystem subsystem) {
    consumerP = (value) -> setKP(value);
    consumerI = (value) -> setKI(value);
    consumerD = (value) -> setKD(value);
    consumerS = (value) -> setKS(value);
    consumerV = (value) -> setKV(value);
    consumerA = (value) -> setKA(value);
    consumerG = (value) -> setKG(value);

    tuneP = new TunableNumber(name + ": kP (Slot: " + slotIndex + ")", kP, consumerP, subsystem);
    tuneI = new TunableNumber(name + ": kI (Slot: " + slotIndex + ")", kI, consumerI, subsystem);
    tuneD = new TunableNumber(name + ": kD (Slot: " + slotIndex + ")", kD, consumerD, subsystem);
    tuneS = new TunableNumber(name + ": kS (Slot: " + slotIndex + ")", kS, consumerS, subsystem);
    tuneV = new TunableNumber(name + ": kV (Slot: " + slotIndex + ")", kV, consumerV, subsystem);
    tuneA = new TunableNumber(name + ": kA (Slot: " + slotIndex + ")", kA, consumerA, subsystem);
    tuneG = new TunableNumber(name + ": kG (Slot: " + slotIndex + ")", kG, consumerG, subsystem);
  }
}
