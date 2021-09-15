package com.zds.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * description
 *
 * @author zhongdongsheng
 * @datetime 2021/9/9 23:19
 */
@ConfigurationProperties(prefix = "swagger")
@Data
public class SwaggerProperties {
    private String host;
    private String port;
    private boolean enabled;
}
