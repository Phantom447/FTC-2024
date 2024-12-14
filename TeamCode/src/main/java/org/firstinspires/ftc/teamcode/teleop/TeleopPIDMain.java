package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.robot;

@TeleOp(name="teleport operations")
public class TeleopPIDMain extends LinearOpMode {
    robot robo;

    @Override
    public void runOpMode() {
        robo = new robot(this);
        robo.init();

        while (opModeInInit()) {
            telemetry.addLine("starting");
            telemetry.addData("lift pos", robo.lift.getCurrentPosition());
            telemetry.addData("lift power", robo.lift.getPower()); /*1000*/
            /*1000*/
            robo.arm.setTargetPosition(1);
            telemetry.update();

        }
        while (opModeIsActive()) {
            int arm_pos = robo.arm.getCurrentPosition() * -1;
            int lift_pos = robo.lift.getCurrentPosition();
            double y = gamepad2.left_stick_y;
            double x = gamepad2.left_stick_x;
            double rx = gamepad2.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double rf = (y - x - rx) / denominator;
            double rb = (y + x - rx) / denominator;
            double lf = (y + x + rx) / denominator;
            double lb = (y - x + rx) / denominator;
            boolean fitsLeftTriggerReq = gamepad1.left_trigger > .053 && lift_pos <= 2800;
            boolean fitsRightTriggerReq = gamepad1.right_trigger > .05;
            boolean fullArmExtension = gamepad1.a && lift_pos > 1100 && Math.abs(arm_pos) < 2750;
            boolean halfArmExtension = gamepad1.a &&  Math.abs(arm_pos) < 800;
            if(gamepad2.right_trigger > 0.1){
                robo.setMotorPowers(rf * 0.2, rb * 0.2, lf * 0.2, lb * 0.2);
            } else {
                robo.setMotorPowers(rf, rb, lf, lb);
            }
            if (fitsLeftTriggerReq) {//lift up
                robo.lift.setPower(.3);
            } else if (gamepad1.right_trigger > .05) {//lift down
                robo.lift.setPower(-.3);
            } else {
                robo.lift.setPower(0);
            }
            if (fullArmExtension || halfArmExtension) {
                robo.arm.setPower(.5);
            } else if (gamepad1.b) {
                robo.arm.setPower(-.5);
            } else {
                robo.arm.setPower(0);
            }

            if (gamepad1.x) {
                robo.workingPIDup(85);
            }
            if (gamepad1.y) {
                robo.claw.setPosition(robo.close_pos);
                robo.workingPIDup(1800);
            }

            if (gamepad1.right_bumper) {
                robo.claw.setPosition(robo.close_pos);
            }
            if (gamepad1.left_bumper) {
                    robo.claw.setPosition(robo.open_pos);
            }

                telemetry.addData("claw position", robo.claw.getPosition());
                telemetry.addData("lift position", robo.lift.getCurrentPosition());/*1300*/
                telemetry.addData("arm position", robo.arm.getCurrentPosition());/*1300*/
                telemetry.addData("lt boolean", fitsLeftTriggerReq);
                telemetry.addData("rt boolean", fitsRightTriggerReq);

                telemetry.update();
            }
        }
    }
