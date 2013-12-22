package org.hccp.morsebird.morse;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: ibrown
 * Date: 12/19/13
 * Time: 2:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class DummySignalController implements SignalController{

    private int unit;

    @Override
    public void setUnitInMillis(int unitInMillis) {
        this.unit=unitInMillis;
        System.out.println("unitInMillis = " + unitInMillis);
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

    @Override
    public void dash() {
        try {
            Thread.sleep(3 * unit);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dot() {
        try {
            Thread.sleep(unit);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
