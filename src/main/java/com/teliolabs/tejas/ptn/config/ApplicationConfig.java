package com.teliolabs.tejas.ptn.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "config")
@Data
@ToString
public class ApplicationConfig {
    private NetworkManagerConfig networkManager;

    public boolean isnUseCache() {
        String useCacheStr = System.getProperty("config.isn.use-cache");
        if (ObjectUtils.isEmpty(useCacheStr)) return isnUseCache;

        return Boolean.parseBoolean(useCacheStr);
    }

    private boolean isnUseCache;

    public boolean isDbInsert() {
        String useCacheStr = System.getProperty("config.db-insert");
        if (ObjectUtils.isEmpty(useCacheStr)) return dbInsert;

        return Boolean.parseBoolean(useCacheStr);
    }

    @JsonProperty("db-insert")
    private boolean dbInsert;
}

