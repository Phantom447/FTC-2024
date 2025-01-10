package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        Pose2d red1 = new Pose2d(10,70,Math.toRadians(-90));
        Pose2d red2 = new Pose2d(-10,70,Math.toRadians(-90));
        Pose2d blue1 = new Pose2d(10,-70,Math.toRadians(-90));
        Pose2d blue2 = new Pose2d(-10,70,Math.toRadians(-90));
        MeepMeep meepMeep = new MeepMeep(800);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(red1)
                        .forward(45)
                        //claw thing
                        .turn(Math.toRadians(90))
                        .forward(35)
                        .turn(Math.toRadians(90))
                        //picker the upper :)))))
                        .forward(35)
                        //putter the downer
                        .back(35)
                        .turn(Math.toRadians(-90))
                        .forward(10)
                        //picker the upper again
                        .turn(Math.toRadians(90))
                        .forward(35)
                        //putter the downer again
                        .back(35)
                        .turn(Math.toRadians(-90))
                        .forward(10)
                        //picker the upper again again
                        .turn(Math.toRadians(90))
                        .forward(35)
                        //putter the downer again again
                        .back(50)
                        .turn(Math.toRadians(90))
                        .forward(35)
                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}