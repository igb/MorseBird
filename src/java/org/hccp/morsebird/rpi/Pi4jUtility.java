package org.hccp.morsebird.rpi;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

import java.util.HashMap;
import java.util.Map;

/**
 * http://pi4j.com/usage.html
 * */
public class Pi4jUtility {

    static Map lookupTable = new HashMap();
    static {
        lookupTable.put(3, RaspiPin.GPIO_08);
        lookupTable.put(4, RaspiPin.GPIO_09);
        lookupTable.put(7, RaspiPin.GPIO_07);
        lookupTable.put(8, RaspiPin.GPIO_15);
        lookupTable.put(10, RaspiPin.GPIO_16);
        lookupTable.put(11, RaspiPin.GPIO_00);
        lookupTable.put(12, RaspiPin.GPIO_01);
        lookupTable.put(13, RaspiPin.GPIO_02);
        lookupTable.put(15, RaspiPin.GPIO_03);
        lookupTable.put(16, RaspiPin.GPIO_04);
        lookupTable.put(18, RaspiPin.GPIO_05);
        lookupTable.put(19, RaspiPin.GPIO_12);
        lookupTable.put(21, RaspiPin.GPIO_13);
        lookupTable.put(22, RaspiPin.GPIO_06);
        lookupTable.put(23, RaspiPin.GPIO_14);
        lookupTable.put(24, RaspiPin.GPIO_10);
        lookupTable.put(26, RaspiPin.GPIO_11);




    }

    /**
     * Returns Pi4J Pin object based on RPI address.
     *
     * @param pin
     * @return
     */
    public static Pin getPi4jPin(int pin) {
        return (Pin) lookupTable.get(pin);
    }

    /**
     * Returns Pi4J Pin object based on RPI address.
     * @param pin
     * @return
     */
    public static Pin getPi4jPin(String pin) {
        return getPi4jPin(Integer.parseInt(pin));
    }

}
