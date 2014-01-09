package org.hccp.morsebird.twitter;

import org.apache.commons.lang.StringUtils;
import org.hccp.morsebird.morse.*;
import org.hccp.morsebird.rpi.BuzzerController;
import org.hccp.morsebird.rpi.LedController;
import org.hccp.morsebird.rpi.RateController;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.sound.sampled.LineUnavailableException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

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

        Properties props = new Properties();

        if (args.length==5) {
            String properties = args[4];
            props.load(new FileInputStream(new File(properties)));
        } else {
            props.load(MorseBird.class.getClassLoader().getResourceAsStream("morsebird.properties"));
        }




        int unit = Integer.parseInt(props.getProperty("unit"));
        String consumerKey;
        String consumerSecret;
        String tokenSecret;
        String token;


        consumerKey = args[0];
        consumerSecret = args[1];
        token = args[2];

        tokenSecret = args[3];

        final HoseReader hoser = new HoseReader(consumerKey, consumerSecret, token, tokenSecret);
        final Encoder encoder = new Encoder();


        final List<SignalController> controllers = new LinkedList<SignalController>();

        String[] signalControllerClasses = props.getProperty("signalControllers").split(",");

        for (int i = 0; i < signalControllerClasses.length; i++) {
            String signalControllerClass = signalControllerClasses[i];
            try {
                SignalController controller = (SignalController) Class.forName(signalControllerClass).newInstance();
                controller.setUnitInMillis(unit);
                controllers.add(controller);
            } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (ClassNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }


        final MorseBird mb = new MorseBird();
        //System.out.println("starting rate controller...");

        RateController rateController = new RateController(unit);
        rateController.setProperties(props);
        rateController.setSignalControllers(controllers);
        Thread rateControllerThread = new Thread(rateController);
        rateControllerThread.setName("rate controller");
        rateControllerThread.start();

        //System.out.println("...started rate controller.");
        //System.out.flush();

       Thread t = new Thread() { public void run() {
         while (true) {

             try {
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

             }catch (InterruptedException ie) {
                 ie.printStackTrace();
             }
       }
       }
       };
        t.setName("morsebird driver");
        t.start();
       }

           public static void generateSignals(List<SignalController> controllers, List<List<Code>> encoded) throws InterruptedException {
        for (int i = 0; i < encoded.size(); i++) {
            List<Code> word = encoded.get(i);
            for (int j = 0; j < word.size(); j++) {

                Code code = word.get(j);
                generateSignals(controllers, code);
                Thread.sleep(0);
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