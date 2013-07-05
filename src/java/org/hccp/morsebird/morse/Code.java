package org.hccp.morsebird.morse;

/**
 * Created with IntelliJ IDEA.
 * User: ibrown
 * Date: 7/4/13
 * Time: 10:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class Code {


    public static final int DOT=1;
    public static final int DASH=0;

    private int[] sequence;

    public Code(int[] sequence){
        this.sequence=sequence;
    }

    public int[] getSequence() {
        return sequence;
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
