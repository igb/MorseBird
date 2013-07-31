package org.hccp.morsebird.morse;

/**
 * –– ––– ·–· ··· ·
 */
public class Code {

    public static final int DOT = Signal.DOT.getValue();
    public static final int DASH = Signal.DASH.getValue();
    private char value = ' ';

    private int[] sequence;

    public Code(int[] sequence){
        this.sequence=sequence;
    }

    public Code(int[] sequence, char value){
        this.sequence=sequence;
        this.value=value;
    }

    public int[] getSequence() {
        return sequence;
    }

    public char getValue(){
        return value;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < sequence.length; i++) {
            int code = sequence[i];
            if (code == 1) {
                sb.append("·");
            } else {
                sb.append("–");
            }
        }
        return sb.toString();
    }
}