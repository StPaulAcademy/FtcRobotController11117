package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.util.concurrent.TimeUnit;

@Autonomous(name="Robot: BasicAutonomousDontUse", group="Taylor Swift")
//@Disabled
public class BasicAutonomousDontUse extends LinearOpMode {

    private DcMotor frontLeftMotor = null;
    private DcMotor frontRightMotor = null;
    private DcMotor backLeftMotor = null;
    private DcMotor backRightMotor = null;
    private final int READ_PERIOD = 1;
    static final double     COUNTS_PER_MOTOR    = 537.7 ;    // eg: GoBilda Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // No External Gearing.
    static final double     WHEEL_DIAMETER_INCHES   = 3.77953 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;
    private ElapsedTime runtime = new ElapsedTime();

    String husky;

    @Override
    public void runOpMode() {
        // Initialize the motors
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        HuskyLens huskyLens = hardwareMap.get(HuskyLens.class, "huskylens");
        Deadline rateLimit = new Deadline(READ_PERIOD, TimeUnit.SECONDS);
        rateLimit.expire();


        //Setting directions
        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);

        //Resets Encoder
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Sets mode to encoder
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        /*
         * All algorithms, except for LINE_TRACKING, return a list of Blocks where a
         * Block represents the outline of a recognized object along with its ID number.
         * ID numbers allow you to identify what the device saw.  See the HuskyLens documentation
         * referenced in the header comment above for more information on IDs and how to
         * assign them to objects.
         *
         * Returns an empty array if no objects are seen.
         */
        rateLimit.reset();
        HuskyLens.Block[] blocks = huskyLens.blocks();
        for(int i = 0; i < blocks.length; i++) {
            telemetry.addData("object:ID1", blocks[i].x);
            if (blocks[i].x < 100) {
                telemetry.addLine("Left");
                husky = "left";
                encoderDrive(DRIVE_SPEED,  -48,  -48, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
                encoderDrive(TURN_SPEED,   12, -12, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout
                encoderDrive(DRIVE_SPEED, -24, -24, 4.0);  // S3: Reverse 24 Inches with 4 Sec timeout
                telemetry.addData("Path", "Complete");
                telemetry.update();
                sleep(1000);  // pause to display final telemetry message.

            } else if (blocks[i].x < 200) {
                telemetry.addLine("Center");
                husky = "center";
                encoderDrive(DRIVE_SPEED,  -10000000,  -10000000, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
                encoderDrive(TURN_SPEED,   200, 200, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout
                encoderDrive(DRIVE_SPEED, -5, -5, 4.0);  // S3: Reverse 24 Inches with 4 Sec timeout
                telemetry.addData("Path", "Complete");
                telemetry.update();
                sleep(1000);  // pause to display final telemetry message.

            } else {
                telemetry.addLine("Right");
                husky = "right";
                encoderDrive(DRIVE_SPEED,  200,  200, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
                encoderDrive(TURN_SPEED,   10, -10, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout
                encoderDrive(DRIVE_SPEED, -200, -200, 4.0);  // S3: Reverse 24 Inches with 4 Sec timeout
                telemetry.addData("Path", "Complete");
                telemetry.update();
                sleep(1000);  // pause to display final telemetry message.
            }
        }

        telemetry.update();

        telemetry.addData("Starting at",  "%7d :%7d",
                frontLeftMotor.getCurrentPosition(),
                frontRightMotor.getCurrentPosition(),
                backRightMotor.getCurrentPosition(),
                backLeftMotor.getCurrentPosition());
        telemetry.update();


        // Wait for the start button to be pressed
        waitForStart();

        encoderDrive(DRIVE_SPEED, 24, 24, 30);
/*
        // Move forward for 3 seconds
        frontLeftMotor.setPower(0.5);  // Set the left motor power to half speed forward
        frontRightMotor.setPower(0.5); // Set the right motor power to half speed forward

        sleep(3000);  // Wait for 3 seconds (adjust the time as needed)

        // Stop the motors
        frontLeftMotor.setPower(0.0);
        frontRightMotor.setPower(0.0);*/
    }
    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;
        telemetry.addData("OpMode1", opModeIsActive());
        telemetry.update();

        // Ensure that the OpMode is still active
        if (opModeIsActive()) {
            telemetry.addData("OpMode2", opModeIsActive());
            telemetry.update();

            // Determine new target position, and pass to motor controller
            newLeftTarget = frontLeftMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = frontRightMotor.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            frontLeftMotor.setTargetPosition(newLeftTarget);
            frontRightMotor.setTargetPosition(newRightTarget);
            //backRightMotor.setTargetPosition(newRightTarget);
            //backLeftMotor.setTargetPosition(newLeftTarget);

            // Turn On RUN_TO_POSITION
            frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            frontLeftMotor.setPower(Math.abs(speed));
            frontRightMotor.setPower(Math.abs(speed));
            //backLeftMotor.setPower(Math.abs(speed));
            //backRightMotor.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (frontLeftMotor.isBusy() || frontRightMotor.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Running to",  " %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Currently at",  " at %7d :%7d",
                        frontLeftMotor.getCurrentPosition(), frontRightMotor.getCurrentPosition(),backRightMotor.getCurrentPosition(), backLeftMotor.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            frontLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            backLeftMotor.setPower(0);
            backRightMotor.setPower(0);

            // Turn off RUN_TO_POSITION
            frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            sleep(250);   // optional pause after each move.
        }
    }
}

