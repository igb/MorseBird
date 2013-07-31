package org.hccp.morsebird.twitter.util;

import org.hccp.morsebird.morse.Code;
import org.hccp.morsebird.morse.Encoder;
import org.hccp.morsebird.morse.SignalController;
import org.hccp.morsebird.morse.ToneGenerator;
import org.hccp.morsebird.rpi.LedController;
import org.hccp.morsebird.twitter.MorseBird;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

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

        if (args.length > 0) {
            unit = Integer.parseInt(args[0]);
        }

        ToneGenerator tg = new ToneGenerator(unit);
        LedController ledController = new LedController();
        ledController.setUnitInMillis(unit);

        List<SignalController> controllers = new LinkedList<SignalController>();
        controllers.add(ledController);
        controllers.add(tg);



        Encoder encoder = new Encoder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String input = reader.readLine();
            List<List<Code>> encoded = encoder.encode(input);

            MorseBird.generateSignals(controllers, encoded);
        }

    }
}
