package org.hccp.morsebird.morse;

import java.util.*;

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

        conversionTable.put('A', new Code(new int[]{Code.DOT, Code.DASH}, 'A'));
        conversionTable.put('B', new Code(new int[]{Code.DASH, Code.DOT,Code.DOT,Code.DOT}, 'B'));
        conversionTable.put('C', new Code(new int[]{Code.DASH, Code.DOT,Code.DASH,Code.DOT}, 'C'));
        conversionTable.put('D', new Code(new int[]{Code.DASH, Code.DOT,Code.DOT}, 'D'));
        conversionTable.put('E', new Code(new int[]{Code.DOT}, 'E'));
        conversionTable.put('F', new Code(new int[]{Code.DOT,Code.DOT,Code.DASH,Code.DOT}, 'F'));
        conversionTable.put('G', new Code(new int[]{Code.DASH, Code.DASH,Code.DOT}, 'G'));
        conversionTable.put('H', new Code(new int[]{Code.DOT, Code.DOT,Code.DOT,Code.DOT}, 'H'));
        conversionTable.put('I', new Code(new int[]{Code.DOT,Code.DOT}, 'I'));
        conversionTable.put('J', new Code(new int[]{Code.DOT,Code.DASH,Code.DASH,Code.DASH}, 'J'));
        conversionTable.put('K', new Code(new int[]{Code.DASH, Code.DOT,Code.DASH}, 'K'));
        conversionTable.put('L', new Code(new int[]{Code.DOT, Code.DASH,Code.DOT,Code.DOT}, 'L'));
        conversionTable.put('M', new Code(new int[]{Code.DASH, Code.DASH}, 'M'));
        conversionTable.put('N', new Code(new int[]{Code.DASH, Code.DOT}, 'N'));
        conversionTable.put('O', new Code(new int[]{Code.DASH, Code.DASH,Code.DASH}, 'O'));
        conversionTable.put('P', new Code(new int[]{Code.DOT, Code.DASH,Code.DASH,Code.DOT}, 'P'));
        conversionTable.put('Q', new Code(new int[]{Code.DASH, Code.DASH,Code.DOT,Code.DASH}, 'Q'));
        conversionTable.put('R', new Code(new int[]{Code.DOT, Code.DASH,Code.DOT}, 'R'));
        conversionTable.put('S', new Code(new int[]{Code.DOT, Code.DOT,Code.DOT}, 'S'));
        conversionTable.put('T', new Code(new int[]{Code.DASH}, 'T'));
        conversionTable.put('U', new Code(new int[]{Code.DOT, Code.DOT,Code.DASH}, 'U'));
        conversionTable.put('V', new Code(new int[]{Code.DOT, Code.DOT,Code.DOT,Code.DASH}, 'V'));
        conversionTable.put('W', new Code(new int[]{Code.DOT, Code.DASH,Code.DASH}, 'W'));
        conversionTable.put('X', new Code(new int[]{Code.DASH, Code.DOT,Code.DOT,Code.DASH}, 'X'));
        conversionTable.put('Y', new Code(new int[]{Code.DASH, Code.DOT,Code.DASH,Code.DASH}, 'Y'));
        conversionTable.put('Z', new Code(new int[]{Code.DASH, Code.DASH,Code.DOT,Code.DOT}, 'Z'));


        conversionTable.put('0', new Code(new int[]{Code.DASH, Code.DASH,Code.DASH,Code.DASH,Code.DASH}, '0'));
        conversionTable.put('1', new Code(new int[]{Code.DOT, Code.DASH,Code.DASH,Code.DASH,Code.DASH},'1'));
        conversionTable.put('2', new Code(new int[]{Code.DOT, Code.DOT,Code.DASH,Code.DASH,Code.DASH},'2'));
        conversionTable.put('3', new Code(new int[]{Code.DOT, Code.DOT,Code.DOT,Code.DASH,Code.DASH},'3'));
        conversionTable.put('4', new Code(new int[]{Code.DOT, Code.DOT,Code.DOT,Code.DOT,Code.DASH},'4'));
        conversionTable.put('5', new Code(new int[]{Code.DOT, Code.DOT,Code.DOT,Code.DOT,Code.DOT},'5'));
        conversionTable.put('6', new Code(new int[]{Code.DASH, Code.DOT,Code.DOT,Code.DOT,Code.DOT},'6'));
        conversionTable.put('7', new Code(new int[]{Code.DASH, Code.DASH,Code.DOT,Code.DOT,Code.DOT},'7'));
        conversionTable.put('8', new Code(new int[]{Code.DASH, Code.DASH,Code.DASH,Code.DOT,Code.DOT},'8'));
        conversionTable.put('9', new Code(new int[]{Code.DASH, Code.DASH,Code.DASH,Code.DASH,Code.DOT},'9'));

        conversionTable.put('.', new Code(new int[]{Code.DOT, Code.DASH,Code.DOT,Code.DASH,Code.DOT, Code.DASH},'.'));
        conversionTable.put(',', new Code(new int[]{Code.DASH, Code.DASH,Code.DOT,Code.DOT,Code.DASH, Code.DASH},','));
        conversionTable.put('?', new Code(new int[]{Code.DOT, Code.DOT,Code.DASH,Code.DASH,Code.DOT, Code.DOT},'?'));
        conversionTable.put('\'', new Code(new int[]{Code.DOT, Code.DASH,Code.DASH,Code.DASH,Code.DASH, Code.DOT},'\''));
        conversionTable.put('!', new Code(new int[]{Code.DASH, Code.DOT,Code.DASH,Code.DOT,Code.DASH, Code.DASH},'!'));
        conversionTable.put('/', new Code(new int[]{Code.DASH, Code.DOT,Code.DOT,Code.DASH,Code.DOT},'/'));
        conversionTable.put('(', new Code(new int[]{Code.DASH, Code.DOT,Code.DASH,Code.DASH,Code.DOT},'('));
        conversionTable.put(')', new Code(new int[]{Code.DASH, Code.DOT,Code.DASH,Code.DASH,Code.DOT, Code.DASH},')'));
        conversionTable.put('&', new Code(new int[]{Code.DOT, Code.DASH,Code.DOT,Code.DOT,Code.DOT},'&'));
        conversionTable.put(':', new Code(new int[]{Code.DASH, Code.DASH,Code.DASH,Code.DOT,Code.DOT, Code.DOT},':'));
        conversionTable.put(';', new Code(new int[]{Code.DASH, Code.DOT,Code.DASH,Code.DOT,Code.DASH, Code.DOT}, ';'));
        conversionTable.put('=', new Code(new int[]{Code.DASH, Code.DOT,Code.DOT,Code.DOT,Code.DASH},'='));
        conversionTable.put('+', new Code(new int[]{Code.DOT, Code.DASH,Code.DOT,Code.DASH,Code.DOT},'+'));
        conversionTable.put('-', new Code(new int[]{Code.DASH, Code.DOT,Code.DOT,Code.DOT,Code.DOT, Code.DASH},'-'));
        conversionTable.put('_', new Code(new int[]{Code.DOT, Code.DOT,Code.DASH,Code.DASH,Code.DOT, Code.DASH},'_'));
        conversionTable.put('"', new Code(new int[]{Code.DOT, Code.DASH,Code.DOT,Code.DOT,Code.DASH, Code.DOT},'"'));
        conversionTable.put('$', new Code(new int[]{Code.DOT, Code.DOT,Code.DOT,Code.DASH,Code.DOT, Code.DOT, Code.DASH},'$'));
        conversionTable.put('@', new Code(new int[]{Code.DOT, Code.DASH,Code.DASH,Code.DOT,Code.DASH, Code.DOT},'@'));

        validChars = conversionTable.keySet();


    }

    public Code encode(char character) {
        char upperCased = Character.toUpperCase(character);
        return (Code)conversionTable.get(upperCased);
    }

    public List<List<Code>> encode(String message) {

        List<List<Code>> wordsList = new LinkedList<List<Code>>();

        String[] words = message.split(" ");

        for (int j = 0; j < words.length; j++) {
            String word = words[j];
            List<Code> wordList = new LinkedList<Code>();
            char[] chars = word.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char aChar = chars[i];
                Code encodedChar = encode(aChar);
                wordList.add(encodedChar);
            }
            wordsList.add(wordList);
        }
        return wordsList;

    }

    public boolean isEncodeable(char character) {
        char upperCased = Character.toUpperCase(character);
        return validChars.contains(upperCased);
    }

    public boolean isEncodeable(String message) {
        String[] words = message.split(" ");
        for (int j = 0; j < words.length; j++) {
            String word = words[j];
            char[] chars = word.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char aChar = chars[i];
                if (!isEncodeable(aChar)) {
                    return false;
                }

            }
        }

        return true;

    }
}
