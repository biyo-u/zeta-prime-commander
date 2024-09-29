package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveSubsystem extends SubsystemBase {

    private MecanumDrive mecanum;
    RevIMU imu;


    public DriveSubsystem(final HardwareMap hMap){

        imu = new RevIMU(hMap);
        Motor frontLeft =  new Motor(hMap, "leftFront", Motor.GoBILDA.RPM_435);
        Motor frontRight =  new Motor(hMap, "rightFront", Motor.GoBILDA.RPM_435);
        Motor backLeft =  new Motor(hMap, "leftBack", Motor.GoBILDA.RPM_435);
        Motor backRight =  new Motor(hMap, "rightBack", Motor.GoBILDA.RPM_435);

        backLeft.setInverted(true);
        frontLeft.setInverted(true);
        backRight.setInverted(true);

        mecanum = new MecanumDrive(frontLeft, frontRight,
                backLeft, backRight);

        imu.init();
    }

    public void drive(double leftX, double leftY, double rightX){

        mecanum.driveFieldCentric(
                leftX, leftY, rightX,  imu.getRotation2d().getDegrees(), false
        );
    }
}
