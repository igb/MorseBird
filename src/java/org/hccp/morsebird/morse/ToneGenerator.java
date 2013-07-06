package org.hccp.morsebird.morse;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.util.List;

/**
 *
 * Beeeep. Beep. Beeeeeeeeeeeeeeeeeeep.
 *
 * http://stackoverflow.com/questions/1932490/java-generating-sound
 */
public class ToneGenerator {

    public static final int UNIT = 50;

    public static void main(String[] args) throws LineUnavailableException, InterruptedException {
        ToneGenerator tg = new ToneGenerator();
        tg.generateDash();
        tg.interElementGap();
        tg.generateDot();


    }

    public void interElementGap() throws InterruptedException {
        Thread.sleep(UNIT);
    }

    public void shortGap() throws InterruptedException {
        Thread.sleep(3 * UNIT);
    }

    public void mediumGap() throws InterruptedException {
        Thread.sleep(7 * UNIT);
    }

    public void generateDot() throws LineUnavailableException {
        generateTone(UNIT);
    }

    public void generateDash() throws LineUnavailableException {
        generateTone(3 * UNIT);
    }

    public void generateToneForCode(Code code) throws LineUnavailableException, InterruptedException {
        int[] sequence = code.getSequence();
        for (int i = 0; i < sequence.length; i++) {
            int element = sequence[i];
            if (element == Code.DASH) {
                generateDash();
            } else {
                generateDot();
            }
            if (i != sequence.length - 1) {
                interElementGap();
            }
        }
        shortGap();
    }

    public void generateToneForWord(List<Code> word) throws LineUnavailableException, InterruptedException {
        for (int i = 0; i < word.size(); i++) {
            Code code =  word.get(i);
            generateToneForCode(code);
        }
        mediumGap();
    }

    public void generateTone(int duration) throws LineUnavailableException {
        byte[] buf = new byte[1];
        AudioFormat af = new AudioFormat((float) 44100, 8, 1, true, false);
        SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
        sdl.open(af);
        sdl.start();
        for (int i = 0; i < duration * (float) 44100 / 1000; i++) {
            double angle = i / ((float) 44100 / 440) * 2.0 * Math.PI;
            buf[0] = (byte) (Math.sin(angle) * 100);
            sdl.write(buf, 0, 1);
        }
        sdl.drain();
        sdl.stop();
        sdl.close();
    }

}
