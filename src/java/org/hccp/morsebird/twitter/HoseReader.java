package org.hccp.morsebird.twitter;


import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesSampleEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.hccp.morsebird.morse.Code;
import org.hccp.morsebird.morse.Encoder;
import org.hccp.morsebird.morse.ToneGenerator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.sound.sampled.LineUnavailableException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class HoseReader {

    public static final String MORSE_BIRD_V_1_0 = "MorseBird_v1_0";
    private final BasicClient client;
    BlockingQueue<String> tweetQueue;

    public HoseReader(String consumerKey, String consumerSecret, String token, String tokenSecret) {
        tweetQueue = new LinkedBlockingQueue<String>(10000);

        StatusesSampleEndpoint endpoint = new StatusesSampleEndpoint();
        endpoint.stallWarnings(false);


        Authentication auth = new OAuth1(consumerKey, consumerSecret, token, tokenSecret);
        client = new ClientBuilder().name(MORSE_BIRD_V_1_0)
                .hosts(Constants.STREAM_HOST)
                .endpoint(endpoint)
                .authentication(auth)
                .processor(new StringDelimitedProcessor(tweetQueue))
                .build();

        client.connect();

    }


    public JSONObject getMessage() throws InterruptedException {
        if (client.isDone()) {
            System.out.println("Client connection closed unexpectedly: " + client.getExitEvent().getMessage());
            //break;
        }

        String msg = tweetQueue.poll(5, TimeUnit.SECONDS);

        if (msg == null) {
            System.out.println("Did not receive a message in 5 seconds");
            return null;
        } else {
            return (JSONObject) JSONValue.parse(msg);
        }
    }

    public void advanceToLatest() {
        tweetQueue.clear();
    }

    public static void main(String[] args) throws InterruptedException, LineUnavailableException, ParseException {

        Encoder encoder = new Encoder();
        ToneGenerator tg = new ToneGenerator();

        String consumerKey = args[0];
        String consumerSecret = args[1];
        String token = args[2];
        String tokenSecret = args[3];


        // Create an appropriately sized blocking queue
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);

        // Define our endpoint: By default, delimited=length is set (we need this for our processor)
        // and stall warnings are on.
        StatusesSampleEndpoint endpoint = new StatusesSampleEndpoint();
        endpoint.stallWarnings(false);


        Authentication auth = new OAuth1(consumerKey, consumerSecret, token, tokenSecret);
        //Authentication auth = new com.twitter.hbc.httpclient.auth.BasicAuth(username, password);

        // Create a new BasicClient. By default gzip is enabled.
        BasicClient client = new ClientBuilder()
                .name(MORSE_BIRD_V_1_0)
                .hosts(Constants.STREAM_HOST)
                .endpoint(endpoint)
                .authentication(auth)
                .processor(new StringDelimitedProcessor(queue))
                .build();

        // Establish a connection
        client.connect();

        // Do whatever needs to be done with messages
        for (int msgRead = 0; msgRead < 1000; msgRead++) {
            if (client.isDone()) {
                System.out.println("Client connection closed unexpectedly: " + client.getExitEvent().getMessage());
                break;
            }

            String msg = queue.poll(5, TimeUnit.SECONDS);

            if (msg == null) {
                System.out.println("Did not receive a message in 5 seconds");
            } else {

                org.json.simple.JSONObject parsedMessage = (JSONObject) JSONValue.parse(msg);
                String text = (String) parsedMessage.get("text");
                String lang = (String) parsedMessage.get("lang");
                String createdAt = (String) parsedMessage.get("created_at");
                if (createdAt != null) {
                    System.out.println("createdAt = " + createdAt);

                    DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
                    Date result = df.parse(createdAt);
                }

                boolean isReply = parsedMessage.get("in_reply_to_status_id") != null;


                if (text != null) {

                    JSONObject entities = (JSONObject) parsedMessage.get("entities");
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
                        queue.clear();

                    }
                }
            }
        }


    }


}
