package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="pidding")
public class TeleopPIDMain extends LinearOpMode {
    DcMotorEx LF;
    DcMotorEx LB;
    DcMotorEx RF;
    DcMotorEx RB;
//    DcMotorEx leftlift = hardwareMap.get(DcMotorEx.class, "leftlift");
//    DcMotorEx rightlift = hardwareMap.get(DcMotorEx.class, "rightlift");
//    ServoImplEx claw = hardwareMap.get(ServoImplEx.class, "claw");


    @Override
    public void runOpMode() {
        LF = hardwareMap.get(DcMotorEx.class, "LF");
        RF = hardwareMap.get(DcMotorEx.class, "RF");
        LB = hardwareMap.get(DcMotorEx.class, "LB");
        RB = hardwareMap.get(DcMotorEx.class, "RB");
        telemetry.addLine("Ready for start!");
        telemetry.update();

        double error;
        double output;

        double currentPos;

        waitForStart();

        Gamepad currentGamepad = new Gamepad();
        Gamepad previousGamepad = new Gamepad();

        while (opModeIsActive()) {
            previousGamepad.copy(currentGamepad);
            currentGamepad.copy(gamepad1);
            ElapsedTime timer = new ElapsedTime();

            //chassis
            double y = -gamepad1.right_stick_y;
            double x = gamepad1.right_stick_x;
            double rx = gamepad1.left_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double rf = (y - x - rx) / denominator;
            double rb = (y + x - rx) / denominator;
            double lf = (y + x + rx) / denominator;
            double lb = (y - x + rx) / denominator;

            RF.setPower(rf);
            RB.setPower(rb);
            LF.setPower(lf);
            LB.setPower(lb);

//            if (gamepad1.left_bumper) {leftlift.setPower(1);rightlift.setPower(1);}
//            else if (gamepad1.right_bumper) {leftlift.setPower(-1);rightlift.setPower(-1);}
//            else {leftlift.setPower(0);rightlift.setPower(0);}

//            if(gamepad1.x){claw.setPosition(1);}
            telemetry.addData("rx",gamepad1.right_stick_x);
            telemetry.update();
        }
    }
}