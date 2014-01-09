package org.hccp.morsebird.rpi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created with IntelliJ IDEA.
 * User: ibrown
 * Date: 1/1/14
 * Time: 9:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class PythonBuzzerController extends AbstractSignalController {

    private Runtime rt;
    private final int pin;
    private float pitch=200;
    private PrintStream controlOutput;


    public PythonBuzzerController() throws IOException, InterruptedException {

        pin = Integer.parseInt(properties.getProperty("buzzer.pin"));
        String buzzerController = properties.getProperty("buzzer.control.executable");
        rt = Runtime.getRuntime();
        String cmd = buzzerController + " " + pin;
        System.out.println("cmd = " + cmd);
        Process buzzProc = rt.exec(cmd);
        OutputStream os = buzzProc.getOutputStream();
        controlOutput = new PrintStream(os);
        final InputStream err = buzzProc.getErrorStream();
        final InputStream is = buzzProc.getInputStream();

        Thread x = new Thread(){
            public void run() {
                   try {
                    for (int x = 0; x != -1; x = is.read() ){
                        System.out.print((char)x);
                        System.out.flush();
                    }
                   } catch (IOException e) {
                       e.printStackTrace();
                   }

                    System.out.flush();
            }

        };
        x.start();

        Thread y = new Thread(){
            public void run() {
                try {
                    for (int x = 0; x != -1; x = err.read() ){
                        System.out.print((char)x);
                        System.out.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.flush();
            }

        };
        y.start();


        System.out.println("done");


    }


    @Override
    protected void off() throws InterruptedException, IOException {

    }

    @Override
    protected void on() throws InterruptedException, IOException {
        System.out.println(unitInMillis / 1000);
        controlOutput.println(pitch + " " + (unitInMillis / 1000));
        controlOutput.flush();
    }
}
