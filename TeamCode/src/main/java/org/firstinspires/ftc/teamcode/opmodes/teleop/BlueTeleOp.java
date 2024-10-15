package org.firstinspires.ftc.teamcode.opmodes.teleop;

import android.transition.Slide;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.CloseGripplerCommand;
import org.firstinspires.ftc.teamcode.commands.ColourAwareIntakeCommand;
import org.firstinspires.ftc.teamcode.commands.DefaultDrive;
import org.firstinspires.ftc.teamcode.commands.DesiredColourBlueCommand;
import org.firstinspires.ftc.teamcode.commands.DesiredColourNeutralCommand;
import org.firstinspires.ftc.teamcode.commands.DesiredColourRedCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeOffCommand;
import org.firstinspires.ftc.teamcode.commands.IntakePivotDownCommand;
import org.firstinspires.ftc.teamcode.commands.IntakePivotUpCommand;
import org.firstinspires.ftc.teamcode.commands.IntakePoopChuteCloseCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeSlidesInCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeSlidesOutCommand;
import org.firstinspires.ftc.teamcode.commands.OpenGripplerCommand;
import org.firstinspires.ftc.teamcode.commands.OuttakeOnCommand;
import org.firstinspires.ftc.teamcode.commands.SlidesHighBasketCommand;
import org.firstinspires.ftc.teamcode.commands.SlidesHighChamberCommand;
import org.firstinspires.ftc.teamcode.commands.SlidesStowCommand;
import org.firstinspires.ftc.teamcode.commands.TransferFlipCommand;
import org.firstinspires.ftc.teamcode.commands.TransferStowCommand;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SlidesSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TransferSubsystem;

import java.util.function.BooleanSupplier;

@TeleOp(name = "Blue TeleOp")
public class BlueTeleOp  extends CommandOpMode {
// rightn trigger: basket
    // left trigger slides out and intake
    // right bumper speciman chamber
    private DriveSubsystem m_drive;
    private DefaultDrive m_driveCommand;
    private IntakeSubsystem intakeSubsystem;
    private TransferSubsystem transferSubsystem;
    private SlidesSubsystem slidesSubsystem;
    private GamepadEx m_driveDriver;
    private GamepadEx m_driveOperator;
    @Override
    public void initialize() {
        m_drive = new DriveSubsystem(hardwareMap);
        m_driveDriver = new GamepadEx(gamepad1);
        m_driveOperator = new GamepadEx(gamepad2);

        intakeSubsystem = new IntakeSubsystem(hardwareMap);
        transferSubsystem = new TransferSubsystem(hardwareMap);
        slidesSubsystem = new SlidesSubsystem(hardwareMap);

        m_driveCommand = new DefaultDrive(m_drive, () -> m_driveDriver.getLeftX(),  () -> m_driveDriver.getLeftY(), () -> m_driveDriver.getRightX());

        register(m_drive);
        m_drive.setDefaultCommand(m_driveCommand);

        //TODO: we need to have commands that can set the desired colour

        //Intake
        new Trigger(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return m_driveDriver.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.5;
            }
        }).whenActive(
                new SequentialCommandGroup(
                    new OpenGripplerCommand(transferSubsystem),
                    new ColourAwareIntakeCommand(intakeSubsystem),
                    new IntakePoopChuteCloseCommand(intakeSubsystem),
                    new IntakeSlidesOutCommand(intakeSubsystem),
                    new IntakePivotDownCommand(intakeSubsystem)
                )
        ).whenInactive(
            new SequentialCommandGroup(
                new IntakeOffCommand(intakeSubsystem),
                new IntakePoopChuteCloseCommand(intakeSubsystem),
                new IntakePivotUpCommand(intakeSubsystem),
                new WaitCommand(300),
                new IntakeSlidesInCommand(intakeSubsystem),
                new ConditionalCommand(
                    new WaitCommand(1),
                    new ParallelCommandGroup(
                        new WaitCommand(800),
                        new CloseGripplerCommand(transferSubsystem),
                        new IntakePivotDownCommand(intakeSubsystem)
                    ),
                    () ->{
                        return intakeSubsystem.getCurrentIntakeColour() == IntakeSubsystem.SampleColour.NONE;
                    }
                )
            )
        );

        //High basket sample
        new Trigger(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return m_driveDriver.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.5;
            }
        }).whenActive(
            new ConditionalCommand(
                new WaitCommand(1),
                new ParallelCommandGroup(
                    new SlidesHighBasketCommand(slidesSubsystem),
                    new TransferFlipCommand(transferSubsystem)
                ),
                () ->{
                    return intakeSubsystem.getCurrentIntakeColour() == IntakeSubsystem.SampleColour.NONE;
                }
            )).whenInactive(
                new SequentialCommandGroup(
                    new OpenGripplerCommand(transferSubsystem),
                    new ParallelCommandGroup(
                        new TransferStowCommand(transferSubsystem),
                        new SlidesStowCommand(slidesSubsystem)
                    ),
                new IntakePivotUpCommand(intakeSubsystem)
                )
        );

        //High chamber specimen
        m_driveDriver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(
                new ConditionalCommand(
                        new WaitCommand(1),
                        new ParallelCommandGroup(
                                new SlidesHighChamberCommand(slidesSubsystem),
                                new TransferFlipCommand(transferSubsystem)
                        ),
                        () ->{
                            return intakeSubsystem.getCurrentIntakeColour() == IntakeSubsystem.SampleColour.NONE;
                        }
                )).whenInactive(
                new SequentialCommandGroup(
                        new OpenGripplerCommand(transferSubsystem),
                        new ParallelCommandGroup(
                                new TransferStowCommand(transferSubsystem),
                                new SlidesStowCommand(slidesSubsystem)
                        ),
                        new IntakePivotUpCommand(intakeSubsystem)
                )
        );

        //alex yucky code please delete
        /*m_driveDriver.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(
              new SequentialCommandGroup(
                new IntakeSlidesOutCommand(intakeSubsystem),
                new WaitCommand(300),
                new IntakePivotDownCommand(intakeSubsystem)
              )

        );*/

        m_driveDriver.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(
              new SequentialCommandGroup(
                      new IntakePivotUpCommand(intakeSubsystem),
                      new WaitCommand(500),
                      new IntakeSlidesInCommand(intakeSubsystem)
              )
        );

        m_driveDriver.getGamepadButton(GamepadKeys.Button.B).whenPressed(
                new OuttakeOnCommand(intakeSubsystem)
        ).whenReleased(
                new IntakeOffCommand(intakeSubsystem)
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


