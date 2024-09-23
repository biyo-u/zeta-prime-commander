package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;

public class RobotStateSubsystem extends SubsystemBase {


    public IntakeSubsystem.SampleColour desiredSampleColour(){
        return IntakeSubsystem.SampleColour.NEUTRAL;
    }
}
