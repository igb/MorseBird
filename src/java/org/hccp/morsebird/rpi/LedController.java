package org.hccp.morsebird.rpi;

import com.pi4j.io.gpio.*;

/**
 * Created with IntelliJ IDEA.
 * User: ibrown
 * Date: 7/13/13
 * Time: 3:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class LedController  extends AbstractSignalController {

    private GpioController gpio;
    private GpioPinDigitalOutput pin;

    /**
     * Defaults to RaspiPin.GPIO_01
     */
    public LedController() {
        gpio = GpioFactory.getInstance();
        pin = gpio.provisionDigitalOutputPin(Pi4jUtility.getPi4jPin(properties.getProperty("led.pin")), "MyLED", PinState.LOW);
    }

    /**
     * e.g. LedController(RaspiPin.GPIO_01)
     * @param ledPin
     */
    public LedController(Pin ledPin) {
        gpio = GpioFactory.getInstance();
        this.pin = gpio.provisionDigitalOutputPin(ledPin, "MyLED", PinState.LOW);
    }


    protected void on() {
        pin.high();
    }

    protected void off() {
        pin.low();
    }

    public static void main(String[] args) throws InterruptedException {


        System.out.println("<--Pi4J--> GPIO Control Example ... started.");

        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #01 as an output pin and turn on
        final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.HIGH);
        System.out.println("pin.getName() = " + pin.getName());
        System.out.println("--> GPIO state should be: ON");

        Thread.sleep(5000);

        // turn off gpio pin #01
        pin.low();
        System.out.println("--> GPIO state should be: OFF");

        Thread.sleep(5000);

        // toggle the current state of gpio pin #01 (should turn on)
        pin.toggle();
        System.out.println("--> GPIO state should be: ON");

        Thread.sleep(5000);

        // toggle the current state of gpio pin #01  (should turn off)
        pin.toggle();
        System.out.println("--> GPIO state should be: OFF");

        Thread.sleep(5000);

        // turn on gpio pin #01 for 1 second and then off
        System.out.println("--> GPIO state should be: ON for only 1 second");
        pin.pulse(1000, true); // set second argument to 'true' use a blocking call

        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        gpio.shutdown();


    }


}
