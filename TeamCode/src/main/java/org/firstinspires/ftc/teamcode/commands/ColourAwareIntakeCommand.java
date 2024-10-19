package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TransferSubsystem;

public class ColourAwareIntakeCommand extends CommandBase {

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final IntakeSubsystem intakeSubsystem;



    public ColourAwareIntakeCommand(IntakeSubsystem subsystem) {
        intakeSubsystem = subsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute(){
        //start the colour aware intaking
        intakeSubsystem.colourAwareIntake();
        if(intakeSubsystem.IsPooping()){
            if(intakeSubsystem.getCurrentIntakeColour() != IntakeSubsystem.SampleColour.NONE && intakeSubsystem.getDesiredIntakeColour() != intakeSubsystem.getCurrentIntakeColour()){
                //poop out

                intakeSubsystem.intakePivotUp();
                intakeSubsystem.poopChuteClose();
                try{
                    Thread.sleep(30);
                }catch(Exception err){}
                intakeSubsystem.intakePivotDown();


            }
        }
    }

    @Override
    public boolean isFinished() {
        return intakeSubsystem.getCurrentIntakeColour() == intakeSubsystem.getDesiredIntakeColour();

    }
}
