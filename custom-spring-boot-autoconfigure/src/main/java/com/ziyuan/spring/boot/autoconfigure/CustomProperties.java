package com.ziyuan.spring.boot.autoconfigure;


import com.ziyuan.spring.boot.autoconfigure.handler.CustomHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
        prefix = "custom"
)
public class CustomProperties {

    private String name;

    private Integer version;

    private Boolean enough;

    private CustomHandler handler;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getEnough() {
        return enough;
    }

    public void setEnough(Boolean enough) {
        this.enough = enough;
    }

    public CustomHandler getHandler() {
        return handler;
    }

    public void setHandler(CustomHandler handler) {
        this.handler = handler;
    }
}
