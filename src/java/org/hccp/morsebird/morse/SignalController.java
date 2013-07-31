package org.hccp.morsebird.morse;

/**
 * Created with IntelliJ IDEA.
 * User: ibrown
 * Date: 7/21/13
 * Time: 11:03 AM
 * To change this template use File | Settings | File Templates.
 */
public interface SignalController {

    public void setUnitInMillis(int unitInMillis);
    public void dash() throws InterruptedException;
    public void dot() throws InterruptedException;
    public void intraCharacterGap() throws InterruptedException;
    public void shortGap() throws InterruptedException;
    public void mediumGap() throws InterruptedException;



}
