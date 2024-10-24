package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepBasket {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        MeepMeep meepMeep = new MeepMeep(500,120);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(50, 50, Math.toRadians(180), Math.toRadians(180), 15)
                .setDimensions(18,18)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(38, 64, Math.toRadians(90)))

                        .splineToConstantHeading(new Vector2d(0,35), Math.toRadians(90))
                //drop off specimen

                        .splineToLinearHeading(new Pose2d(50,40,Math.toRadians(270)),Math.toRadians(270))
                //pick up the first sample
                    .splineToLinearHeading(new Pose2d(55,50,Math.toRadians(230)),Math.toRadians(230))
                //drop off in the basket
                     .splineToLinearHeading(new Pose2d(58,40,Math.toRadians(270)),Math.toRadians(270))

                .splineToLinearHeading(new Pose2d(55,50,Math.toRadians(230)),Math.toRadians(230))
                //drop off in the basket

                .splineToLinearHeading(new Pose2d(58,40,Math.toRadians(320)),Math.toRadians(320))

                .splineToLinearHeading(new Pose2d(53,50,Math.toRadians(230)),Math.toRadians(230))


                .splineToLinearHeading(new Pose2d(30,10,Math.toRadians(0)),Math.toRadians(0))


                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();


    }
}