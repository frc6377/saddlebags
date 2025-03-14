package utilities;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import java.util.function.Supplier;

public class OI_Utils {
  // Operator Interface (OI) class containing all control information
  public static final int driverJoystickPort = 0;
  public static final int operatorJoystickPort = 1;

  public static Supplier<Double> getAxisSupplier(Control axis) {
    if (axis.getType() != Control.ControlType.AXIS) {
      DriverStation.reportError(axis.getAction() + " is not an axis", true);
      return () -> 0d;
    }
    return () -> axis.getCurve().calculate(axis.getController().getRawAxis(axis.getId()));
  }

  public static Trigger getButton(Control button) {
    switch (button.getType()) {
      case BUTTON:
        return new JoystickButton(button.getController(), button.getId());
      case POVBUTTON:
        return new POVButton(button.getController(), button.getId());
      case TRIGGER:
        return new Trigger(
            () -> button.getController().getRawAxis(button.getId()) > button.getThreshold());
      default:
        throw new RuntimeException(button.getAction() + " is not a valid input");
    }
  }

  public static class Control {
    public enum ControlType {
      AXIS,
      BUTTON,
      POVBUTTON,
      TRIGGER
    }

    private int id;
    private String action;
    private XboxController controller;
    private ControlCurve curve;
    private double threshold; // Percentage where axis is triggered as a button
    private ControlType type;

    public Control(
        int id, String action, String name, XboxController controller, ControlType type) {
      this.id = id;
      this.action = action;
      this.controller = controller;
      this.type = type;
    }

    public Control(
        XboxController.Axis axis, String action, XboxController controller, ControlCurve curve) {
      this(axis.value, action, axis.name(), controller, ControlType.AXIS);
      this.curve = curve;
    }

    public Control(XboxController.Axis axis, String action, XboxController controller) {
      this(axis, action, controller, new ControlCurve(1, 0, 0, 0));
    }

    public Control(XboxController.Button button, String action, XboxController controller) {
      this(button.value, action, button.name(), controller, ControlType.BUTTON);
    }

    public Control(double povAngle, String action, XboxController controller) {
      this((int) povAngle, action, "POV " + povAngle, controller, ControlType.POVBUTTON);
    }

    public Control(
        XboxController.Axis axis, String action, XboxController controller, double threshold) {
      this(axis.value, action, axis.name(), controller, ControlType.TRIGGER);
      this.threshold = threshold;
    }

    private int getId() {
      return id;
    }

    private String getAction() {
      return action;
    }

    private XboxController getController() {
      return controller;
    }

    private ControlCurve getCurve() {
      return curve;
    }

    private double getThreshold() {
      return threshold;
    }

    private ControlType getType() {
      return type;
    }
  }

  public static class ControlCurve {
    private final double ySaturation; // Maximum output, in percentage of possible output
    private final double yIntercept; // Minimum output, in percentage of saturation
    private final double curvature; // Curvature shift between linear and cubic
    private final double deadzone; // Range of input that will always return zero output
    private final boolean inverted;

    public ControlCurve(
        double ySaturation,
        double yIntercept,
        double curvature,
        double deadzone,
        boolean inverted) {
      this.ySaturation = ySaturation;
      this.yIntercept = yIntercept;
      this.curvature = curvature;
      this.deadzone = deadzone;
      this.inverted = inverted;
    }

    public ControlCurve(double ySaturation, double yIntercept, double curvature, double deadzone) {
      this(ySaturation, yIntercept, curvature, deadzone, false);
    }

    public double calculate(double input) {
      /* https://www.desmos.com/calculator/w6ovblmmqj
      First is the deadzone
      y = 0 {|x| < d}
      The second is the curve
      y = a(sign(x) * b + (1 - b) * (c * x^3 + (1 - c) * x)) {|x| >= d}
      Where
      x = input
      y = output
      a = ySaturation
      b = yIntercept
      c = curvature
      d = deadzone
      and 0 <= a,b,c,d < 1
      */
      if (Math.abs(input) < deadzone) {
        return 0;
      }
      return (inverted ? -1 : 1)
          * ySaturation
          * (Math.signum(input) * yIntercept
              + (1 - yIntercept) * (curvature * Math.pow(input, 3) + (1 - curvature) * input));
    }
  }
}
