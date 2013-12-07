package org.hccp.morsebird.rpi;

import com.pi4j.io.gpio.*;
import org.hccp.morsebird.morse.SignalController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ibrown
 * Date: 11/30/13
 * Time: 4:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class RateController extends PropertyBasedController {


    private GpioController gpio;
    private GpioPinDigitalOutput pinA;
    private GpioPinDigitalOutput pinB;
    private int unitInMilliseconds;

    private List<SignalController> signalControllers;


    public RateController(int unitInMilliseconds) {
        this.unitInMilliseconds = unitInMilliseconds;

        gpio = GpioFactory.getInstance();
        pinA = gpio.provisionDigitalOutputPin(Pi4jUtility.getPi4jPin(properties.getProperty("encoder.a.pin")), "A", PinState.LOW);
        pinB = gpio.provisionDigitalOutputPin(Pi4jUtility.getPi4jPin(properties.getProperty("encoder.b.pin")), "B", PinState.LOW);

    }


    public void updateSignalControllers() {
        for (int i = 0; i < signalControllers.size(); i++) {
            SignalController signalController = signalControllers.get(i);
            signalController.setUnitInMillis(unitInMilliseconds);
        }
    }
}
