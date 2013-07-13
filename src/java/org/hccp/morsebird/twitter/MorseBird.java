package org.hccp.morsebird.twitter;

import org.apache.commons.lang.StringUtils;
import org.hccp.morsebird.morse.Code;
import org.hccp.morsebird.morse.Encoder;
import org.hccp.morsebird.morse.ToneGenerator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.sound.sampled.LineUnavailableException;
import java.text.ParseException;
import java.util.Iterator;
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

    public static void main(String[] args) throws InterruptedException, ParseException, LineUnavailableException {
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            System.out.println("arg = " + arg);
        }
        int unit = 500;

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
                    for (int i = 0; i < encoded.size(); i++) {
                        List<Code> word = encoded.get(i);
                        for (int j = 0; j < word.size(); j++) {
                            Code code = word.get(j);
                            tg.generateToneForCode(code);
                            System.out.print(code.getValue());
                        }

                        if (i < encoded.size()-1) {
                            tg.shortGap();
                            System.out.print(" ");
                        }
                    }

                    tg.mediumGap();
                    tg.mediumGap();
                    System.out.print("\n\n");
                    hoser.advanceToLatest();
                }
            }
        }
    }
}