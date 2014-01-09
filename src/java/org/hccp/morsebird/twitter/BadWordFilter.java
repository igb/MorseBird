package org.hccp.morsebird.twitter;


import org.hccp.morsebird.morse.Encoder;

import java.util.HashSet;
import java.util.Set;

public class BadWordFilter {

    public static Set<String> badwords = new HashSet<String>();




    public static boolean hasBadWords(String message) {
        String[] words = message.toUpperCase().split(Encoder.SPLIT_REGEX);
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (badwords.contains(word)) {
                return true;
            }
        }
        return false;
    }


}
