package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class AscentSubsystem extends SubsystemBase {

    //Define motors and servos
    private DcMotor ascentLeftMotor;
    private DcMotor ascentRightMotor;

    public Servo ascentLeftHook;
    public Servo ascentRightHook;

    // Define variables
    private int ascentStowedPosition = 0;
    private int ascentLowRungPreparedPosition = 0;
    private int ascentLowRungPosition = 0;
    private int ascentHighRungPosition = 0;
    private int ascentFinishedLevel3Position = 0;

    private int ascentOpenHookPosition = 0;
    private int ascentClosedHookPosition = 0;

    public AscentSubsystem(final HardwareMap hMap) {
        ascentLeftMotor = hMap.get(DcMotor.class, "ascentLeftMotor");
        ascentRightMotor = hMap.get(DcMotor.class, "ascentRightMotor");

        ascentLeftHook = hMap.get(Servo.class, "ascentLeftHook");
        ascentRightHook = hMap.get(Servo.class, "ascentRightHook");
    }

    public void ascentStow() {
        //Stows the ascent mechanism
        ascentLeftMotor.setTargetPosition(ascentStowedPosition);
        ascentRightMotor.setTargetPosition(ascentStowedPosition);
        ascentLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ascentRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ascentLeftMotor.setPower(1);
        ascentRightMotor.setPower(1);
    }

    public boolean IsAscentStowed() {
        return (ascentLeftMotor.getCurrentPosition() > (ascentStowedPosition - 50)) && (ascentRightMotor.getCurrentPosition() > (ascentStowedPosition - 50));
    }

    public void ascentLowRungPrepare() {
        //Stows the ascent mechanism
        ascentLeftMotor.setTargetPosition(ascentLowRungPreparedPosition);
        ascentRightMotor.setTargetPosition(ascentLowRungPreparedPosition);
        ascentLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ascentRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ascentLeftMotor.setPower(1);
        ascentRightMotor.setPower(1);
    }

    public boolean IsAscentPrepared() {
        return (ascentLeftMotor.getCurrentPosition() > (ascentLowRungPreparedPosition - 50)) && (ascentRightMotor.getCurrentPosition() > (ascentLowRungPreparedPosition - 50));
    }

    public void ascentLowRung() {
        //Stows the ascent mechanism
        ascentLeftMotor.setTargetPosition(ascentLowRungPosition);
        ascentRightMotor.setTargetPosition(ascentLowRungPosition);
        ascentLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ascentRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ascentLeftMotor.setPower(1);
        ascentRightMotor.setPower(1);
    }

    public boolean IsAscentLowRung() {
        return (ascentLeftMotor.getCurrentPosition() > (ascentLowRungPosition - 50)) && (ascentRightMotor.getCurrentPosition() > (ascentLowRungPosition - 50));
    }

    public void ascentHighRung() {
        //Stows the ascent mechanism
        ascentLeftMotor.setTargetPosition(ascentHighRungPosition);
        ascentRightMotor.setTargetPosition(ascentHighRungPosition);
        ascentLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ascentRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ascentLeftMotor.setPower(1);
        ascentRightMotor.setPower(1);
    }

    public boolean IsAscentHighRung() {
        return (ascentLeftMotor.getCurrentPosition() > (ascentHighRungPosition - 50)) && (ascentRightMotor.getCurrentPosition() > (ascentHighRungPosition - 50));
    }

    public void ascentFinishLevel3() {
        //Stows the ascent mechanism
        ascentLeftMotor.setTargetPosition(ascentFinishedLevel3Position);
        ascentRightMotor.setTargetPosition(ascentFinishedLevel3Position);
        ascentLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ascentRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ascentLeftMotor.setPower(1);
        ascentRightMotor.setPower(1);
    }

    public boolean IsAscentFinishedLevel3() {
        return (ascentLeftMotor.getCurrentPosition() > (ascentFinishedLevel3Position - 50)) && (ascentRightMotor.getCurrentPosition() > (ascentFinishedLevel3Position - 50));
    }

    public void ascentOpenHooks() {
        ascentLeftHook.setPosition(ascentOpenHookPosition);
        ascentRightHook.setPosition(ascentOpenHookPosition);
    }

    public boolean AreAscentHooksOpen() {
        return true;
    }

    public void ascentCloseHooks() {
        ascentLeftHook.setPosition(ascentClosedHookPosition);
        ascentRightHook.setPosition(ascentClosedHookPosition);
    }

    public boolean AreAscentHooksClosed() {
        return true;
    }
}