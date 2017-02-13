package io.github.riwcwt.mybatis;

import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GeneratorTest {

    @Test
    public void generator() throws IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {

        Properties properties = new Properties();
        properties.setProperty("target.project", System.getProperty("user.dir"));
        properties.setProperty("maven.repository.home", "E:/repository/maven");

        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        File configFile = new File("src/main/resources/mybatis/mybatis-generator-config.xml");
        ConfigurationParser configurationParser = new ConfigurationParser(properties, warnings);
        Configuration configuration = configurationParser.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, callback, warnings);
        myBatisGenerator.generate(null);
    }

    @Test
    public void path() throws URISyntaxException {
        System.out.println(ClassLoader.getSystemClassLoader().getResource(".").toURI().getPath());
        System.out.println(System.getProperty("user.dir"));
    }

}
