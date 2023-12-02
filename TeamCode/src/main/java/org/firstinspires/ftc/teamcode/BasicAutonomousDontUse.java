package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Robot: BasicAutonomousDontUse", group="Robot")
@Disabled
public class BasicAutonomousDontUse extends LinearOpMode {

    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        // Initialize the motors
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);

        // Wait for the start button to be pressed
        waitForStart();

        // Move forward for 3 seconds
        leftDrive.setPower(0.5);  // Set the left motor power to half speed forward
        rightDrive.setPower(0.5); // Set the right motor power to half speed forward

        sleep(3000);  // Wait for 3 seconds (adjust the time as needed)

        // Stop the motors
        leftDrive.setPower(0.0);
        rightDrive.setPower(0.0);
    }
}