package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 18)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(12, -59.5, Math.toRadians(-90)))
                .setTangent(-90)
                .setReversed(true)
                .waitSeconds(0.1)
                .strafeTo(new Vector2d(0, -35))
                .strafeTo(new Vector2d(35, -35))
                .waitSeconds(0.1)
                .lineToY(-12)
                .strafeTo(new Vector2d(43, -12))
                .waitSeconds(0.1)
                .lineToY(-50)
                .waitSeconds(0.1)
                .lineToY(-12)
                .strafeTo(new Vector2d(50, -12))
                .waitSeconds(0.1)
                .lineToY(-50)
                .strafeTo(new Vector2d(34.5, -60))
                .strafeTo(new Vector2d(0, -35))
                .strafeTo(new Vector2d(34.5, -60))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}