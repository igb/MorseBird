package org.hccp.morsebird.twitter.util;

import org.hccp.morsebird.morse.Code;
import org.hccp.morsebird.morse.Encoder;
import org.hccp.morsebird.morse.SignalController;
import org.hccp.morsebird.morse.ToneGenerator;
import org.hccp.morsebird.rpi.LedController;
import org.hccp.morsebird.rpi.RateController;
import org.hccp.morsebird.twitter.MorseBird;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: ibrown
 * Date: 7/30/13
 * Time: 3:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestUtility {

    public static void main(String[] args) throws InterruptedException, IOException {
        int unit=1000;

        Properties props = new Properties();


            props.load(new FileInputStream(new File("/Users/ibrown/Documents/morsebird/props/test.morsebird.properties")));


        RateController rateController = new RateController(unit);
        rateController.setProperties(props);

        Thread t = new Thread(rateController);
        t.start();

    }
}
