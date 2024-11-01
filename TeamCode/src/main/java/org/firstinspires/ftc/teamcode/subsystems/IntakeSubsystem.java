package org.firstinspires.ftc.teamcode.subsystems;

import android.graphics.Color;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

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
    private double intakeSlidesOutPosition = 0.1;

    private double intakePivotUpPosition = 0;
    private double intakePivotDownPosition = 0.58;

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

    private Telemetry telemetry;

    private boolean isPooping = true;

    public IntakeSubsystem(final HardwareMap hMap, Telemetry telemetry){

        this.telemetry = telemetry;

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

    public void slowIntake(){
        intakeMotor.setPower(0.3);
    }

    public void intakeSlidesIn() {
        //Brings the slides in
        intakeLeftSlide.setPosition(intakeSlidesInPosition);
        intakeRightSlide.setPosition(intakeSlidesInPosition);
    }

    public void IncrSlides(){
        double currentPos = getIntakeSlidePosition();
        //slides must be decremented
        double newPos = currentPos - 0.01;

        if(newPos < intakeSlidesOutPosition){
            newPos = intakeSlidesOutPosition;
        }
        setIntakeSlidePosition(newPos);
    }

    public boolean hasItemInIntake(){
        telemetry.addData("IntakeColour", getDesiredIntakeColour());
        telemetry.addData("Desired", getDesiredIntakeColour());
        telemetry.addData("Result", getCurrentIntakeColour() == getDesiredIntakeColour());
        telemetry.update();

        return getCurrentIntakeColour() == getDesiredIntakeColour();

    }

    public boolean AreIntakeSlidesIn() {
        return true;
    }

    public void intakeSlidesOut() {
        //Brings the slides out
        intakeLeftSlide.setPosition(intakeSlidesOutPosition);
        intakeRightSlide.setPosition(intakeSlidesOutPosition);
    }

    public void SetPoopMode(boolean mode){
        isPooping = mode;
    }

    public boolean IsPooping(){
        return isPooping;
    }

    public double getIntakeSlidePosition(){
        return intakeLeftSlide.getPosition();
    }

    public void setIntakeSlidePosition(double amount) {
        intakeLeftSlide.setPosition(amount);
        intakeRightSlide.setPosition(amount);
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

        //telemetry.addData("HSV", hsvValues[0]);

        if(hsvValues[0] > 200) {
            if(desiredColour == SampleColour.BLUE_OR_NEUTRAL){
                return SampleColour.BLUE_OR_NEUTRAL;
            }
            return SampleColour.BLUE;
        }
        if(hsvValues[0] >= 70 && hsvValues[0] <=100) {
            if(desiredColour == SampleColour.RED_OR_NEUTRAL){
                return SampleColour.RED_OR_NEUTRAL;
            }
            if(desiredColour == SampleColour.BLUE_OR_NEUTRAL){
                return SampleColour.BLUE_OR_NEUTRAL;
            }
            //telemetry.addData("FOUND", "NEUTRAL");
            //telemetry.update();
            return SampleColour.NEUTRAL;
        }
        if(hsvValues[0] >= 20) {
            if(desiredColour == SampleColour.RED_OR_NEUTRAL){
                return SampleColour.RED_OR_NEUTRAL;
            }
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

    public void setDesiredColour(SampleColour colour){
        desiredColour = colour;
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

    public SampleColour getDesiredIntakeColour(){
        return  desiredColour;
    }


    public void colourAwareIntake(){

            telemetry.addData("Desired:", desiredColour);
            telemetry.update();

            SampleColour currentColour = getCurrentIntakeColour();

            if (currentColour == SampleColour.NONE) {
                poopChuteOpen();
                this.Intake();
            }
            else if(desiredColour == SampleColour.BLUE_OR_NEUTRAL && (currentColour == SampleColour.BLUE || currentColour == SampleColour.NEUTRAL)){
                this.IntakeOff();
            }
            else if(desiredColour == SampleColour.RED_OR_NEUTRAL && (currentColour == SampleColour.RED || currentColour == SampleColour.NEUTRAL)){
                this.IntakeOff();
            }
            else if(getCurrentIntakeColour() != desiredColour){
                    if(!IsPooping()) {
                        this.Outtake();
                    }

            }else {

                this.IntakeOff();
            }
    }
}
