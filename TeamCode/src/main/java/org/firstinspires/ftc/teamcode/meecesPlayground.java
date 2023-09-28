package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


/*
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When a selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

@TeleOp(name="meecesPlayground", group="Linear OpMode")
//@Disabled
public class meecesPlayground extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontLeftMotor = null;
    private DcMotor frontRightMotor = null;
    private DcMotor backRightMotor = null;
    private DcMotor backLeftMotor = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRightMotor  = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");


        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        //left.setDirection(DcMotor.Direction.FORWARD);
        //right.setDirection(DcMotor.Direction.FORWARD);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            telemetry.addData("Status", "Running");

            // Setup a variable for each drive wheel to save power level for telemetry
            double vertical;
            double horizontal;
            double pivot;
            double frontLeftMotorTel;
            double frontRightMotorTel;
            double backLeftMotorTel;
            double backRightMotorTel;
            double negHorizontal;
            double posHorizontal;

            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            double leftStickY = -gamepad1.left_stick_y;
            double leftStickX = gamepad1.left_stick_x;
            double rightStickX = gamepad1.right_stick_x;
            vertical  = Range.clip(leftStickY, -1.0, 1.0) ;
            horizontal = Range.clip(leftStickX, -1.0, 1.0) ;
            pivot = Range.clip(rightStickX, -1.0, 1.0) ;

            // Tank Mode uses one stick to control each wheel.
            // - This requires no math, but it is hard to drive forward slowly and keep straight.
            // leftPower  = -gamepad1.left_stick_y ;
            // rightPower = -gamepad1.right_stick_y ;

            // Send calculated power to wheels

            negHorizontal = pivot + (-vertical - horizontal);
            posHorizontal = pivot + (-vertical + horizontal);

            if (gamepad1.left_bumper){
                negHorizontal = negHorizontal/2;
                posHorizontal = posHorizontal/2;
            }


            frontLeftMotor.setPower(negHorizontal);
            frontRightMotor.setPower(posHorizontal);
            backLeftMotor.setPower(posHorizontal);
            backRightMotor.setPower(negHorizontal);

            frontLeftMotorTel = negHorizontal;
            frontRightMotorTel = posHorizontal;
            backLeftMotorTel = posHorizontal;
            backRightMotorTel = negHorizontal;



            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "frontLeftMotor (%.2f), frontRightMotor (%.2f), backLeftMotor (%.2f), backRightMotor (%.2f)", frontLeftMotorTel, frontRightMotorTel, backLeftMotorTel, backRightMotorTel);
            telemetry.addData("Meece", "Playground lol Blue Smurf Cat go BRRRRRRRR©™ WE LIVE WE LAUGH WE LIE YEAH AUTONOMICE WOOOOOOOOO");
            telemetry.update();
        }
    }
}