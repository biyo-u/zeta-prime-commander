package org.firstinspires.ftc.teamcode.opmodes.auto;
import androidx.collection.ArraySet;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.ActionCommand;
import org.firstinspires.ftc.teamcode.commands.AscentOpenHooksCommand;
import org.firstinspires.ftc.teamcode.commands.CloseGripplerCommand;
import org.firstinspires.ftc.teamcode.commands.ColourAwareIntakeCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeOffCommand;
import org.firstinspires.ftc.teamcode.commands.IntakePivotDownCommand;
import org.firstinspires.ftc.teamcode.commands.IntakePivotUpCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeSlidesInCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeSlidesOutCommand;
import org.firstinspires.ftc.teamcode.commands.OpenGripplerCommand;
import org.firstinspires.ftc.teamcode.commands.SlidesStowCommand;
import org.firstinspires.ftc.teamcode.commands.TransferFlipCommand;
import org.firstinspires.ftc.teamcode.commands.TransferStowCommand;
import org.firstinspires.ftc.teamcode.commands.groups.DeliveryCommandGroup;
import org.firstinspires.ftc.teamcode.commands.groups.DeliveryResetCommandGroup;
import org.firstinspires.ftc.teamcode.commands.groups.IntakeCommandGroup;
import org.firstinspires.ftc.teamcode.subsystems.AscentSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.RobotStateSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SlidesSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TransferSubsystem;
import org.firstinspires.ftc.teamcode.utils.OTOSDrive;

@Autonomous(name = "NewSpecimenAuto", group = "Autonomous")
public class NewSpecimenAuto extends CommandOpMode {

    Action deliverPreload;

    Action moveToHumanPlayer;

    Action deliverSecondSpecimen;
    Action park;

    TrajectoryActionBuilder part1;

    TrajectoryActionBuilder part2;

    Action pleaseWork;
    IntakeSubsystem intakeSubsystem;

    TransferSubsystem transferSubsystem;

    RobotStateSubsystem robotState;

    SlidesSubsystem slidesSubsystem;

    AscentSubsystem ascentSubsystem;

    @Override
    public void initialize() {

        intakeSubsystem = new IntakeSubsystem(hardwareMap, telemetry);
        transferSubsystem = new TransferSubsystem(hardwareMap);
        robotState = new RobotStateSubsystem();
        slidesSubsystem = new SlidesSubsystem(hardwareMap);
        ascentSubsystem = new AscentSubsystem(hardwareMap);


        // instantiate your MecanumDrive at a particular pose.
        OTOSDrive drive = new OTOSDrive(hardwareMap,
                new Pose2d(14.8, -64, Math.toRadians(-90)));

        //pose to the submersible wall
        Pose2d dropOffPose = new Pose2d(3.4, -32.5, Math.toRadians(-90));

        deliverPreload = drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(dropOffPose, Math.toRadians(90))
                .build();

        moveToHumanPlayer = drive.actionBuilder(new Pose2d(3.4,-32.5,Math.toRadians(90)))
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(new Pose2d(50,-55,Math.toRadians(-260)),Math.toRadians(0))
                .build();

        deliverSecondSpecimen = drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(dropOffPose, Math.toRadians(90))
                .build();

        park = drive.actionBuilder(new Pose2d(3.4,-32.5,Math.toRadians(-90)))
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(new Pose2d(55,-65,Math.toRadians(90)),Math.toRadians(0))
                .build();

        intakeSubsystem.setDesiredColour(IntakeSubsystem.SampleColour.NEUTRAL);
        intakeSubsystem.intakePivotDown();
        transferSubsystem.closeGrippler();


        CommandScheduler.getInstance().schedule(
                new WaitUntilCommand(this::isStarted).andThen(
                        new SequentialCommandGroup(

                                new ActionCommand(deliverPreload, new ArraySet<>()),

                                new WaitCommand(100),
                                new TransferFlipCommand(transferSubsystem),
                                new WaitCommand(400),
                                new SequentialCommandGroup(

                                        new OpenGripplerCommand(transferSubsystem),
                                        new TransferStowCommand(transferSubsystem),

                                        new IntakePivotUpCommand(intakeSubsystem, robotState),
                                        new SlidesStowCommand(slidesSubsystem),
                                        new InstantCommand(()->{
                                            slidesSubsystem.NoPowerSlides();
                                        })
                                ),
                                // specimen is now delivered

                                /*new ParallelCommandGroup(
                                        new ActionCommand(moveToHumanPlayer, new ArraySet<>()),
                                        new DeliveryResetCommandGroup(intakeSubsystem,transferSubsystem,slidesSubsystem, robotState)
                                ),

                                new WaitCommand(100),
                                new ActionCommand(deliverSecondSpecimen, new ArraySet<>()),
                                new TransferFlipCommand(transferSubsystem),
                                        new WaitCommand(400),
                                        new SequentialCommandGroup(

                                                new OpenGripplerCommand(transferSubsystem),
                                                new TransferStowCommand(transferSubsystem),

                                                new IntakePivotUpCommand(intakeSubsystem, robotState),
                                                new SlidesStowCommand(slidesSubsystem),
                                                new InstantCommand(()->{
                                                    slidesSubsystem.NoPowerSlides();
                                                })
                                        ),

                                //wait for skibidi toilet
                                new WaitCommand(1000),*/

                                new ParallelCommandGroup(
                                    new ActionCommand(park, new ArraySet<>()),
                                    new DeliveryResetCommandGroup(intakeSubsystem,transferSubsystem,slidesSubsystem, robotState)
                                )
                        )
                )
        );

    }

}
