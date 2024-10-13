package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class TransferSubsystem extends SubsystemBase {

    //Define motors and servos
    private Servo armServo;
    private Servo gripplerServo;
    private Servo griggleWristServo;

    // Define variables
    private double stowedTransferPosition = 0;
    private double flippedPosition = 0;
    private double middleGripplerRotation = 0;
    private double leftGripplerRotation = 0;
    private double rightGripplerRotation = 0;
    private double closedGripplerPosition = 0;
    private double openGripplerPosition = 0;

    public TransferSubsystem(final HardwareMap hMap) {
        armServo = hMap.get(Servo.class, "arm");
        gripplerServo = hMap.get(Servo.class, "grippler");
        griggleWristServo = hMap.get(Servo.class, "griggleWrist");
    }

    public void stowTransfer() {
        armServo.setPosition(stowedTransferPosition);
    }

    public boolean IsTransferStowed() {
        return true;
    }

    public void flipTransfer() {
        armServo.setPosition(flippedPosition);
    }

    public boolean IsTransferFlipped() {
        return true;
    }

    public void middleGripplerRotation() {
        griggleWristServo.setPosition(middleGripplerRotation);
    }

    public boolean IsGripplerAtMiddleRotation() {
        return true;
    }

    public void leftGripplerRotation() {
        griggleWristServo.setPosition(leftGripplerRotation);
    }

    public boolean IsGripplerAtLeftRotation() {
        return true;
    }

    public void rightGripplerRotation() {
        griggleWristServo.setPosition(rightGripplerRotation);
    }

    public boolean IsGripplerAtRightRotation() {
        return true;
    }

    public void openGrippler() {
        gripplerServo.setPosition(openGripplerPosition);
    }

    public boolean IsGripplerOpen() {
        return true;
    }

    public void closeGrippler() {
        gripplerServo.setPosition(closedGripplerPosition);
    }

    public boolean IsGripplerClosed() {
        return true;
    }
}