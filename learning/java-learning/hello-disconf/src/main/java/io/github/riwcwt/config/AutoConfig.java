package io.github.riwcwt.config;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import com.baidu.disconf.client.common.annotations.DisconfItem;
import org.springframework.stereotype.Component;

@Component
@DisconfFile(filename = "autoconfig.properties")
public class AutoConfig {
    private String auto;

    private String name;

    @DisconfFileItem(name = "auto", associateField = "auto")
    public String getAuto() {
        return auto;
    }

    public void setAuto(String auto) {
        this.auto = auto;
    }

    @DisconfItem(key = "application.name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
