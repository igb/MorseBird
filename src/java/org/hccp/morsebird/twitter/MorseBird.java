package org.hccp.morsebird.twitter;

import org.hccp.morsebird.morse.Code;
import org.hccp.morsebird.morse.Encoder;
import org.hccp.morsebird.morse.ToneGenerator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.sound.sampled.LineUnavailableException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Streams tweets from the public Sample Stream, filters and converts Morse-code encodeable tweets and converts to audio signal.
 */

public class MorseBird {

    public static void main(String[] args) throws InterruptedException, ParseException, LineUnavailableException {

        String consumerKey = args[0];
        String consumerSecret = args[1];
        String token = args[2];
        String tokenSecret = args[3];

        HoseReader hoser = new HoseReader(consumerKey, consumerSecret, token, tokenSecret);
        Encoder encoder = new Encoder();
        ToneGenerator tg = new ToneGenerator();

        while (true) {
             JSONObject msg = hoser.getMessage();

            String text = (String) msg.get("text");
            String lang = (String) msg.get("lang");
            String createdAt = (String) msg.get("created_at");
            if (createdAt != null) {
                System.out.println("createdAt = " + createdAt);

                DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
                Date result = df.parse(createdAt);
            }

            boolean isReply = msg.get("in_reply_to_status_id") != null;


            if (text != null) {

                JSONObject entities = (JSONObject) msg.get("entities");
                JSONArray urls = (JSONArray) entities.get("urls");

                if (urls.size() == 0 && encoder.isEncodeable(text) && lang.equals("en") && !isReply) {
                    System.out.println("createdAt = " + createdAt);
                    List<List<Code>> encoded = encoder.encode(text);
                    for (int i = 0; i < encoded.size(); i++) {
                        List<Code> word = encoded.get(i);
                        for (int j = 0; j < word.size(); j++) {
                            Code code = word.get(j);
                            System.out.print(code.getValue());
                            tg.generateToneForCode(code);

                        }
                        tg.shortGap();
                        System.out.print(" ");
                    }

                    tg.mediumGap();
                    tg.mediumGap();
                    System.out.println();
                    System.out.println();
                    hoser.advanceToLatest();

                }
            }






        }

    }

}
