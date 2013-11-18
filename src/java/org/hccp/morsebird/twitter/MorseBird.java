package org.hccp.morsebird.twitter;

import org.apache.commons.lang.StringUtils;
import org.hccp.morsebird.morse.*;
import org.hccp.morsebird.rpi.BuzzerController;
import org.hccp.morsebird.rpi.LedController;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * ···–·–···–––··· –·––··–··· ··–··–·––––– –····· ·––···––····–····–·–· ····–––·––··–···
 * ···–·–···–––––··–– ··–····–··–··–···· ·––·–·· –·–·––––····–··–·–··· –––––·–·····–····––·–·––––···
 * ·–·–·–·––––····––····–··· –·––··–··· ·––·–·· –·–·––––····–··–·–··· –––– ·–··––····––– ·····––·–··–·–···–·–·–
 */

public class MorseBird {
    public static final String TEXT = "text";
    public static final String LANG = "lang";
    public static final String IN_REPLY_TO_STATUS_ID = "in_reply_to_status_id";
    public static final String EN = "en";

    private void removeUrls(JSONArray array, StringBuffer text) {
        if (array != null && array.size() > 0) {
            Iterator<JSONObject> itr = array.iterator();
            while (itr.hasNext()) {
                JSONObject element = itr.next();
                JSONArray indices = (JSONArray) element.get("indices");
                int start = ((Long)indices.get(0)).intValue();
                int stop = ((Long)indices.get(1)).intValue();
                text.replace(start, stop, StringUtils.repeat(Encoder.SPLIT, stop - start));
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, ParseException, LineUnavailableException, IOException {
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            System.out.println("arg = " + arg);
        }
        int unit = 100; //500;

        String consumerKey = args[0];
        String consumerSecret = args[1];
        String token = args[2];
        String tokenSecret = args[3];
        if (args.length==5) {
            unit = Integer.parseInt(args[4]);
        }

        HoseReader hoser = new HoseReader(consumerKey, consumerSecret, token, tokenSecret);
        Encoder encoder = new Encoder();

        ToneGenerator tg = new ToneGenerator(unit);

        LedController ledController = new LedController();
        ledController.setUnitInMillis(unit);

        BuzzerController buzzerController = new BuzzerController();
        buzzerController.setUnitInMillis(unit);

        List<SignalController> controllers = new LinkedList<SignalController>();
        controllers.add(ledController);
        controllers.add(tg);
        controllers.add(buzzerController);


        MorseBird mb = new MorseBird();
        System.out.println("objects created...");
        while (true) {
            JSONObject msg = hoser.getMessage();

            String text = (String) msg.get(TEXT);
            String lang = (String) msg.get(LANG);
            boolean isReply = msg.get(IN_REPLY_TO_STATUS_ID) != null;

            if (text != null) {
                StringBuffer sb = new StringBuffer(text);
                JSONObject entities = (JSONObject) msg.get("entities");
                JSONArray urls = (JSONArray) entities.get("urls");
                JSONArray media = (JSONArray) entities.get("media");

                if (encoder.isEncodeable(sb.toString()) && lang.equals(EN) && !isReply) {
                    mb.removeUrls(urls, sb);
                    mb.removeUrls(media, sb);

                    List<List<Code>> encoded = encoder.encode(sb.toString());
                    generateSignals(controllers, encoded);
                    hoser.advanceToLatest();
                }
            }
        }
    }

    public static void generateSignals(List<SignalController> controllers, List<List<Code>> encoded) throws InterruptedException {
        for (int i = 0; i < encoded.size(); i++) {
            List<Code> word = encoded.get(i);
            for (int j = 0; j < word.size(); j++) {
                Code code = word.get(j);
                generateSignals(controllers, code);
                System.out.print(code.getValue());
                if (j < word.size() - 1) {
                    generateSignal(controllers, Signal.SHORT_GAP);
                }
            }
            if (i < (encoded.size() - 1) ) {
                System.out.print(" ");
                generateSignal(controllers, Signal.MEDIUM_GAP);

            }
        }

        System.out.print("\n\n");
        generateSignal(controllers, Signal.MEDIUM_GAP);
        generateSignal(controllers, Signal.MEDIUM_GAP);


    }

    private static void generateSignals(List<SignalController> controllers, Code code) throws InterruptedException {

            int[] sequence = code.getSequence();
            for (int j = 0; j < sequence.length; j++) {
                int element = sequence[j];
                if (element == Code.DASH) {
                   generateSignal(controllers, Signal.DASH);
                } else if (element == Code.DOT){
                   generateSignal(controllers, Signal.DOT);
                }

                if (j < sequence.length - 1) {
                    generateSignal(controllers, Signal.INTRA_CHARACTER_GAP);
                }
            }

    }

    private static void generateSignal(List<SignalController> controllers, Signal signal) throws InterruptedException {
        List<Thread> threads = new LinkedList<Thread>();

        for (int i = 0; i < controllers.size(); i++) {
            SignalController signalController = controllers.get(i);
            SignalExecutor executor = new SignalExecutor(signalController, signal);
            threads.add(new Thread(executor));
        }
        for (int i = 0; i < threads.size(); i++) {
            Thread thread = threads.get(i);
            thread.start();
        }
        for (int i = 0; i < threads.size(); i++) {
            Thread thread = threads.get(i);
            thread.join();
        }

    }

}