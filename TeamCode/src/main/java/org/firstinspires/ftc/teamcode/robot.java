/* Copyright (c) 2022 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file works in conjunction with the External Hardware Class sample called: ConceptExternalHardwareClass.java
 * Please read the explanations in that Sample about how to use this class definition.
 *
 * This file defines a Java Class that performs all the setup and configuration for a sample robot's hardware (motors and sensors).
 * It assumes three motors (left_drive, right_drive and arm) and two servos (left_hand and right_hand)
 *
 * This one file/class can be used by ALL of your OpModes without having to cut & paste the code each time.
 *
 * Where possible, the actual hardware objects are "abstracted" (or hidden) so the OpMode code just makes calls into the class,
 * rather than accessing the internal hardware directly. This is why the objects are declared "private".
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with *exactly the same name*.
 *
 * Or.. In OnBot Java, add a new file named RobotHardware.java, drawing from this Sample; select Not an OpMode.
 * Also add a new OpMode, drawing from the Sample ConceptExternalHardwareClass.java; select TeleOp.
 *
 */
@Config
public class robot {
    public LinearOpMode myOpMode;
    public OpMode notMyopMode;
    public DcMotorEx leftFront, leftBack, rightFront, rightBack, lift, arm;
    public ServoImplEx claw;
    public double error;
    public  double output;
    public double currentPos;
    public robot(LinearOpMode opmode) {
        myOpMode = opmode;
    }
    public robot(OpMode opmode) {
        notMyopMode = opmode;
    }


    public void init() {
        claw = myOpMode.hardwareMap.get(ServoImplEx.class, "llama");
        arm = myOpMode.hardwareMap.get(DcMotorEx.class, "arm");
        lift = myOpMode.hardwareMap.get(DcMotorEx.class, "lift");
        leftFront = myOpMode.hardwareMap.get(DcMotorEx.class, "leftFront");
        leftBack = myOpMode.hardwareMap.get(DcMotorEx.class, "leftBack");
        rightFront = myOpMode.hardwareMap.get(DcMotorEx.class, "rightFront");
        rightBack = myOpMode.hardwareMap.get(DcMotorEx.class, "rightBack");

        claw.setPosition(1);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
        arm.setDirection(DcMotorSimple.Direction.REVERSE);


        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        setMotorPowers(0);
//        FrontLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//        FrontRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//        BackLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//        BackRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);



        // reset encoders so they chill
          lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
          arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

          lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
          arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setMotorPowers(double speed) {
        leftFront.setPower(speed);
        leftBack.setPower(speed);
        rightFront.setPower(speed);
        rightBack.setPower(speed);
    }

    public void workingPIDup(){
        ElapsedTime timer = new ElapsedTime();
            ElapsedTime oneSec = new ElapsedTime();
                currentPos = (lift.getCurrentPosition());
                error = LiftUtil.target - currentPos;
                LiftUtil.integralSum += error;
//                double derivative = (error - LiftUtil.lastError) / timer.seconds();
//                output = (LiftUtil.LIFTP * error) + (LiftUtil.LIFTI * LiftUtil.integralSum) + (LiftUtil.LIFTD * derivative) + (LiftUtil.LIFTA);
                output = (LiftUtil.LIFTP * error) + (LiftUtil.LIFTI * LiftUtil.integralSum) + (LiftUtil.LIFTA);
                lift.setPower(output);
                LiftUtil.lastError = error;
        }



    public void setMotorPowers(double s1, double s2, double s3, double s4) {
        leftFront.setPower(s1);
        leftBack.setPower(s2);
        rightFront.setPower(s3);
        rightBack.setPower(s4);
    }

}