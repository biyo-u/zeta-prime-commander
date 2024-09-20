package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.DefaultDrive;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;

@TeleOp(name = "Blue TeleOp")
public class BlueTeleOp  extends CommandOpMode {

    private DriveSubsystem m_drive;
    private DefaultDrive m_driveCommand;

    private GamepadEx m_driverOp;
    @Override
    public void initialize() {
        m_drive = new DriveSubsystem(hardwareMap);
        m_driverOp = new GamepadEx(gamepad1);

        m_driveCommand = new DefaultDrive(m_drive, () -> m_driverOp.getLeftX(),  () -> m_driverOp.getLeftY(), () -> m_driverOp.getRightX());

        register(m_drive);
        m_drive.setDefaultCommand(m_driveCommand);
    }
}


