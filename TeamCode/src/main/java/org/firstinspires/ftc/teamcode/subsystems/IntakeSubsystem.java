package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class IntakeSubsystem extends SubsystemBase {

    //Define motors and servos
    private DcMotor intakeMotor;
    private DcMotor intakeSlideMotor;

    public Servo intakePivot;

    // Define variables
    private int intakeSlidesInPosition = 0;
    private int intakeSlidesOutPosition = 0;

    private int intakePivotUpPosition = 0;
    private int intakePivotDownPosition = 0;

    public IntakeSubsystem(final HardwareMap hMap){
        intakeMotor = hMap.get(DcMotor.class, "intakeMotor");
        intakeSlideMotor = hMap.get(DcMotor.class, "intakeSlideMotor");

        intakePivot = hMap.get(Servo.class, "pivotIntake");
    }

    public void Intake() {
        //Turns the intake on
        intakeMotor.setPower(1);
    }

    public void Outtake() {
        //Revers the intake
        intakeMotor.setPower(-1);
    }

    public void intakeSlidesIn() {
        //Brings the slides in
        intakeSlideMotor.setTargetPosition(intakeSlidesInPosition);
        intakeSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        intakeSlideMotor.setPower(1);
    }

    public boolean AreIntakeSlidesIn() {
        return intakeSlideMotor.getCurrentPosition() < (intakeSlidesInPosition + 50);
    }

    public void intakeSlidesOut() {
        //Brings the slides out
        intakeSlideMotor.setTargetPosition(intakeSlidesOutPosition);
        intakeSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        intakeSlideMotor.setPower(1);
    }

    public boolean AreIntakeSlidesOut() {
        return intakeSlideMotor.getCurrentPosition() > (intakeSlidesInPosition - 50);
    }

    public void intakePivotUp () {
        intakePivot.setPosition(intakePivotUpPosition);
    }

    public boolean IsIntakePivotedUp() {
        return intakePivot.getPosition() > (intakePivotUpPosition - 0.3);
    }

    public void intakePivotDown () {
        intakePivot.setPosition(intakePivotDownPosition);
    }

    public boolean IsIntakePivotedDown() {
        return intakePivot.getPosition() < (intakePivotDownPosition + 0.3);
    }
}
