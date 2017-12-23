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
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@MapperScan(value = {"io.github.riwcwt.order.mapper"}, sqlSessionFactoryRef = "order-sqlSessionFactory")
public class OrderDatabaseConfig {

    @Autowired
    private Environment environment = null;

    @Primary
    @Bean(name = "order-dataSource")
    public DataSource datasource() {
        DruidXADataSource dataSource = new DruidXADataSource();

        dataSource.setDriverClassName(environment.getProperty("order.jdbc.driver-class-name"));
        dataSource.setUrl(environment.getProperty("order.jdbc.url"));
        dataSource.setUsername(environment.getProperty("order.jdbc.username"));
        dataSource.setPassword(environment.getProperty("order.jdbc.password"));

        AtomikosDataSourceBean dataSourceBean = new AtomikosDataSourceBean();
        dataSourceBean.setXaDataSource(dataSource);
        dataSourceBean.setUniqueResourceName("order-dataSource");
        return dataSourceBean;
    }

    @Primary
    @Bean(name = "order-sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("order-dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

        PageInterceptor helper = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("reasonable", "true");
        helper.setProperties(properties);

        sqlSessionFactoryBean.setPlugins(new Interceptor[]{helper});

        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(new DefaultResourceLoader().getResource("classpath:mybatis/config.xml"));
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/order/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

}
