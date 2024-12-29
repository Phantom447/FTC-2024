package org.firstinspires.ftc.teamcode.Autos;

import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.MecanumDrive;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@Autonomous(name = "RedSpicemen", group = "Autonomous")
public class RedSpicemen extends LinearOpMode {

    public class Claw {
        private Servo claw;

        public Claw(HardwareMap hardwareMap) {
            claw = hardwareMap.get(Servo.class, "llama");
        }

        public class CloseClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                claw.setPosition(0.55);
                return false;
            }
        }
        public Action closeClaw() {
            return new CloseClaw();
        }

        public class OpenClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                claw.setPosition(1.0);
                return false;
            }
        }
        public Action openClaw() {
            return new OpenClaw();
        }
    }

    @Override
    public void runOpMode() {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(12, -59.5, Math.toRadians(-90)));
        Claw claw = new Claw(hardwareMap);

        // vision here that outputs position

        Action trajectoryAction1;
        Action trajectoryAction2;
        Action trajectoryAction3;
        Action trajectoryActionCloseOut;

//  v      trajectoryAction1 = drive.actionBuilder(drive.pose)
//                .lineToYSplineHeading(33, Math.toRadians(0))
//                .waitSeconds(2)
//                .setTangent(Math.toRadians(90))
//                .lineToY(48)
//                .setTangent(Math.toRadians(0))
//                .lineToX(32)
//                .strafeTo(new Vector2d(44.5, 30))
//                .turn(Math.toRadians(180))
//                .lineToX(47.5)
//                .waitSeconds(3)
//                .build();
        trajectoryActionCloseOut = drive.actionBuilder(drive.pose)
                .setTangent(-90)
                .setReversed(true)
                .strafeTo(new Vector2d(1, -35))
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
                .build();

        // actions that need to happen on init; for instance, a claw tightening.
        Actions.runBlocking(claw.closeClaw());


        while (!isStopRequested() && !opModeIsActive()) {
            telemetry.update();
        }

//        int startPosition = visionOutputPosition;
//        telemetry.addData("Starting Position", startPosition);
        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

//        Action trajectoryActionChosen  = trajectoryAction1;
//        if (startPosition == 1) {
//            trajectoryActionChosen = trajectoryAction1;
//        } else if (startPosition == 2) {
//            trajectoryActionChosen = trajectoryAction2;
//        } else {
//            trajectoryActionChosen = trajectoryAction3;
//        }

        Actions.runBlocking(
                new SequentialAction(
                        trajectoryActionCloseOut
                )
        );
    }
}