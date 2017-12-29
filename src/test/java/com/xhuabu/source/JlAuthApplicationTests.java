package com.xhuabu.source;

import com.xhuabu.source.config.PropertiesConfiguration;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class JlAuthApplicationTests extends BaseTest{


    @Autowired
    PropertiesConfiguration propertiesConfiguration;

    /**
     *  白名单加载(有)
     */
    @Test
    public void getProperties(){
        logger.info(propertiesConfiguration.getIpWhiteList());
    }

    /**
     *  白名单加载(无)
     */
    @Test
    public void getProperties2(){
        logger.info(propertiesConfiguration.getIpWhiteList());
    }


}
