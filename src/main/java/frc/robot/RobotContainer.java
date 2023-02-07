package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import frc.robot.subsystems.Drivetrain;


public class RobotContainer {
  NetworkTableInstance inst = NetworkTableInstance.getDefault();
  NetworkTable table = inst.getTable("datatable");
  NetworkTableEntry tx = table.getEntry("tx");
  NetworkTableEntry ty = table.getEntry("ty");
  NetworkTableEntry ta = table.getEntry("ta");
  NetworkTableEntry tv = table.getEntry("tv");
  
  // The robot's subsystems
  private final Drivetrain m_drivetrain = new Drivetrain ();

  // The robot's commands
  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser;

  // Sticks
  private final XboxController m_driverstick = new XboxController(Constants.DRIVERSTICKPORT);


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    //read values periodically
    double limelightx = tx.getDouble(0.0);
    double limelighty = ty.getDouble(0.0);
    double area = ta.getDouble(0.0);
    double limelightnum = tv.getDouble(0.0);
    System.out.println("network2" + tx);
    System.out.println("network" + tv);
    System.out.print("Init");

    SmartDashboard.putNumber("tx", limelightx);
    SmartDashboard.putNumber("ty", limelighty);
    SmartDashboard.putNumber("ta", area);
    SmartDashboard.putNumber("tv", limelightnum);

    System.out.println("robot container");

    // Configure the button bindings
    configureButtonBindings();
	
	  // Initialize drivetrain
	  m_drivetrain.drivetrainInit();

    // Set default Teleop command
    m_drivetrain.setDefaultCommand(
      new RunCommand(() -> m_drivetrain.manualDrive(-m_driverstick.getLeftY(), m_driverstick.getRightX(), m_driverstick.getLeftX()), m_drivetrain));
       
    // Setup autonomous select commands
    m_chooser = new SendableChooser<>();

    SmartDashboard.putData(m_chooser);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
  }
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */

  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    m_autonomousCommand = m_chooser.getSelected();
    return m_autonomousCommand;
  }
}
