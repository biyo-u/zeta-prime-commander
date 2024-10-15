package org.firstinspires.ftc.teamcode.subsystems;

import android.graphics.Color;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;

public class IntakeSubsystem extends SubsystemBase {

    //Define motors and servos
    private DcMotor intakeMotor;

    private Servo intakeLeftPivot;
    private Servo intakeRightPivot;
    private Servo intakeLeftSlide;
    private Servo intakeRightSlide;

    private Servo poopChute;

    // Define the colour sensor
    private NormalizedColorSensor colourSensor;

    // Define variables
    private double intakeSlidesInPosition = 0.5;
    private double intakeSlidesOutPosition = 0;

    private double intakePivotUpPosition = 0;
    private double intakePivotDownPosition = 0.5;

    private double intakePoopOpen = 0.5;

    private double intakePoopClose = 0;

    private final float[] hsvValues = new float[3];

    private SampleColour desiredColour = SampleColour.NEUTRAL;

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

        intakeLeftPivot = hMap.get(Servo.class, "pivotLeftIntake");
        intakeRightPivot = hMap.get(Servo.class, "pivotRightIntake");
        intakeLeftSlide = hMap.get(Servo.class, "intakeLeftSlide");
        intakeRightSlide = hMap.get(Servo.class, "intakeRightSlide");
        poopChute = hMap.get(Servo.class, "poopChute");

        colourSensor = hMap.get(NormalizedColorSensor.class, "colourSensor");

        intakeLeftPivot.setDirection(Servo.Direction.REVERSE);

        intakeRightSlide.setDirection(Servo.Direction.REVERSE);

        intakePivotUp();
        intakeSlidesIn();
        poopChuteClose();

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

    public void intakePivotUp() {
        intakeLeftPivot.setPosition(intakePivotUpPosition);
        intakeRightPivot.setPosition(intakePivotUpPosition);
    }

    public boolean IsIntakePivotedUp() {
        return true;
    }

    public void intakePivotDown() {
        intakeLeftPivot.setPosition(intakePivotDownPosition);
        intakeRightPivot.setPosition(intakePivotDownPosition);
    }

    public boolean IsIntakePivotedDown() {
        return true;
    }

    public void poopChuteOpen() {
        poopChute.setPosition(intakePoopOpen);
    }

    public boolean IsPoopChuteOpened(){
        return true;
    }

    public void poopChuteClose() {
        poopChute.setPosition(intakePoopClose);
    }

    public boolean IsPoopChuteClosed(){
        return true;
    }

    public SampleColour getCurrentIntakeColour(){
        //TODO: add colour detection here.
        //TODO: cache this for 20 - 40 ms
        NormalizedRGBA colors = colourSensor.getNormalizedColors();
        Color.colorToHSV(colors.toColor(), hsvValues);

        if(hsvValues[0] > 200) {
            return SampleColour.BLUE;
        }
        if(hsvValues[0] >= 70 && hsvValues[0] <=100) {
            return SampleColour.NEUTRAL;
        }
        if(hsvValues[0] >= 20) {
            return SampleColour.RED;
        }
        return SampleColour.NONE;
    }

    public void setDesiredColourBlue() {
        desiredColour = SampleColour.BLUE;
    }

    public boolean IsDesiredColourBlueSet() {
        return true;
    }

    public void setDesiredColourRed() {
        desiredColour = SampleColour.RED;
    }

    public boolean IsDesiredColourRedSet() {
        return true;
    }

    public void setDesiredColourNeutral() {
        desiredColour = SampleColour.NEUTRAL;
    }

    public boolean IsDesiredColourNeutralSet() {
        return true;
    }



    public void colourAwareIntake(){

            if (getCurrentIntakeColour() == SampleColour.NONE) {
                this.Intake();
            } else if(getCurrentIntakeColour() != desiredColour){
                this.Outtake();
            }else {

                this.IntakeOff();
            }
    }
}
