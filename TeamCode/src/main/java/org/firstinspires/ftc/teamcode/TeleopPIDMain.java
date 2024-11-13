package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

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
            robo.workingPIDup();
            telemetry.update();

        }
            while (opModeIsActive()) {
                int arm_pos = robo.arm.getCurrentPosition();
                int lift_pos = robo.lift.getCurrentPosition();
                double y = gamepad1.left_stick_y;
                double x = gamepad1.left_stick_x;
                double rx = gamepad1.right_stick_x;

                double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
                double rf = (y - x - rx) / denominator;
                double rb = (y + x - rx) / denominator;
                double lf = (y + x + rx) / denominator;
                double lb = (y - x + rx) / denominator;
                boolean fitsLeftTriggerReq = gamepad1.left_trigger > .05 && lift_pos <= 1000;
                boolean fitsRightTriggerReq = gamepad1.right_trigger > .05;

                robo.setMotorPowers(rf, rb, lf, lb);
                if (fitsLeftTriggerReq) {//lift up
                    robo.lift.setPower(.3);
                } else if (fitsRightTriggerReq) {//lift down
                    robo.lift.setPower(-.3);
                } else {
                    robo.lift.setPower(0);
                }
                if (gamepad1.left_bumper && arm_pos < 800) {
                    robo.arm.setPower(.5);
                } else if (gamepad1.right_bumper) {
                    robo.arm.setPower(-.5);
                } else {
                    robo.arm.setPower(0);
                }
                if (gamepad1.x) {
                    robo.workingPIDup();
                }
                if (gamepad1.b) {
                    robo.claw.setPosition(0.15);
                }

                if (gamepad1.a) {
                    robo.claw.setPosition(0.95);
                }
                if (gamepad1.b) {
                    robo.claw.setPosition(0.15);
                }

                telemetry.addData("claw position", robo.claw.getPosition());
                telemetry.addData("encoder position", robo.lift.getCurrentPosition());/*1300*/
                telemetry.addData("lt boolean", fitsLeftTriggerReq);
                telemetry.addData("rt boolean", fitsRightTriggerReq);

                telemetry.update();
        }
    }
}