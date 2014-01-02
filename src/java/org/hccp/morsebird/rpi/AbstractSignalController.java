package org.hccp.morsebird.rpi;

import org.hccp.morsebird.morse.SignalController;
import org.hccp.morsebird.twitter.MorseBird;

import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: ibrown
 * Date: 11/17/13
 * Time: 9:59 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractSignalController extends PropertyBasedController implements SignalController {


    protected int unitInMillis;


    @Override
    public void setUnitInMillis(int unitInMillis) {
        this.unitInMillis = unitInMillis;
    }

    @Override
    public void dash() throws InterruptedException, IOException {
        on();
        Thread.sleep(unitInMillis * 3);
        off();

    }

    protected abstract void off() throws InterruptedException, IOException;

    protected abstract void on() throws InterruptedException, IOException;

    @Override
    public void dot() throws InterruptedException, IOException {
        on();
        Thread.sleep(unitInMillis);
        off();

    }

    @Override
    public void intraCharacterGap() throws InterruptedException {
        Thread.sleep(unitInMillis);

    }

    @Override
    public void shortGap() throws InterruptedException {
        Thread.sleep(unitInMillis * 3);
    }

    @Override
    public void mediumGap() throws InterruptedException {
        Thread.sleep(unitInMillis * 7);
    }



}
