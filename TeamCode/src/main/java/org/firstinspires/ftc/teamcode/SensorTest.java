package org.firstinspires.ftc.teamcode;//tells java which folder the code belongs in

import com.qualcomm.robotcore.eventloop.opmode.OpMode;//to bring in opmode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;//to use @teleop
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareDevice;

        @TeleOp//tells it that this is a teleop program and shows up on the driverhub
        public class SensorTest extends OpMode { // a public class named yellow sensor using opmde. opmode is the overall stucture and @teleop is one of the values inside
            private ColorSensor colorSensor;//telling that i have a colorsensor named colorsensor

            @Override //
            public void init() { //this runs whn init is pressed on the driver station, thats where sensor is set up
                colorSensor = hardwareMap.get(ColorSensor.class, "yellowsensor");//connects the color sensor to the name i gave it,and the name of the hardware
            }

            @Override
            public void loop() {

                if (colorSensor.red() > 100 && colorSensor.green() > 100 && colorSensor.blue() < 100) { //the value for yellow
                    telemetry.addData("Color", "Yellow");//basicly print that
                } else {
                    telemetry.addData("unrecognized", "Color");//or print this
                }
            telemetry.update(); //sends it to the driver hub so we can see the color

            }
        }



