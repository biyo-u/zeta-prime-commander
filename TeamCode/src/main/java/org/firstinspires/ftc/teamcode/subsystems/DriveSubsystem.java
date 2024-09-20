package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveSubsystem extends SubsystemBase {

    private MecanumDrive mecanum;
    RevIMU imu;


    public DriveSubsystem(final HardwareMap hMap){

        imu = new RevIMU(hMap);

        Motor frontLeft =  new Motor(hMap, "frontLeft", Motor.GoBILDA.RPM_435);
        Motor frontRight =  new Motor(hMap, "frontRight", Motor.GoBILDA.RPM_435);
        Motor backLeft =  new Motor(hMap, "backLeft", Motor.GoBILDA.RPM_435);
        Motor backRight =  new Motor(hMap, "backRight", Motor.GoBILDA.RPM_435);

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
