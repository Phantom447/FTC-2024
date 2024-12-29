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
            /*1000*/
            telemetry.update();

        }
        while (opModeIsActive()) {
            double y = gamepad2.left_stick_y;
            double x = gamepad2.left_stick_x;
            double rx = gamepad2.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double rf = (y - x - rx) / denominator;
            double rb = (y + x - rx) / denominator;
            double lf = (y + x + rx) / denominator;
            double lb = (y - x + rx) / denominator;
            if (gamepad2.right_trigger > 0.1) {
                robo.setMotorPowers(rf * 0.2, rb * 0.2, lf * 0.2, lb * 0.2);
            } else {
                robo.setMotorPowers(rf, rb, lf, lb);

                if (gamepad1.right_bumper) {
                    robo.claw.setPosition(robo.close_pos);
                }
                if (gamepad1.left_bumper) {
                    robo.claw.setPosition(robo.open_pos);
                }

                telemetry.addData("claw position", robo.claw.getPosition());
                telemetry.update();
            }
        }
    }
}