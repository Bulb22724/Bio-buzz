package org.firstinspires.ftc.teamcode.core.units;

import androidx.annotation.NonNull;

/**
 * Container for the project's unit and state types.
 *
 * Types are nested so they can share one source file while remaining public:
 * for example, use {@code Units.Length} or {@code Units.Angle}.
 */
public final class Units {
    private Units() {
    }

    public static class Acceleration {
        double metersPerSecondSquared;

        private Acceleration(double metersPerSecondSquared) {
            this.metersPerSecondSquared = metersPerSecondSquared;
        }

        public static Acceleration ms2(double metersPerSecondSquared) {
            return new Acceleration(metersPerSecondSquared);
        }

        public static Acceleration cms2(double centimetersPerSecondSquared) {
            return new Acceleration(centimetersPerSecondSquared / 100.0);
        }

        public static Acceleration mms2(double millimetersPerSecondSquared) {
            return new Acceleration(millimetersPerSecondSquared / 1000.0);
        }

        public double ms2() {
            return metersPerSecondSquared;
        }

        public double cms2() {
            return metersPerSecondSquared * 100.0;
        }

        public double mms2() {
            return metersPerSecondSquared * 1000.0;
        }

        @NonNull
        @Override
        public String toString() {
            return ms2() + "m/s^2";
        }
    }

    public enum Alliance {
        RED,
        BLUE,
        DEFAULT
    }

    public static class Angle {
        double radians;

        private Angle(double radians) {
            this.radians = radians;
        }

        public static Angle deg(double degrees) {
            return new Angle(Math.toRadians(degrees));
        }

        public static Angle rad(double radians) {
            return new Angle(radians);
        }

        public double deg() {
            return Math.toDegrees(radians);
        }

        public double rad() {
            return radians;
        }

        public Angle normalize() {
            radians %= (2.0 * Math.PI);
            if (radians > Math.PI) radians -= 2.0 * Math.PI;
            if (radians < -Math.PI) radians += 2.0 * Math.PI;
            return this;
        }

        public Angle clampDeg(double min, double max) {
            double d = Math.min(Math.max(rad(radians).deg(), min), max);
            radians = deg(d).rad();
            return this;
        }

        @NonNull
        @Override
        public String toString() {
            return deg() + "°";
        }
    }

    public static class AngularVelocity {
        double radiansPerSecond;

        private AngularVelocity(double radiansPerSecond) {
            this.radiansPerSecond = radiansPerSecond;
        }

        public static AngularVelocity rads(double radiansPerSecond) {
            return new AngularVelocity(radiansPerSecond);
        }

        public static AngularVelocity degs(double degreesPerSecond) {
            return new AngularVelocity(Math.toRadians(degreesPerSecond));
        }

        public static AngularVelocity rpm(double revolutionsPerMinute) {
            return new AngularVelocity(revolutionsPerMinute * 2.0 * Math.PI / 60.0);
        }

        public double rads() {
            return radiansPerSecond;
        }

        public double degs() {
            return Math.toDegrees(radiansPerSecond);
        }

        public double rpm() {
            return radiansPerSecond * 60.0 / (2.0 * Math.PI);
        }

        @NonNull
        @Override
        public String toString() {
            return rpm() + "RPM";
        }
    }

    public enum APInterpolation {
        LINEAR,
        FACING_POINT
    }

    public static class Current {
        double amperes;

        private Current(double amperes) {
            this.amperes = amperes;
        }

        public static Current A(double a) {
            return new Current(a);
        }

        public static Current mA(double milliAmp) {
            return new Current(milliAmp / 1000.0);
        }

        public static Current uA(double microAmp) {
            return new Current(microAmp / 1_000_000.0);
        }

        public static Current kA(double kiloAmp) {
            return new Current(kiloAmp * 1000.0);
        }

        public double A() {
            return amperes;
        }

        public double mA() {
            return amperes * 1000.0;
        }

        public double uA() {
            return amperes * 1_000_000.0;
        }

        public double kA() {
            return amperes / 1000.0;
        }

        @NonNull
        @Override
        public String toString() {
            return A() + "A";
        }
    }

    public static class Force {
        double newtons;

        private Force(double newtons) {
            this.newtons = newtons;
        }

