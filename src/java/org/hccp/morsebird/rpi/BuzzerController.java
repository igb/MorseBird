package org.hccp.morsebird.rpi;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: ibrown
 * Date: 11/17/13
 * Time: 8:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuzzerController  extends AbstractSignalController {
    private Runtime rt;


    public BuzzerController() throws IOException, InterruptedException {
        rt = Runtime.getRuntime();
        Process setModeProc = rt.exec("gpio mode 1 pwm");
        setModeProc.waitFor();
        Process pwmBal = rt.exec("gpio pwm-bal");
        pwmBal.waitFor();
    }

    protected void on() throws InterruptedException, IOException {
        Process on = rt.exec("gpio pwm 1 500");
        on.waitFor();
    }

    protected void off() throws InterruptedException, IOException {
        Process off = rt.exec("gpio pwm 1 0");
        off.waitFor();
    }


}
