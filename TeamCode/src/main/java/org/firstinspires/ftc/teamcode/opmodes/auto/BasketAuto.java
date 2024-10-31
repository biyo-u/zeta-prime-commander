package org.firstinspires.ftc.teamcode.opmodes.auto;
import androidx.collection.ArraySet;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.commands.ActionCommand;
import org.firstinspires.ftc.teamcode.commands.CloseGripplerCommand;
import org.firstinspires.ftc.teamcode.commands.ColourAwareIntakeCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeOnCommand;
import org.firstinspires.ftc.teamcode.commands.IntakePivotDownCommand;
import org.firstinspires.ftc.teamcode.commands.IntakePivotUpCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeSlidesOutCommand;
import org.firstinspires.ftc.teamcode.commands.OpenGripplerCommand;
import org.firstinspires.ftc.teamcode.commands.SlidesHighBasketCommand;
import org.firstinspires.ftc.teamcode.commands.SlidesStowCommand;
import org.firstinspires.ftc.teamcode.commands.TransferFlipCommand;
import org.firstinspires.ftc.teamcode.commands.TransferStowCommand;
import org.firstinspires.ftc.teamcode.commands.groups.IntakeCommandGroup;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.RobotStateSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SlidesSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TransferSubsystem;
import org.firstinspires.ftc.teamcode.utils.OTOSDrive;

@Autonomous(name = "BasketAuto", group = "Autonomous")
public class BasketAuto  extends CommandOpMode {

    Action dropOffPreload;
    Action firstSample;

    Action firstSampleSlowMoveIn;
    Action deliverFirstSample;
    Action deliverFirstSampleMoveIn;
    Action secondSample;
    Action secondSampleSlowMoveIn;
    Action deliverSecondSample;
    Action deliverSecondSampleMoveIn;
    Action thirdSample;
    Action thirdSampleSlowMoveIn;
    Action deliverThirdSample;
    Action park;

    TrajectoryActionBuilder part1;

    TrajectoryActionBuilder part2;

    Action pleaseWork;
    IntakeSubsystem intakeSubsystem;

    TransferSubsystem transferSubsystem;

    RobotStateSubsystem robotState;

    SlidesSubsystem slidesSubsystem;