        public static Force N(double newtons) {
            return new Force(newtons);
        }

        public static Force kN(double kilonewtons) {
            return new Force(kilonewtons * 1000.0);
        }

        public double N() {
            return newtons;
        }

        public double kN() {
            return newtons / 1000.0;
        }

        @NonNull
        @Override
        public String toString() {
            return N() + "N";
        }
    }

    public static class Length {
        double meters;

        private Length(double meters) {
            this.meters = meters;
        }

        public static Length mm(double mm) {
            return new Length(mm / 1000.0);
        }

        public static Length cm(double cm) {
            return new Length(cm / 100.0);
        }

        public static Length m(double m) {
            return new Length(m);
        }

        public static Length inch(double inch) {
            return new Length(inch * 0.0254);
        }

        public double mm() {
            return meters * 1000.0;
        }

        public double cm() {
            return meters * 100.0;
        }

        public double m() {
            return meters;
        }

        public double inch() {
            return meters / 0.0254;
        }

        @NonNull
        @Override
        public String toString() {
            return mm() + "mm";
        }
    }

    public enum ObeliskRead {
        PPG,
        PGP,
        GPP,
        FAILED
    }

    public static class Position {
        public Length x;
        public Length y;
        public Length z;

        public Position(Length x, Length y, Length z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @NonNull
        @Override
        public String toString() {
            return x + " | " + y + " | " + z;
        }
    }

    public enum RobotState {
        INIT,
        TELEOP,
        AUTO,
        DEFAULT
    }

    public static class Time {
        double seconds;

        private Time(double seconds) {
            this.seconds = seconds;
        }

        public static Time ms(double milliseconds) {
            return new Time(milliseconds / 1000.0);
        }

        public static Time s(double seconds) {
            return new Time(seconds);
        }

        public static Time min(double minutes) {
            return new Time(minutes * 60.0);
        }

        public double ms() {
            return seconds * 1000.0;
        }

        public double s() {
            return seconds;
        }

        public double min() {
            return seconds / 60.0;
        }

        @NonNull
        @Override
        public String toString() {
            return s() + "s";
        }
    }

    public static class Torque {
        double newtonMeters;

        private Torque(double newtonMeters) {
            this.newtonMeters = newtonMeters;
        }

        public static Torque Nm(double newtonMeters) {
            return new Torque(newtonMeters);
        }

        public static Torque Ncm(double newtonCentimeters) {
            return new Torque(newtonCentimeters / 100.0);
        }

        public double Nm() {
            return newtonMeters;
        }

        public double Ncm() {
            return newtonMeters * 100.0;
        }

        @NonNull
        @Override
        public String toString() {
            return Nm() + "Nm";
        }
    }

    public static class Velocity {
        double metersPerSecond;

        private Velocity(double metersPerSecond) {
            this.metersPerSecond = metersPerSecond;
        }

        public static Velocity ms(double metersPerSecond) {
            return new Velocity(metersPerSecond);
        }

        public static Velocity cms(double centimetersPerSecond) {
            return new Velocity(centimetersPerSecond / 100.0);
        }

        public static Velocity mms(double millimetersPerSecond) {
            return new Velocity(millimetersPerSecond / 1000.0);
        }

        public double ms() {
            return metersPerSecond;
        }

        public double cms() {
            return metersPerSecond * 100.0;
        }

        public double mms() {
            return metersPerSecond * 1000.0;
        }

        public double inchs() {
            return metersPerSecond / 0.0254;
        }

        @NonNull
        @Override
        public String toString() {
            return ms() + "ms";
        }
    }

    public static class Weight {
        double kilograms;

        private Weight(double kilograms) {
            this.kilograms = kilograms;
        }

        public static Weight g(double grams) {
            return new Weight(grams / 1000.0);
        }

        public static Weight kg(double kilograms) {
            return new Weight(kilograms);
        }

        public static Weight lb(double pounds) {
            return new Weight(pounds * 0.45359237);
        }

        public double g() {
            return kilograms * 1000.0;
        }

        public double kg() {
            return kilograms;
        }

        public double lb() {
            return kilograms / 0.45359237;
        }

        @NonNull
        @Override
        public String toString() {
            return kg() + "kg";
        }
    }
}
