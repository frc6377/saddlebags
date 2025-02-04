// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package utilities;

import java.util.function.Consumer;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.Slot1Configs;
import com.ctre.phoenix6.configs.Slot2Configs;
import com.ctre.phoenix6.configs.SlotConfigs;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Subsystem;

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

  private SlotConfigs slotConfigs;

  public HowdyPID(int slotID) {
    slotConfigs = new SlotConfigs();
    slotConfigs.SlotNumber = slotID;
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

  // get what slot you want to use
  public SlotConfigs geSlotConfigs(int slotIndex) {
    SlotConfigs slotConfigs = new SlotConfigs().withKP(kP).withKI(kI).withKD(kD).withKS(kS).withKV(kV).withKA(kA).withKG(kG);
    slotConfigs.SlotNumber = slotIndex;
    return slotConfigs;
  }

  public void createTunableNumbers(String name, Subsystem subsystem) {
    consumerP = (value) -> slotConfigs.withKP(value);
    consumerI = (value) -> slotConfigs.withKI(value);
    consumerD = (value) -> slotConfigs.withKD(value);
    consumerS = (value) -> slotConfigs.withKS(value);
    consumerV = (value) -> slotConfigs.withKV(value);
    consumerA = (value) -> slotConfigs.withKA(value);
    consumerG = (value) -> slotConfigs.withKG(value);

    tuneP = new TunableNumber(name.concat(" kP"), kP, consumerP, subsystem);
    tuneI = new TunableNumber(name.concat(" kI"), kI, consumerI, subsystem);
    tuneD = new TunableNumber(name.concat(" kD"), kD, consumerD, subsystem);
    tuneS = new TunableNumber(name.concat(" kS"), kS, consumerS, subsystem);
    tuneV = new TunableNumber(name.concat(" kV"), kV, consumerV, subsystem);
    tuneA = new TunableNumber(name.concat(" kA"), kA, consumerA, subsystem);
    tuneG = new TunableNumber(name.concat(" kG"), kG, consumerG, subsystem);
  }
}
