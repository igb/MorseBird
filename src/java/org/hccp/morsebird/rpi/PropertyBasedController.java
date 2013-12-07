package org.hccp.morsebird.rpi;

import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: ibrown
 * Date: 12/7/13
 * Time: 1:39 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class PropertyBasedController {
    protected Properties properties;


    public PropertyBasedController() {
        try {
            properties.load(this.getClass().getResourceAsStream("morsebird.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
