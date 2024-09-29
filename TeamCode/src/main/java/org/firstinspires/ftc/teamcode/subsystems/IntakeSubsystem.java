package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class IntakeSubsystem extends SubsystemBase {

    //Define motors and servos
    private DcMotor intakeMotor;

    public Servo intakePivot;
    public Servo intakeLeftSlide;
    public Servo intakeRightSlide;

    // Define variables
    private double intakeSlidesInPosition = 0.5;
    private double intakeSlidesOutPosition = 0;

    private int intakePivotUpPosition = 0;
    private int intakePivotDownPosition = 0;

    private SampleColour desiredColour = SampleColour.NONE;

    public enum SampleColour
    {
        NONE,
        RED,
        BLUE,
        NEUTRAL,

        RED_OR_NEUTRAL,

        BLUE_OR_NEUTRAL
    }

    public IntakeSubsystem(final HardwareMap hMap){
        intakeMotor = hMap.get(DcMotor.class, "intakeMotor");

        intakePivot = hMap.get(Servo.class, "pivotIntake");
        intakeLeftSlide = hMap.get(Servo.class, "intakeLeftSlide");
        intakeRightSlide = hMap.get(Servo.class, "intakeRightSlide");

        intakeRightSlide.setDirection(Servo.Direction.REVERSE);
    }

    public void Intake() {
        //Turns the intake on
        intakeMotor.setPower(1);
    }

    public void IntakeOff(){
        intakeMotor.setPower(0);
    }

    public void Outtake() {
        //Revers the intake
        intakeMotor.setPower(-1);
    }

    public void intakeSlidesIn() {
        //Brings the slides in
        intakeLeftSlide.setPosition(intakeSlidesInPosition);
        intakeRightSlide.setPosition(intakeSlidesInPosition);
    }

    public boolean AreIntakeSlidesIn() {
        return true;
    }

    public void intakeSlidesOut() {
        //Brings the slides out
        intakeLeftSlide.setPosition(intakeSlidesOutPosition);
        intakeRightSlide.setPosition(intakeSlidesOutPosition);
    }

    public boolean AreIntakeSlidesOut() {
        return true;
    }

    public void intakePivotUp () {
        intakePivot.setPosition(intakePivotUpPosition);
    }

    public boolean IsIntakePivotedUp() {
        return true;
    }

    public void intakePivotDown () {
        intakePivot.setPosition(intakePivotDownPosition);
    }

    public boolean IsIntakePivotedDown() {
        return true;
    }

    public SampleColour getCurrentIntakeColour(){
        //TODO: add colour detection here.
        //TODO: cache this for 20 - 40 ms
        return SampleColour.NONE;
    }

    public void colourAwareIntake(){

        if(getCurrentIntakeColour() == SampleColour.NONE){
            this.Intake();
        }else if(getCurrentIntakeColour() == desiredColour){
            this.IntakeOff();
        }else{
            this.Outtake();
        }
    }
}
