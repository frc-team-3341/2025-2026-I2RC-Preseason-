// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix6.controls.StaticBrake;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.event.EventLoop;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveTrain;

public class TankDrive extends Command {
  public DriveTrain dt;
  public Joystick joy;
  /** Creates a new TankDrive. */
  public TankDrive(DriveTrain dt, Joystick j) {
    this.dt = dt;
    this.joy = j;
    dt.speed = 0.7;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(dt);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    dt.tankDrive(0.0, 0.0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

   
    double leftPowerRaw = joy.getRawAxis(1);

    double rightPowerRaw = joy.getRawAxis(5);

    dt.tankDrive(leftPowerRaw*-dt.speed, rightPowerRaw*-dt.speed);
  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    dt.tankDrive(0.0, 0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  public Command increaseSpeed(double increment) {
    return dt.runOnce(() -> {
      if (dt.speed<1.2){
        dt.speed += increment;
      }
      
    });
  }

  public Command decreaseSpeed(double increment){
    return dt.runOnce(() -> {
      if (dt.speed>0.45){
        dt.speed -= increment;
      } 

    });
  }

  public double returnSpeed(){
    return dt.speed;
  }
}
