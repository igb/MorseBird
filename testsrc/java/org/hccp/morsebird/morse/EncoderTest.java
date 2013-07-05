package org.hccp.morsebird.morse;

import junit.framework.TestCase;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ibrown
 * Date: 7/4/13
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class EncoderTest extends TestCase {

    public static final String TWEET1 = "RT @Hasan93uc: اذا هذي مب معكم تأكدوا من الجنسيه =)) http://t.co/8TS3FAwA3";
    public static final String TWEET2 = "@The_Bulley_LFC saying what? We're not mean at all!";
    public static final String TWEET3 = "Bb lobet tapi tv blum lobet =D";
    public static final String TWEET4 = "RT @AyyParfavar: Mujeres que van al gimnasio pero van maquilladas. ¡Ay parfavar!";
    public static final String TWEET5 = " @00Josefina Que denso ....";

    public static final String TEST_MSG_001 = "Hi U!";

    public static final String[] GOOD_TWEETS = {TWEET2, TWEET3,TWEET5};
    public static final String[] BAD_TWEETS = {TWEET1, TWEET4};

    public void testGoodTweets() {
        Encoder encoder= new Encoder();

        for (int i = 0; i < GOOD_TWEETS.length; i++) {
            String goodTweet = GOOD_TWEETS[i];
            assertTrue(goodTweet + " is not endcodeable.", encoder.isEncodeable(goodTweet));
        }
    }


    public void testBadTweets() {
        Encoder encoder= new Encoder();

        for (int i = 0; i < BAD_TWEETS.length; i++) {
            String badTweet = BAD_TWEETS[i];
            assertFalse(badTweet + " should not be endcodeable.", encoder.isEncodeable(badTweet));
        }
    }


    public void testEncoding() {
        Encoder encoder= new Encoder();

        List<List<Code>> encoded = encoder.encode(TEST_MSG_001);
        assertEquals(2, encoded.size());
        List<Code> first = encoded.get(0);
        assertEquals(2, first.size());
        List<Code> second = encoded.get(1);
        assertEquals(2, second.size());

        assertEquals("····", first.get(0).toString());
        assertEquals("··", first.get(1).toString());

        assertEquals("··–", second.get(0).toString());
        assertEquals("–·–·––", second.get(1).toString());

    }

}
