package io.github.riwcwt.helloatomikos.config;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@MapperScan(value = {"io.github.riwcwt.express.mapper"}, sqlSessionFactoryRef = "express-sqlSessionFactory")
public class ExpressDatabaseConfig {
    @Autowired
    private Environment environment = null;

    @Bean(name = "express-dataSource")
    public DataSource datasource() {
        DruidXADataSource dataSource = new DruidXADataSource();

        dataSource.setDriverClassName(environment.getProperty("express.jdbc.driver-class-name"));
        dataSource.setUrl(environment.getProperty("express.jdbc.url"));
        dataSource.setUsername(environment.getProperty("express.jdbc.username"));
        dataSource.setPassword(environment.getProperty("express.jdbc.password"));

        AtomikosDataSourceBean dataSourceBean = new AtomikosDataSourceBean();
        dataSourceBean.setXaDataSource(dataSource);
        dataSourceBean.setUniqueResourceName("express-dataSource");
        return dataSourceBean;
    }

    @Bean(name = "express-sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("express-dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

        PageInterceptor helper = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("reasonable", "true");
        helper.setProperties(properties);

        sqlSessionFactoryBean.setPlugins(new Interceptor[]{helper});

        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(new DefaultResourceLoader().getResource("classpath:mybatis/config.xml"));
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/express/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

}
