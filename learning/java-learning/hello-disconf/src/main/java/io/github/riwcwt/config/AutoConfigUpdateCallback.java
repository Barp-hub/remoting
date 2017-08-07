package io.github.riwcwt.config;

import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.update.IDisconfUpdate;
import org.springframework.stereotype.Service;

@Service
@DisconfUpdateService(classes = {AutoConfig.class})
public class AutoConfigUpdateCallback implements IDisconfUpdate {
    @Override
    public void reload() throws Exception {
        System.out.println("reload配置文件...");
    }
}
