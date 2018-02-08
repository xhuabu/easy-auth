package com.xhuabu.source.common.cache;

import com.xhuabu.source.config.JLAuthConfiguration;
import org.springframework.stereotype.Component;

/**
 * Created by lee on 18/1/30.
 * ${DESCRIPTION}
 *
 * @CreatedBy lee
 * @Date 18/1/30
 */
@Component
public class CryptCache {

    private JLAuthConfiguration passwordEncoder;

    public JLAuthConfiguration getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(JLAuthConfiguration passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
