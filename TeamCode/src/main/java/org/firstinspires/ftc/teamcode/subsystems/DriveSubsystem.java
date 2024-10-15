package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utils.OTOSDrive;

public class DriveSubsystem extends SubsystemBase {

    private MecanumDrive mecanum;

    private OTOSDrive drive;
    RevIMU imu;


    public DriveSubsystem(final HardwareMap hMap){

        imu = new RevIMU(hMap);

       /* Motor frontLeft =  new Motor(hMap, "leftFront", Motor.GoBILDA.RPM_435);
        Motor frontRight =  new Motor(hMap, "rightFront", Motor.GoBILDA.RPM_435);
        Motor backLeft =  new Motor(hMap, "leftBack", Motor.GoBILDA.RPM_435);
        Motor backRight =  new Motor(hMap, "rightBack", Motor.GoBILDA.RPM_435);

        backLeft.setInverted(true);
        frontLeft.setInverted(true);

        backRight.setInverted(true);

        mecanum = new MecanumDrive(frontLeft, frontRight,
                backLeft, backRight);*/

        drive = new OTOSDrive(hMap,new Pose2d(0,0,0));

        imu.init();
    }

    public void drive(double leftX, double leftY, double rightX){

        /*mecanum.driveFieldCentric(
                leftX, leftY, rightX,  imu.getRotation2d().getDegrees(), false
        );*/

        drive.setDrivePowers(new PoseVelocity2d(
                new Vector2d(
                        leftY,
                        -leftX
                ),
                -rightX
        ));

        drive.updatePoseEstimate();


    }
}
