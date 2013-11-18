package org.hccp.morsebird.twitter;

import org.hccp.morsebird.morse.Code;
import org.hccp.morsebird.morse.Signal;
import org.hccp.morsebird.morse.SignalController;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: ibrown
 * Date: 7/30/13
 * Time: 2:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class SignalExecutor implements Runnable {

    private final SignalController controller;
    private final Signal signal;

    public SignalExecutor(SignalController controller, Signal signal) {
        this.controller = controller;
        this.signal = signal;
    }

    @Override
    public void run() {

      try {
       if (signal.equals(Signal.DASH)) {
           controller.dash();
       } else if (signal.equals(Signal.DOT)) {
           controller.dot();
       } else if (signal.equals(Signal.INTRA_CHARACTER_GAP)) {
           controller.intraCharacterGap();
       } else if (signal.equals(Signal.SHORT_GAP)) {
           controller.shortGap();
       } else if (signal.equals(Signal.MEDIUM_GAP)) {
           controller.mediumGap();
       }
      } catch (InterruptedException ie) {
          ie.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }

    }
}
