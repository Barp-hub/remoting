package io.github.riwcwt.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;


@Configuration
@EnableTransactionManagement
@MapperScan(value = {"io.github.riwcwt.mapper"}, sqlSessionFactoryRef = "console-sqlSessionFactory")
public class ConsoleDatabaseConfig {
    @Autowired
    private Environment env;

    @Bean(name = "console-dataSource", initMethod = "init", destroyMethod = "close")
    public DruidDataSource dataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(env.getProperty("console.jdbc.driver-class-name"));
        dataSource.setUrl(env.getProperty("console.jdbc.url"));
        dataSource.setUsername(env.getProperty("console.jdbc.username"));
        dataSource.setPassword(env.getProperty("console.jdbc.password"));

        dataSource.setFilters("log4j");
        dataSource.setMaxActive(20);
        dataSource.setInitialSize(1);
        dataSource.setMaxWait(60000);
        dataSource.setMinIdle(1);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(300000);

        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);

        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxOpenPreparedStatements(20);

        return dataSource;
    }

    @Bean(name = "console-transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("console-dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "console-sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("console-dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(new DefaultResourceLoader().getResource("classpath:mybatis/config.xml"));
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/console/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

}
