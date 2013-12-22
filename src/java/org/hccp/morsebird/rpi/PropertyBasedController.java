package org.hccp.morsebird.rpi;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: ibrown
 * Date: 12/7/13
 * Time: 1:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyBasedController {
    protected Properties properties;


    public PropertyBasedController() {
        try {
            properties = new Properties();
            InputStream inputStream = getPropertyInputStream();
            System.out.println("inputStream = " + inputStream);
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static InputStream getPropertyInputStream() {
        Class clazz = PropertyBasedController.class.getClass().getClass();
        return clazz.getResourceAsStream("/morsebird.properties");
    }

    public static void main(String[] args) throws InterruptedException {

        Object inputStream = getPropertyInputStream();
        System.out.println("inputStream = " + inputStream);
        new PropertyBasedController();
    }
}
