package org.hccp.morsebird.morse;

/**
 * Created with IntelliJ IDEA.
 * User: ibrown
 * Date: 7/30/13
 * Time: 2:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class Signal {

    public static final Signal MEDIUM_GAP = new Signal(4);
    public static final Signal SHORT_GAP = new Signal(3);
    public static final Signal INTRA_CHARACTER_GAP = new Signal(2);
    public static final Signal DOT = new Signal(1);
    public static final Signal DASH = new Signal(0);

    private final int value;


    private Signal(int i) {
        this.value = i;
    }

    public int getValue() {
        return value;
    }
}
