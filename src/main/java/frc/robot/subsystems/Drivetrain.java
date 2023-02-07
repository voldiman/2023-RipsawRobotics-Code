package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.InvertType;
import edu.wpi.first.wpilibj.Encoder;
import frc.robot.Constants;


public class Drivetrain extends SubsystemBase {
  // Encoders
  private static final double kCountsPerRevolution = 1440.0;
  private static final double kWheelDiameterInch = 2.75591; // 70 mm
  private final Encoder m_leftEncoder = new Encoder(4, 5);
  private final Encoder m_rightEncoder = new Encoder(6, 7);
  
  // VictorSPX motor controllers
	WPI_VictorSPX _leftFront = new WPI_VictorSPX(Constants.LEFTMASTERPORT);
  WPI_VictorSPX _leftFollower = new WPI_VictorSPX(Constants.LEFTSLAVEPORT);
  WPI_VictorSPX _rghtFront = new WPI_VictorSPX(Constants.RIGHTMASTERPORT);
  WPI_VictorSPX _rghtFollower = new WPI_VictorSPX(Constants.RIGHTSLAVEPORT);
  WPI_TalonFX _midMotor = new WPI_TalonFX(Constants.MIDMOTORPORT);

  // Grouped motor controllers
  DifferentialDrive _diffDrive = new DifferentialDrive(_leftFront, _rghtFront);
  
  public void manualDrive(double move, double turn, double slide) {
    
    /* deadband gamepad 10% */
      if (Math.abs(move) < 0.10) {
            move = 0;
      }
      if (Math.abs(turn) < 0.10) {
          turn = 0;
      if (Math.abs(slide) < 0.10){
          slide = 0;
      }
      }
    slide = slide / 1;
    /* drive robot */
    _diffDrive.arcadeDrive(move, turn);
    _midMotor.set(slide);
  }

  // @Override
  public void drivetrainInit() {

    /* factory default values */
    _rghtFront.configFactoryDefault();
    _rghtFollower.configFactoryDefault();
    _leftFront.configFactoryDefault();
    _leftFollower.configFactoryDefault();

    /* set up followers */
    _rghtFollower.follow(_rghtFront);
    _leftFollower.follow(_leftFront);

    /* [3] flip values so robot moves forward when stick-forward/LEDs-green */
    _rghtFront.setInverted(false); // !< Update this
    _leftFront.setInverted(true); // !< Update this

    /*
     * set the invert of the followers to match their respective master controllers
     */
    _rghtFollower.setInverted(InvertType.FollowMaster);
    _leftFollower.setInverted(InvertType.FollowMaster);

    /*
     * [4] adjust sensor phase so sensor moves positive when Talon LEDs are green
     */
    _rghtFront.setSensorPhase(true);
    _leftFront.setSensorPhase(true);

    /*
     * WPI drivetrain classes defaultly assume left and right are opposite. call
     * this so we can apply + to both sides when moving forward. DO NOT CHANGE
     */
    
  }

  public Drivetrain() {
    
  System.out.println("Drivetrain");
    m_rightEncoder.setDistancePerPulse((Math.PI * kWheelDiameterInch) / kCountsPerRevolution);
    m_leftEncoder.setDistancePerPulse((Math.PI * kWheelDiameterInch) / kCountsPerRevolution);
    resetEncoders();
  }
  public void arcadeDrive(double xaxisSpeed, double zaxisRotate) {
    _diffDrive.arcadeDrive(xaxisSpeed, zaxisRotate);
  }

public void resetEncoders() {
  m_leftEncoder.reset();
  m_rightEncoder.reset();
}

public int getLeftEncoderCount() {
  return m_leftEncoder.get();
}

public int getRightEncoderCount() {
  return m_rightEncoder.get();
}

public double getLeftDistanceInch() {
  return m_leftEncoder.getDistance();
}

public double getRightDistanceInch() {
  return m_rightEncoder.getDistance();
}

public double getAverageDistanceInch() {
  return (getLeftDistanceInch() + getRightDistanceInch()) / 2.0;
}

  }


