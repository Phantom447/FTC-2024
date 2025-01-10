package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.robot;

@Config
@TeleOp(name="teleport operations")
public class TeleopPIDMain extends LinearOpMode {
    robot robo;

    public static double intakeSpool = 0.4;
    public static double scoringSpool = 1;

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
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            robo.leftFront.setPower(y + x + rx);
            robo.leftBack.setPower(y - x + rx);
            robo.rightFront.setPower(y - x - rx);
            robo.rightBack.setPower(y + x - rx);


            if (gamepad1.y) {
                robo.Hori(.75);
            } else if (gamepad1.a) {
                robo.Hori(-.75);
            } else {
                robo.Hori(0);
            }
            if (gamepad1.right_trigger > 0.1) {
                robo.intake(0.5);
            } else if (gamepad1.right_trigger == 0) {
                robo.intake(-0.5);
            } else {
                robo.intake(0);
            }
            if(gamepad2.x){
                robo.arm(.9, .3);
            } else if(gamepad2.b){
                robo.arm(.3, .9);
            }
            if (gamepad2.y) {
                robo.Hori(.75);
            } else if (gamepad2.a) {
                robo.Hori(-.75);
            } else {
                robo.Hori(0);
            }
            if(gamepad2.right_trigger > 0.1) {
                robo.claw(.3);
            } else if(gamepad2.right_trigger == 0){
                robo.claw(0);
            }
            if(gamepad2.left_bumper){
                robo.gate(0.3);
            } else if(gamepad1.left_bumper){
                robo.gate(0.65);
            }



        }
    }
}