package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="teleport operations")
public class TeleopPIDMain extends LinearOpMode {
    robot robo;
    @Override
    public void runOpMode() {
        while (opModeInInit()){
            telemetry.addLine("starting");
            telemetry.update();
            robo = new robot(this);
            robo.init();
        }
        while (opModeIsActive()){
            double y = -gamepad1.right_stick_y;
            double x = gamepad1.right_stick_x;
            double rx = gamepad1.left_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double rf = (y - x - rx) / denominator;
            double rb = (y + x - rx) / denominator;
            double lf = (y + x + rx) / denominator;
            double lb = (y - x + rx) / denominator;

            robo.setMotorPowers(rf, rb, lf, lb);

            if (gamepad1.left_trigger>.1){
                robo.lift.setPower(.5);
            }
            if (gamepad1.right_trigger>.1){
                robo.lift.setPower(-.5);
            }

        }
    }
}