    @Override
    public void initialize() {

        intakeSubsystem = new IntakeSubsystem(hardwareMap, telemetry);
        transferSubsystem = new TransferSubsystem(hardwareMap);
        robotState = new RobotStateSubsystem();
        slidesSubsystem = new SlidesSubsystem(hardwareMap);


        // instantiate your MecanumDrive at a particular pose.
        OTOSDrive drive = new OTOSDrive(hardwareMap,
                new Pose2d(-14.8, -64, Math.toRadians(-90)));

        //pose to the submersible wall
        Pose2d dropOffPose = new Pose2d(-3.5, -34, Math.toRadians(-90));

        dropOffPreload = drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(dropOffPose, Math.toRadians(90))
                .build();

        //pose to the first sample
        Pose2d firstSamplePose = new Pose2d(-26,-55,Math.toRadians(-240));

        firstSample = drive.actionBuilder(dropOffPose)
                .setTangent(Math.toRadians(-131))
                .splineToLinearHeading(firstSamplePose,Math.toRadians(-131))
                .build();

        //pose after the slow move in
        Pose2d slowMoveForward = new Pose2d(-26, -49, Math.toRadians(-240));

        firstSampleSlowMoveIn = drive.actionBuilder(firstSamplePose)
                .setTangent(Math.toRadians(-240))
                .splineToLinearHeading(slowMoveForward,Math.toRadians(-240), new TranslationalVelConstraint(10))
                 .build();

        Pose2d deliverPose = new Pose2d(-50,-55,Math.toRadians(-315));

        deliverFirstSample = drive.actionBuilder(slowMoveForward)
                //pick up the first sample
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(deliverPose,Math.toRadians(180))
                .build();

        Pose2d deliverFirstSampleMoveInPose = new Pose2d(-53,-58, Math.toRadians(-315));

        deliverFirstSampleMoveIn = drive.actionBuilder(deliverPose)
                .splineToLinearHeading(deliverFirstSampleMoveInPose, Math.toRadians(-90))
                .build();

        secondSample = drive.actionBuilder(deliverPose)
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-52,-59,Math.toRadians(-277)),Math.toRadians(90))
                .build();

        secondSampleSlowMoveIn = drive.actionBuilder(new Pose2d(-52,-59,Math.toRadians(-277)))
                .splineToLinearHeading(new Pose2d(-52,-54,Math.toRadians(-277)), Math.toRadians(90), new TranslationalVelConstraint(10))
                .build();

        deliverSecondSample = drive.actionBuilder(new Pose2d(-52,-54,Math.toRadians(-277)))
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(new Pose2d(-50,-55,Math.toRadians(-315)),Math.toRadians(-90))
                .build();

        deliverSecondSampleMoveIn = drive.actionBuilder(new Pose2d(-50,-55,Math.toRadians(-315)))
                .splineToLinearHeading(deliverFirstSampleMoveInPose, Math.toRadians(-90))
                .build();

        thirdSample = drive.actionBuilder(new Pose2d(-50,-55,Math.toRadians(-315)))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-55,-59,Math.toRadians(-277)),Math.toRadians(90))
                .build();

        thirdSampleSlowMoveIn = drive.actionBuilder(new Pose2d(-55,-59,Math.toRadians(-277)))
                .splineToLinearHeading(new Pose2d(-55,-50,Math.toRadians(-277)), Math.toRadians(90), new TranslationalVelConstraint(10))
                .build();

        deliverThirdSample = drive.actionBuilder(new Pose2d(-55,-50,Math.toRadians(-277)))
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(new Pose2d(-55,-45,Math.toRadians(-315)),Math.toRadians(-90))
                .build();

        park = drive.actionBuilder(new Pose2d(-53,-50,Math.toRadians(-315)))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-30,-10,Math.toRadians(180)),Math.toRadians(0))
                .build();



        /*pleaseWork = drive.actionBuilder(drive.pose)
                //Drop Off Specimen
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-8.5, -35, Math.toRadians(-90)), Math.toRadians(90))
                .waitSeconds(0.5)

                //To first sample
                .setTangent(Math.toRadians(-131))
                .splineToLinearHeading(new Pose2d(-25,-50,Math.toRadians(-235)),Math.toRadians(-131))
                .waitSeconds(0.5)

                //Drop first sample
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(-50,-62,Math.toRadians(-315)),Math.toRadians(180))
                .waitSeconds(0.5)

                //To second sample
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-50,-55,Math.toRadians(-270)),Math.toRadians(90))
                .waitSeconds(0.5)

                //Drop second sample
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(new Pose2d(-50,-62,Math.toRadians(-315)),Math.toRadians(-90))
                .waitSeconds(0.5)

                //To third sample
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-40,-50,Math.toRadians(-230)),Math.toRadians(90))
                .waitSeconds(0.5)

                //Drop third sample
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(new Pose2d(-50,-62,Math.toRadians(-315)),Math.toRadians(-90))
                .waitSeconds(0.5)

                //Park
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-17,-10,Math.toRadians(180)),Math.toRadians(45))
                .build();

        Action builtPart1 = part1.build();
        Action builtPart2 = part2.build();
        */

        intakeSubsystem.intakePivotDown();
        transferSubsystem.closeGrippler();


        CommandScheduler.getInstance().schedule(
                new WaitUntilCommand(this::isStarted).andThen(
                    new SequentialCommandGroup(

                            new ActionCommand(dropOffPreload, new ArraySet<>()),

                            new WaitCommand(200),
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


                            new ActionCommand(firstSample, new ArraySet<>()),
                            new ParallelCommandGroup(
                                new SequentialCommandGroup(
                                        new IntakeSlidesOutCommand(intakeSubsystem),
                                        new IntakePivotDownCommand(intakeSubsystem, robotState),
                                        new ColourAwareIntakeCommand(intakeSubsystem).withTimeout(3000)
                                ),
                                new SequentialCommandGroup(
                                        new WaitCommand(500), //time to settle in
                                        new ActionCommand(firstSampleSlowMoveIn, new ArraySet<>())
                                )
                            ),
                            new WaitCommand(500), //TODO - use the sensor
                            new ConditionalCommand(
                                    new IntakeCommandGroup(intakeSubsystem, transferSubsystem, robotState),
                                    new InstantCommand(), //just retracting with no sample - do nothing
                                    () -> intakeSubsystem.hasItemInIntake()
                            ),
                            new ActionCommand(deliverFirstSample, new ArraySet<>()),

                            new SequentialCommandGroup(
                                    new CloseGripplerCommand(transferSubsystem),
                                    new WaitCommand(200),
                                    new ParallelCommandGroup(
                                            //new SlidesHighBasketCommand(slidesSubsystem),
                                            new TransferFlipCommand(transferSubsystem)
                                    )
                            ),

                           new ActionCommand(deliverFirstSampleMoveIn, new ArraySet<>()),
                           new OpenGripplerCommand(transferSubsystem),
                           new WaitCommand(500),
                           //new SlidesStowCommand(slidesSubsystem),
                           new TransferStowCommand(transferSubsystem),



                            new ActionCommand(secondSample, new ArraySet<>()),
                            new ParallelCommandGroup(
                                    new SequentialCommandGroup(
                                            new IntakeSlidesOutCommand(intakeSubsystem),
                                            new IntakePivotDownCommand(intakeSubsystem, robotState),
                                            new ColourAwareIntakeCommand(intakeSubsystem).withTimeout(3000)
                                    ),
                                    new SequentialCommandGroup(
                                            new WaitCommand(500), //time to settle in
                                            new ActionCommand(secondSampleSlowMoveIn, new ArraySet<>())
                                    )
                            ),
                            new WaitCommand(500), //TODO - use the sensor
                            new ConditionalCommand(
                                    new IntakeCommandGroup(intakeSubsystem, transferSubsystem, robotState),
                                    new InstantCommand(), //just retracting with no sample - do nothing
                                    () -> intakeSubsystem.hasItemInIntake()
                            ),
                            new ActionCommand(deliverSecondSample, new ArraySet<>()),

                            new SequentialCommandGroup(
                                    new CloseGripplerCommand(transferSubsystem),
                                    new WaitCommand(200),
                                    new ParallelCommandGroup(
                                            //new SlidesHighBasketCommand(slidesSubsystem),
                                            new TransferFlipCommand(transferSubsystem)
                                    )
                            ),

                            new ActionCommand(deliverFirstSampleMoveIn, new ArraySet<>()),
                            new OpenGripplerCommand(transferSubsystem),
                            new WaitCommand(500),
                            //new SlidesStowCommand(slidesSubsystem),
                            new TransferStowCommand(transferSubsystem),




                            new ActionCommand(thirdSample, new ArraySet<>()),
                            new ParallelCommandGroup(
                                    new SequentialCommandGroup(
                                            new IntakeSlidesOutCommand(intakeSubsystem),
                                            new IntakePivotDownCommand(intakeSubsystem, robotState),
                                            new ColourAwareIntakeCommand(intakeSubsystem).withTimeout(3000)
                                    ),
                                    new SequentialCommandGroup(
                                            new WaitCommand(500), //time to settle in
                                            new ActionCommand(thirdSampleSlowMoveIn, new ArraySet<>())
                                    )
                            ),
                            new WaitCommand(500), //TODO - use the sensor
                            new ConditionalCommand(
                                    new IntakeCommandGroup(intakeSubsystem, transferSubsystem, robotState),
                                    new InstantCommand(), //just retracting with no sample - do nothing
                                    () -> intakeSubsystem.hasItemInIntake()
                            ),
                            new ActionCommand(deliverThirdSample, new ArraySet<>()),

                            new SequentialCommandGroup(
                                    new CloseGripplerCommand(transferSubsystem),
                                    new WaitCommand(200),
                                    new ParallelCommandGroup(
                                            //new SlidesHighBasketCommand(slidesSubsystem),
                                            new TransferFlipCommand(transferSubsystem)
                                    )
                            ),

                            new ActionCommand(deliverFirstSampleMoveIn, new ArraySet<>()),
                            new OpenGripplerCommand(transferSubsystem),
                            new WaitCommand(500),
                            //new SlidesStowCommand(slidesSubsystem),
                            new TransferStowCommand(transferSubsystem),



                            new ActionCommand(park, new ArraySet<>())

                    )
                )
        );

    }

}
