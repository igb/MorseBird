package org.hccp.morsebird.twitter;


import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesSampleEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class HoseReader {

    public static void main(String[] args) throws InterruptedException {

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
                .name("sampleExampleClient")
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
                System.out.println(msg);
            }
        }




    }
}
