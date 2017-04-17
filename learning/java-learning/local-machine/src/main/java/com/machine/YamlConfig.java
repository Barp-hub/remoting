package com.machine;

import com.alibaba.fastjson.JSON;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Map;

/**
 * Created by michael on 2017-04-17.
 */
public class YamlConfig {

    public static void main(String[] args) {
        try {
            Yaml yaml = new Yaml();
            URL url = YamlConfig.class.getClassLoader().getResource("config.yaml");
            if (url != null) {
                //获取config.yaml文件中的配置数据，然后转换为obj，
                Object obj = yaml.load(new FileInputStream(url.getFile()));
                System.out.println(obj);
                //也可以将值转换为Map
                Map map = (Map) yaml.load(new FileInputStream(url.getFile()));
                System.out.println(map);
                //通过map我们取值就可以了.
                System.out.println(JSON.toJSONString(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
