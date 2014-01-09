package org.hccp.morsebird.twitter;


import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesSampleEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class HoseReader {

    public static final String MORSE_BIRD_V_1_0 = "MorseBird_v1_0";
    private BasicClient client;
    private BlockingQueue<String> tweetQueue;
    private int timeout = 60;

    public HoseReader(String consumerKey, String consumerSecret, String token, String tokenSecret) {
        tweetQueue = new LinkedBlockingQueue<String>(10000);

        StatusesSampleEndpoint endpoint = new StatusesSampleEndpoint();
        endpoint.stallWarnings(false);
        endpoint.filterLevel(Constants.FilterLevel.Medium);


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
        }

        String msg = tweetQueue.poll(timeout, TimeUnit.SECONDS);

        if (msg == null) {
            System.out.println("Did not receive a message in " + timeout + " seconds");
            return null;
        } else {
            return (JSONObject) JSONValue.parse(msg);
        }
    }

    public void advanceToLatest() {
        tweetQueue.clear();
    }


}
