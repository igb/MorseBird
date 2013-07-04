package org.hccp.morsebird.morse;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ibrown
 * Date: 7/4/13
 * Time: 9:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class Encoder {
    private Map conversionTable;
    private Set validChars;

    public Encoder() {

        conversionTable = new HashMap();

        conversionTable.put('A', new Code(new int[]{Code.DOT, Code.DASH}));
        conversionTable.put('B', new Code(new int[]{Code.DASH, Code.DOT,Code.DOT,Code.DOT}));
        conversionTable.put('C', new Code(new int[]{Code.DASH, Code.DOT,Code.DASH,Code.DOT}));
        conversionTable.put('D', new Code(new int[]{Code.DASH, Code.DOT,Code.DOT}));
        conversionTable.put('E', new Code(new int[]{Code.DOT}));
        conversionTable.put('F', new Code(new int[]{Code.DOT,Code.DOT,Code.DASH,Code.DOT}));
        conversionTable.put('G', new Code(new int[]{Code.DASH, Code.DASH,Code.DOT}));
        conversionTable.put('H', new Code(new int[]{Code.DOT, Code.DOT,Code.DOT,Code.DOT}));
        conversionTable.put('I', new Code(new int[]{Code.DOT,Code.DOT}));
        conversionTable.put('J', new Code(new int[]{Code.DOT,Code.DASH,Code.DASH,Code.DASH}));
        conversionTable.put('K', new Code(new int[]{Code.DASH, Code.DOT,Code.DASH}));
        conversionTable.put('L', new Code(new int[]{Code.DOT, Code.DASH,Code.DOT,Code.DOT}));
        conversionTable.put('M', new Code(new int[]{Code.DASH, Code.DASH}));
        conversionTable.put('N', new Code(new int[]{Code.DASH, Code.DOT}));
        conversionTable.put('O', new Code(new int[]{Code.DASH, Code.DASH,Code.DASH}));
        conversionTable.put('P', new Code(new int[]{Code.DOT, Code.DASH,Code.DASH,Code.DOT}));
        conversionTable.put('Q', new Code(new int[]{Code.DASH, Code.DASH,Code.DOT,Code.DASH}));
        conversionTable.put('R', new Code(new int[]{Code.DOT, Code.DASH,Code.DOT}));
        conversionTable.put('S', new Code(new int[]{Code.DOT, Code.DOT,Code.DOT}));
        conversionTable.put('T', new Code(new int[]{Code.DASH}));
        conversionTable.put('U', new Code(new int[]{Code.DOT, Code.DOT,Code.DASH}));
        conversionTable.put('V', new Code(new int[]{Code.DOT, Code.DOT,Code.DOT,Code.DASH}));
        conversionTable.put('W', new Code(new int[]{Code.DOT, Code.DASH,Code.DASH}));
        conversionTable.put('X', new Code(new int[]{Code.DASH, Code.DOT,Code.DOT,Code.DASH}));
        conversionTable.put('Y', new Code(new int[]{Code.DASH, Code.DOT,Code.DASH,Code.DASH}));
        conversionTable.put('Z', new Code(new int[]{Code.DASH, Code.DASH,Code.DOT,Code.DOT}));


        conversionTable.put('0', new Code(new int[]{Code.DASH, Code.DASH,Code.DASH,Code.DASH,Code.DASH}));
        conversionTable.put('1', new Code(new int[]{Code.DOT, Code.DASH,Code.DASH,Code.DASH,Code.DASH}));
        conversionTable.put('2', new Code(new int[]{Code.DOT, Code.DOT,Code.DASH,Code.DASH,Code.DASH}));
        conversionTable.put('3', new Code(new int[]{Code.DOT, Code.DOT,Code.DOT,Code.DASH,Code.DASH}));
        conversionTable.put('4', new Code(new int[]{Code.DOT, Code.DOT,Code.DOT,Code.DOT,Code.DASH}));
        conversionTable.put('5', new Code(new int[]{Code.DOT, Code.DOT,Code.DOT,Code.DOT,Code.DOT}));
        conversionTable.put('6', new Code(new int[]{Code.DASH, Code.DOT,Code.DOT,Code.DOT,Code.DOT}));
        conversionTable.put('7', new Code(new int[]{Code.DASH, Code.DASH,Code.DOT,Code.DOT,Code.DOT}));
        conversionTable.put('8', new Code(new int[]{Code.DASH, Code.DASH,Code.DASH,Code.DOT,Code.DOT}));
        conversionTable.put('9', new Code(new int[]{Code.DASH, Code.DASH,Code.DASH,Code.DASH,Code.DOT}));

        conversionTable.put('.', new Code(new int[]{Code.DOT, Code.DASH,Code.DOT,Code.DASH,Code.DOT, Code.DASH}));
        conversionTable.put(',', new Code(new int[]{Code.DASH, Code.DASH,Code.DOT,Code.DOT,Code.DASH, Code.DASH}));
        conversionTable.put('?', new Code(new int[]{Code.DOT, Code.DOT,Code.DASH,Code.DASH,Code.DOT, Code.DOT}));
        conversionTable.put('\'', new Code(new int[]{Code.DOT, Code.DASH,Code.DASH,Code.DASH,Code.DASH, Code.DOT}));
        conversionTable.put('!', new Code(new int[]{Code.DASH, Code.DOT,Code.DASH,Code.DOT,Code.DASH, Code.DASH}));
        conversionTable.put('/', new Code(new int[]{Code.DASH, Code.DOT,Code.DOT,Code.DASH,Code.DOT}));
        conversionTable.put('(', new Code(new int[]{Code.DASH, Code.DOT,Code.DASH,Code.DASH,Code.DOT}));
        conversionTable.put(')', new Code(new int[]{Code.DASH, Code.DOT,Code.DASH,Code.DASH,Code.DOT, Code.DASH}));
        conversionTable.put('&', new Code(new int[]{Code.DOT, Code.DASH,Code.DOT,Code.DOT,Code.DOT}));
        conversionTable.put(':', new Code(new int[]{Code.DASH, Code.DASH,Code.DASH,Code.DOT,Code.DOT, Code.DOT}));
        conversionTable.put(';', new Code(new int[]{Code.DASH, Code.DOT,Code.DASH,Code.DOT,Code.DASH, Code.DOT}));
        conversionTable.put('=', new Code(new int[]{Code.DASH, Code.DOT,Code.DOT,Code.DOT,Code.DASH}));
        conversionTable.put('+', new Code(new int[]{Code.DOT, Code.DASH,Code.DOT,Code.DASH,Code.DOT}));
        conversionTable.put('-', new Code(new int[]{Code.DASH, Code.DOT,Code.DOT,Code.DOT,Code.DOT, Code.DASH}));
        conversionTable.put('_', new Code(new int[]{Code.DOT, Code.DOT,Code.DASH,Code.DASH,Code.DOT, Code.DASH}));
        conversionTable.put('"', new Code(new int[]{Code.DOT, Code.DASH,Code.DOT,Code.DOT,Code.DASH, Code.DOT}));
        conversionTable.put('$', new Code(new int[]{Code.DOT, Code.DOT,Code.DOT,Code.DASH,Code.DOT, Code.DOT, Code.DASH}));
        conversionTable.put('@', new Code(new int[]{Code.DOT, Code.DASH,Code.DASH,Code.DOT,Code.DASH, Code.DOT}));

        validChars = conversionTable.keySet();


    }

    public Code encode(char character) {
        return (Code)conversionTable.get(character);
    }

    public boolean isEncodeable(char character) {
        return validChars.contains(character);
    }

    public boolean isEncodeable(String message) {
        char[] chars = message.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];
            if (!isEncodeable(aChar)) {
                return false;
            }

        }
        return true;

    }
}
