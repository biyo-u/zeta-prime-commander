package org.firstinspires.ftc.teamcode.opmodes.auto;
import androidx.collection.ArraySet;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.RepeatCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.ActionCommand;
import org.firstinspires.ftc.teamcode.commands.IntakePivotUpCommand;
import org.firstinspires.ftc.teamcode.commands.IntakePoopChuteOpenCommand;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.utils.OTOSDrive;

@Autonomous(name = "SpecimenAuto", group = "Autonomous")
public class SpecimenAuto extends CommandOpMode {

    private IntakeSubsystem intakeSubsystem;

    private Action leftSampleAction;
    private Action middleSampleAction;
    private Action rightSampleAction;
    private Action rightSampleToPickupAction;
    private Action chamberDropOffAction;
    private Action specimenPickUpAction;
    private Action parkAction;
    @Override
    public void initialize() {

        // instantiate your MecanumDrive at a particular pose.
        OTOSDrive drive = new OTOSDrive(hardwareMap,
                new Pose2d(25, -61, Math.toRadians(90)));

        leftSampleAction = drive.actionBuilder(drive.pose)
                .strafeToConstantHeading(new Vector2d(47, -44))
                .waitSeconds(0.25)
                .build();

        middleSampleAction = drive.actionBuilder(drive.pose)
                .turnTo(Math.toRadians(70))
                .waitSeconds(0.25)
                .build();

        rightSampleAction = drive.actionBuilder(drive.pose)
                .strafeToConstantHeading(new Vector2d(57,-44))
                .waitSeconds(0.25)
                .build();

        rightSampleToPickupAction = drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(35, -49, Math.toRadians(-45)), Math.toRadians(-135))
                .waitSeconds(0.25)
                .build();

        chamberDropOffAction = drive.actionBuilder(drive.pose)
                .strafeTo(new Vector2d(12,-37))
                .waitSeconds(0.25)
                .build();

        specimenPickUpAction = drive.actionBuilder(drive.pose)
                .strafeTo(new Vector2d(35, -49))
                .waitSeconds(0.25)
                .build();

        parkAction = drive.actionBuilder(drive.pose)
                .strafeTo(new Vector2d(39, -53))
                .waitSeconds(0.25)
                .build();

        CommandScheduler.getInstance().schedule(
                new WaitUntilCommand(this::isStarted).andThen(
                        new SequentialCommandGroup(
                                new ActionCommand(leftSampleAction, new ArraySet<>()),
                                new ActionCommand(middleSampleAction, new ArraySet<>()),
                                new ActionCommand(rightSampleAction, new ArraySet<>()),
                                new ActionCommand(rightSampleToPickupAction, new ArraySet<>()),
                                new ActionCommand(chamberDropOffAction, new ArraySet<>()),
                                new ActionCommand(specimenPickUpAction, new ArraySet<>()),
                                new ActionCommand(chamberDropOffAction, new ArraySet<>()),
                                new ActionCommand(specimenPickUpAction, new ArraySet<>()),
                                new ActionCommand(chamberDropOffAction, new ArraySet<>()),
                                new ActionCommand(specimenPickUpAction, new ArraySet<>()),
                                new ActionCommand(chamberDropOffAction, new ArraySet<>()),
                                new ActionCommand(specimenPickUpAction, new ArraySet<>()),
                                new ActionCommand(chamberDropOffAction, new ArraySet<>()),
                                new ActionCommand(parkAction, new ArraySet<>())
                        )
                )
        );

    }

}