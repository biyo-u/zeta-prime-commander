package org.firstinspires.ftc.teamcode.opmodes.auto;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.commands.ActionCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeOnCommand;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

@Autonomous(name = "BasketAuto", group = "Autonomous")
public class BasketAuto  extends CommandOpMode {

    Action dropOffAction;
    Action parkAction;

    IntakeSubsystem intakeSubsystem;

    @Override
    public void initialize() {

        intakeSubsystem = new IntakeSubsystem(hardwareMap);

        // instantiate your MecanumDrive at a particular pose.
        MecanumDrive drive = new MecanumDrive(hardwareMap,
                new Pose2d(11.8, 61.7, Math.toRadians(90)));

        dropOffAction = drive.actionBuilder(drive.pose)
                .splineToSplineHeading( new Pose2d(11.1, 8.9, Math.toRadians(90)), Math.toRadians(90))
                .build();

        parkAction = drive.actionBuilder(drive.pose)
                .splineToSplineHeading( new Pose2d(11.1, 8.9, Math.toRadians(90)), Math.toRadians(90))
                .build();


        CommandScheduler.getInstance().schedule(
                new WaitUntilCommand(this::isStarted).andThen(
                    new SequentialCommandGroup(
                            new ActionCommand(dropOffAction, null),
                            new IntakeOnCommand(intakeSubsystem),
                            new ActionCommand(parkAction, null)
                    )
                )
        );

    }

}
