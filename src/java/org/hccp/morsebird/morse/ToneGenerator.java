package org.hccp.morsebird.morse;

import javax.sound.sampled.*;
import java.util.List;

/**
 *
 * Beeeep. Beep. Beeeeeeeeeeeeeeeeeeep.
 *
 * http://stackoverflow.com/questions/1932490/java-generating-sound
 * http://mindprod.com/jgloss/sound.html#SYNTHESISED
 */
public class ToneGenerator implements SignalController {


    public static void main ( String[] args ) throws LineUnavailableException, InterruptedException {
                ToneGenerator tg = new ToneGenerator();
        tg.generateDash();
        tg.interElementGap();
        tg.generateDot();
    }



    private int unit = 1000; // defaults to one second



    public ToneGenerator() {

    }


    public void interElementGap() throws InterruptedException {
        Thread.sleep(unit);
    }

    @Override
    public void setUnitInMillis(int unitInMillis) {
        this.unit=unitInMillis;
    }

    @Override
    public void dash() {
        try {
            generateDash();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dot() {
        try {
            generateDot();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void intraCharacterGap() throws InterruptedException {
        Thread.sleep(unit);
    }

    public void shortGap() throws InterruptedException {
        Thread.sleep(3 * unit);
    }

    public void mediumGap() throws InterruptedException {
        Thread.sleep(7 * unit);
    }

    public void generateDot() throws LineUnavailableException {
        generateTone(unit);
    }

    public void generateDash() throws LineUnavailableException {
        generateTone(3 * unit);
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

    /**
     * Generate a tone.
     * @param duration_in_millis duration of tone in milliseconds
     * @throws LineUnavailableException
     */
    public void generateTone(int duration_in_millis) throws LineUnavailableException {
        double duration = 0.001 * duration_in_millis;
        int sampleRate = 8000;
        double frequency = 1000.0;
        double RAD = 2.0 * Math.PI;

        AudioFormat af = new AudioFormat((float) sampleRate, 16, 1, true, true);
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, af);
        SourceDataLine source = (SourceDataLine) AudioSystem.getLine(info);
        source.open(af);
        source.start();
        byte[] buf = new byte[(int) Math.round(sampleRate * duration)];
        for (int i = 0; i < buf.length; i++) {
            buf[i] =(byte) (Math.sin(RAD * frequency / sampleRate * i) * 127.0);
        }

        source.write(buf, 0, buf.length);
        source.drain();
        source.stop();
        source.close();

    }

}
