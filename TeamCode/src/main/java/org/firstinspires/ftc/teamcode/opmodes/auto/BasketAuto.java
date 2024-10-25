package org.firstinspires.ftc.teamcode.opmodes.auto;
import androidx.collection.ArraySet;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
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
import org.firstinspires.ftc.teamcode.utils.OTOSDrive;

@Autonomous(name = "BasketAuto", group = "Autonomous")
public class BasketAuto  extends CommandOpMode {

    Action moveToChamberAction;
    Action moveToSample1Action;

    Action moveToBasket1Action;

    IntakeSubsystem intakeSubsystem;

    @Override
    public void initialize() {

        intakeSubsystem = new IntakeSubsystem(hardwareMap, telemetry);

        // instantiate your MecanumDrive at a particular pose.
        OTOSDrive drive = new OTOSDrive(hardwareMap,
                new Pose2d(11.8, 61.7, Math.toRadians(90)));

        moveToChamberAction = drive.actionBuilder(drive.pose)
                .splineToConstantHeading(new Vector2d(0,35), Math.toRadians(90)) .build();

        moveToSample1Action = drive.actionBuilder(drive.pose)
                .splineToLinearHeading(new Pose2d(50,40,Math.toRadians(270)),Math.toRadians(270))
                .build();

        moveToBasket1Action =   drive.actionBuilder(drive.pose).splineToLinearHeading(new Pose2d(55,50,Math.toRadians(230)),Math.toRadians(230))
                        .build();



        CommandScheduler.getInstance().schedule(
                new WaitUntilCommand(this::isStarted).andThen(
                    new SequentialCommandGroup(
                            new ActionCommand(moveToChamberAction, new ArraySet<>()),
                            new ActionCommand(moveToSample1Action, new ArraySet<>()),
                            new ActionCommand(moveToBasket1Action, new ArraySet<>())
                    )
                )
        );

    }

}
