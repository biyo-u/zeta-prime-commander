package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class SlidesSubsystem extends SubsystemBase {

    //Define motors and servos
    private DcMotor verticalSlideMotor;

    // Define variables
    private int stowedSlidesPosition = 0;
    private int backwardsTransferPosition = 0;
    private int lowChamberPosition = 150;
    private int highChamberPosition = 550;
    private int lowBasketPosition = 610;
    private int highBasketPosition = 1720;

    private int deliverHighChamberPosition = 400;

    public SlidesSubsystem(final HardwareMap hMap) {
        verticalSlideMotor = hMap.get(DcMotor.class, "verticalSlidesMotor");
        verticalSlideMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void stowSlides() {
        verticalSlideMotor.setTargetPosition(stowedSlidesPosition);
        verticalSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        verticalSlideMotor.setPower(1);
    }

    public boolean AreSlidesStowed() {
        return verticalSlideMotor.getCurrentPosition() < (stowedSlidesPosition + 50);
    }

    public void highChamberDeliver(){
        verticalSlideMotor.setTargetPosition(deliverHighChamberPosition);
        verticalSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        verticalSlideMotor.setPower(1);
    }

    public boolean IsAtHighChamberDeliver(){
        return verticalSlideMotor.getCurrentPosition() < (deliverHighChamberPosition +50);
    }

    public void backwardsTransfer() {
        verticalSlideMotor.setTargetPosition(backwardsTransferPosition);
        verticalSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        verticalSlideMotor.setPower(1);
    }

    public boolean AreSlidesAllowingBackwardsTransfer() {
        return verticalSlideMotor.getCurrentPosition() > (backwardsTransferPosition - 50);
    }

    public void lowChamber() {
        verticalSlideMotor.setTargetPosition(lowChamberPosition);
        verticalSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        verticalSlideMotor.setPower(1);
    }

    public boolean IsAtLowChamber() {
        return verticalSlideMotor.getCurrentPosition() > (lowChamberPosition - 50);
    }
    public void highChamber() {
        verticalSlideMotor.setTargetPosition(highChamberPosition);
        verticalSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        verticalSlideMotor.setPower(1);
    }

    public boolean IsAtHighChamber() {
        return verticalSlideMotor.getCurrentPosition() > (highChamberPosition - 50);
    }

    public void lowBasket() {
        verticalSlideMotor.setTargetPosition(lowBasketPosition);
        verticalSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        verticalSlideMotor.setPower(1);
    }

    public boolean IsAtLowBasket() {
        return verticalSlideMotor.getCurrentPosition() > (lowBasketPosition - 50);
    }
    public void highBasket() {
        verticalSlideMotor.setTargetPosition(highBasketPosition);
        verticalSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        verticalSlideMotor.setPower(1);
    }

    public boolean IsAtHighBasket() {
        return verticalSlideMotor.getCurrentPosition() > (highBasketPosition - 50);
    }
}