package org.firstinspires.ftc.teamcode.NEWsubsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class ViperSlideSubsystem  extends SubsystemBase {

    // our two hardworking motors (keep up the good work lads)

    private  DcMotor liftMotorLeft;
    private  DcMotor liftMotorRight;

    private double MaxPower = 0.4;
    private int descendlimit = 0;
        private int extendlimit = 1000; // TODO: find the proper extendlimit so robot no die

    public ViperSlideSubsystem(final HardwareMap hw){
        liftMotorLeft = hw.get(DcMotor.class, "liftMotorLeft");
        liftMotorRight = hw.get(DcMotor.class, "liftMotorRight");

        liftMotorLeft.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    /** the full extent is 0% of the way or in initialized state **/
    public void ZeroPercent(){

        liftMotorLeft.setTargetPosition(descendlimit);
        liftMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotorLeft.setPower(MaxPower);

        liftMotorRight.setTargetPosition(descendlimit);
        liftMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotorRight.setPower(MaxPower);
    }

    /** the full extent is 50% of the way or half of max height **/

    public void FiftyPercent(){

        liftMotorLeft.setTargetPosition(extendlimit/2);
        liftMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotorLeft.setPower(MaxPower);

        liftMotorRight.setTargetPosition(extendlimit/2);
        liftMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotorRight.setPower(MaxPower);
    }
    /** the full extent is 100% of the way or at max height **/

    public void HundredPercent(){

        liftMotorLeft.setTargetPosition(extendlimit);
        liftMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotorLeft.setPower(MaxPower);

        liftMotorRight.setTargetPosition(extendlimit);
        liftMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotorRight.setPower(MaxPower);
    }

}
