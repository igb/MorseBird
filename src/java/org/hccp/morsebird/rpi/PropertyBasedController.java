package org.hccp.morsebird.rpi;


import java.io.*;
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

    public static InputStream getPropertyInputStream() throws FileNotFoundException {
        Class clazz = PropertyBasedController.class.getClass().getClass();

        InputStream resourceAsStream = clazz.getResourceAsStream("/morsebird.properties");
        if (resourceAsStream != null) {
            return resourceAsStream;
        } else {
            String propsFile=System.getProperty("morsebird.properties.file");
            return new FileInputStream(new File(propsFile));
        }

    }

    public static void main(String[] args) throws InterruptedException, FileNotFoundException {

        Object inputStream = getPropertyInputStream();
        System.out.println("inputStream = " + inputStream);
        new PropertyBasedController();
    }
}
