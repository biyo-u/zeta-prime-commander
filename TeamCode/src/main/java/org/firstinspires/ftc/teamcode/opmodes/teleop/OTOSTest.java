package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.DefaultDrive;
import org.firstinspires.ftc.teamcode.commands.DefaultOTOSDrive;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OTOSDriveSubsystem;

@TeleOp(name = "OTOS TEST")
public class OTOSTest extends CommandOpMode {

    private OTOSDriveSubsystem m_drive;
    private DefaultOTOSDrive m_driveCommand;

    private IntakeSubsystem intakeSubsystem;
    private GamepadEx m_driverOp;
    @Override
    public void initialize() {
        m_drive = new OTOSDriveSubsystem(hardwareMap);
       // intakeSubsystem = new IntakeSubsystem(hardwareMap);
        m_driverOp = new GamepadEx(gamepad1);

        m_driveCommand = new DefaultOTOSDrive(m_drive, () -> m_driverOp.getLeftX(),  () -> m_driverOp.getLeftY(), () -> m_driverOp.getRightX());

        register(m_drive);
        m_drive.setDefaultCommand(m_driveCommand);

        //TODO: we need to have commands that can set the desired colour
       /* m_driverOp.getGamepadButton(GamepadKeys.Button.B).whenPressed(
                new ColourAwareIntakeCommand(intakeSubsystem)
        ).whenReleased(
                new IntakeOffCommand(intakeSubsystem)
        );*/

    }
}


