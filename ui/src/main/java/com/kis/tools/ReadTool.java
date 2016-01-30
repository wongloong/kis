package com.kis.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by yks on 15-10-9.
 */
public class ReadTool {
    private Logger logger = LoggerFactory.getLogger(ReadTool.class);
    private static ReadTool readTool = new ReadTool();
    String path = "/application.properties";

    private ReadTool() {
    }
    public static ReadTool getInstance(){
        return readTool;
    }
    /**
     *
     * path is /resource/config.properties
     * @param key  get value by key
     * @return "" or String
     */
    public String getProperties(String key) {
        Properties props = new Properties();
        InputStream in=null;
        String result="";
        try {
            in = getClass().getResourceAsStream(path);
            props.load(in);
            result = props.getProperty(key);
        } catch (Exception e1) {
            logger.error(e1.getMessage());
            e1.printStackTrace();
        }finally {
            try {
                if(null!=in){
                    in.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return result;
    }
}
