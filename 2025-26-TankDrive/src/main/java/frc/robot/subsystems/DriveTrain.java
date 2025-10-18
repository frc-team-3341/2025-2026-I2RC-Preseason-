// Based upon 2021's Competition Season DriveTrain code

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;
import edu.wpi.first.math.geometry.Rotation3d;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructArrayPublisher;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrain extends SubsystemBase {
  private final WPI_TalonSRX leftDriveTalon;
  private final WPI_TalonSRX rightDriveTalon;

  private double speed;


  // code for simulating robot pose
  private Field2d m_field = new Field2d();
  private final Pose3d poseB = new Pose3d(); // You can leave this as a placeholder or set it later

  private final StructPublisher<Pose3d> posePublisher = NetworkTableInstance.getDefault()
  .getStructTopic("/AdvantageScope/Robot", Pose3d.struct) // special name
  .publish();

private final StructArrayPublisher<Pose3d> poseArrayPublisher = NetworkTableInstance.getDefault()
    .getStructArrayTopic("MyPoseArray", Pose3d.struct).publish();
  private DifferentialDriveOdometry m_odometry;
  private final DifferentialDrivetrainSim driveSim;

  private AHRS navx = new AHRS(NavXComType.kMXP_SPI);

  private ShuffleboardTab DTTab = Shuffleboard.getTab("DriveTrain");
  private GenericEntry LeftVoltage = DTTab.add("Left Output Percent", 0.0).getEntry();
  private GenericEntry RightVoltage = DTTab.add("Right Output Percent", 0.0).getEntry();

  /** Creates a new DriveTrain */
  public DriveTrain() {
    leftDriveTalon = new WPI_TalonSRX(Constants.DriveTrainPorts.LeftDriveTalonPort);
    rightDriveTalon = new WPI_TalonSRX(Constants.DriveTrainPorts.RightDriveTalonPort);

    leftDriveTalon.setNeutralMode(NeutralMode.Coast);
    rightDriveTalon.setNeutralMode(NeutralMode.Coast);

    leftDriveTalon.setInverted(true);
    rightDriveTalon.setInverted(false);

    leftDriveTalon.setSensorPhase(true);
    rightDriveTalon.setSensorPhase(true);

    leftDriveTalon.configFactoryDefault();
    leftDriveTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    rightDriveTalon.configFactoryDefault();
    rightDriveTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

    speed = 0;


    final double KvLinear = 2.98;
    final double KaLinear = 0.2;
    final double kVangular = 0.7;
    final double kAangular = 0.3;
    final double kTrackwidthMeters = 0.69;

    // Create the simulation model of our drivetrain.
    driveSim = new DifferentialDrivetrainSim(
        // Create a linear system from our identification gains.
        LinearSystemId.identifyDrivetrainSystem(KvLinear, KaLinear, kVangular, kAangular),
        DCMotor.getCIM(1), // 1 CIM motor on each side of the drivetrain.
        10.71, // 10.71:1 gearing reduction.
        kTrackwidthMeters, // The track width is 0.7112
        Units.inchesToMeters(3), // The robot uses 3" radius wheels.

        // The standard deviations for measurement noise:
        // x and y: 0.001 m
        // heading: 0.001 rad
        // l and r velocity: 0.1 m/s
        // l and r position: 0.005 m
        VecBuilder.fill(0.0001, 0.0001, 0.0001, 0.01, 0.01, 0.0005, 0.0005));

    m_field.setRobotPose(new Pose2d(0, 0, new Rotation2d(0)));
    SmartDashboard.putData("Field", m_field);
    m_odometry = new DifferentialDriveOdometry(new Rotation2d(0.0), 0, 0);

  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    rightDriveTalon.set(rightSpeed);
    leftDriveTalon.set(leftSpeed);
  }


  public void resetEncoders() {
    leftDriveTalon.setSelectedSensorPosition(0, 0, 10);
    rightDriveTalon.setSelectedSensorPosition(0, 0, 10);
    
  }

  public double getTicks() {
    return (leftDriveTalon.getSelectedSensorPosition(0) + rightDriveTalon.getSelectedSensorPosition(0)) / 2.0;
  }

  public double ticksToMeters() {
    return (0.1524 * Math.PI / 4096.0) * getTicks();

  }

  public double metersToTicks(double positionMeters){
    return positionMeters / (0.1524 * Math.PI) * 4096.0;
  }

  public double getAngle() {
    return navx.getAngle();
  }

  public void resetNavx() {
    navx.reset();
  }


  @Override
  public void periodic() {
    
    SmartDashboard.putNumber("Left Output Percent", leftDriveTalon.getMotorOutputPercent());
    SmartDashboard.putNumber("Left Output Voltage", leftDriveTalon.getMotorOutputVoltage());
    SmartDashboard.putNumber("Right Output Percent", rightDriveTalon.getMotorOutputPercent());
    SmartDashboard.putNumber("Right Output Voltage", rightDriveTalon.getMotorOutputVoltage());
    SmartDashboard.putNumber("Angle", navx.getAngle());

    LeftVoltage.setDouble(leftDriveTalon.getMotorOutputPercent());
    RightVoltage.setDouble(rightDriveTalon.getMotorOutputPercent());
  }

  @Override
public void simulationPeriodic() {
    SmartDashboard.putNumber("Left Sim Pos", driveSim.getLeftPositionMeters());
    SmartDashboard.putNumber("Right Sim Pos", driveSim.getRightPositionMeters());

    // Update encoder and gyro positions
    leftDriveTalon.setSelectedSensorPosition(metersToTicks(driveSim.getLeftPositionMeters()), 0, 10);
    rightDriveTalon.setSelectedSensorPosition(metersToTicks(driveSim.getRightPositionMeters()), 0, 10);

    // Update drivetrain simulation
    driveSim.setInputs(leftDriveTalon.getMotorOutputVoltage(), rightDriveTalon.getMotorOutputVoltage());
    driveSim.update(0.02);

    // Update odometry with the simulated drivetrain state
    m_odometry.update(
        new Rotation2d(driveSim.getHeading().getRadians()),
        driveSim.getLeftPositionMeters(),
        driveSim.getRightPositionMeters()
    );

    m_field.setRobotPose(m_odometry.getPoseMeters());

    // --- Publish Pose3d to NetworkTables for visualization ---
    Pose2d currentPose2d = m_odometry.getPoseMeters();
Rotation3d rot3d = new Rotation3d(0.0, 0.0, currentPose2d.getRotation().getRadians());
Pose3d currentPose3d = new Pose3d(
    new Translation3d(currentPose2d.getX(), currentPose2d.getY(), 0.0),
    rot3d
);

posePublisher.set(currentPose3d);
poseArrayPublisher.set(new Pose3d[] { currentPose3d, poseB });
}
}