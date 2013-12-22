package org.hccp.morsebird.rpi;

import org.hccp.morsebird.Configurable;
import org.hccp.morsebird.morse.SignalController;

import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


/**
 * Created with IntelliJ IDEA.
 * User: ibrown
 * Date: 11/30/13
 * Time: 4:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class RateController implements Runnable, Configurable {


    private int unitInMilliseconds;



    private List<SignalController> signalControllers;
    private final BlockingQueue stateChanges;
    private Properties properties;


    public RateController(int unitInMilliseconds) throws FileNotFoundException {

        System.out.println("in RateController init..");
        signalControllers=new LinkedList<SignalController>();
        this.unitInMilliseconds = unitInMilliseconds;
        stateChanges = new ArrayBlockingQueue(1000);


    }


    public void setSignalControllers(List<SignalController> signalControllers) {
        this.signalControllers = signalControllers;
    }

    @Override
    public void run() {


        int lastReadState=0;
        int pinA=0;
        int pinB=0;

        System.out.println("in RateController run()...");

        PinPoller pinPoller = new PinPoller(properties.getProperty("rateControlExecutable"), Integer.parseInt(properties.getProperty("encoder.a.pin")), Integer.parseInt(properties.getProperty("encoder.b.pin")), stateChanges);
        System.out.println("starting pin poller...");

        Thread pinPollerThread = new Thread(pinPoller);
        pinPollerThread.setName("pin poller");
        pinPollerThread.start();

        while (true) {

            String state = null;

            try {
                state = (String) stateChanges.take();
                String[] stateStruct = state.split(" ");
                String pin = stateStruct[0];

                int pinValue = Boolean.parseBoolean(stateStruct[1]) ? 1 : 0;
                if ("A".equals(pin)) {
                    pinA = pinValue;
                } else {
                    pinB =  pinValue;
                    String boolstring = pinA + "" + pinB;
                    int currentState = Integer.parseInt(boolstring, 2);
                    int value = (lastReadState << 2)^currentState;
                    boolean isFwd=false;
                    switch(value) {
                        case 14:
                            isFwd=true;
                        case 8:
                            isFwd=true;
                        case 1:
                            isFwd=true;
                        case 7:
                            isFwd=true;
                    }
                    if(isFwd) {
                        unitInMilliseconds++;
                    } else {
                        if (unitInMilliseconds > 1) unitInMilliseconds--;
                    }
                    System.out.println(unitInMilliseconds);
                    updateSignalControllers();
                    lastReadState=currentState;

                }


            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }



        }


    }


    public void updateSignalControllers() {
        for (int i = 0; i < signalControllers.size(); i++) {
            SignalController signalController = signalControllers.get(i);
            signalController.setUnitInMillis(unitInMilliseconds);
        }
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    private class PinPoller implements Runnable {

        private BlockingQueue stateChanges;
        private int pinA;
        private int pinB;
        private String executable;


        private PinPoller(String executable, int pinA, int pinB, BlockingQueue stateChanges) {

            System.out.println("in pinpoller init()");

            this.pinA = pinA;
            this.pinB = pinB;
            this.executable=executable;


            this.stateChanges = stateChanges;

        }

        @Override
        public void run() {
            System.out.println("in pinpoller run()");

            Runtime rt = Runtime.getRuntime();

            Process readPin = null;
            try {
                System.out.println("execking python");
                readPin = rt.exec(executable + " " + pinA + " " + pinB);
                System.out.println("python execked");
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputStream is = readPin.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            String line;

            System.out.println("python execked 2");

            try {
                while ((line = reader.readLine()) != null) {
                    stateChanges.put(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
    }

}
