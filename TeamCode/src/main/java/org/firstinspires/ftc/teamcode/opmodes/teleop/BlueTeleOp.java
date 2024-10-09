package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.ColourAwareIntakeCommand;
import org.firstinspires.ftc.teamcode.commands.DefaultDrive;
import org.firstinspires.ftc.teamcode.commands.DesiredColourBlueCommand;
import org.firstinspires.ftc.teamcode.commands.DesiredColourNeutralCommand;
import org.firstinspires.ftc.teamcode.commands.DesiredColourRedCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeOffCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeSlidesInCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeSlidesOutCommand;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

@TeleOp(name = "Blue TeleOp")
public class BlueTeleOp  extends CommandOpMode {

    private DriveSubsystem m_drive;
    private DefaultDrive m_driveCommand;
    private IntakeSubsystem intakeSubsystem;
    private GamepadEx m_driveDriver;
    private GamepadEx m_driveOperator;
    @Override
    public void initialize() {
        m_drive = new DriveSubsystem(hardwareMap);
        m_driveDriver = new GamepadEx(gamepad1);
        m_driveOperator = new GamepadEx(gamepad2);

        intakeSubsystem = new IntakeSubsystem(hardwareMap);

        m_driveCommand = new DefaultDrive(m_drive, () -> m_driveDriver.getLeftX(),  () -> m_driveDriver.getLeftY(), () -> m_driveDriver.getRightX());

        register(m_drive);
        m_drive.setDefaultCommand(m_driveCommand);

        //TODO: we need to have commands that can set the desired colour

        m_driveDriver.getGamepadButton(GamepadKeys.Button.B).whenPressed(
                new ColourAwareIntakeCommand(intakeSubsystem)

        ).whenReleased(
                new IntakeOffCommand(intakeSubsystem)
        );

        //alex yucky code please delete
        m_driveDriver.getGamepadButton(GamepadKeys.Button.A).whenPressed(
                new IntakeSlidesOutCommand(intakeSubsystem)

        ).whenReleased(
                new IntakeSlidesInCommand(intakeSubsystem)
        );

        m_driveOperator.getGamepadButton(GamepadKeys.Button.X).whenPressed(
                new DesiredColourBlueCommand(intakeSubsystem)
        );

        m_driveOperator.getGamepadButton(GamepadKeys.Button.B).whenPressed(
                new DesiredColourRedCommand(intakeSubsystem)
        );

        m_driveOperator.getGamepadButton(GamepadKeys.Button.Y).whenPressed(
                new DesiredColourNeutralCommand(intakeSubsystem)
        );
    }
}


