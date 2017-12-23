package io.github.riwcwt.helloatomikos.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

@Configuration
@EnableTransactionManagement
public class ApplicationConfig {

    @Bean(name = "atomikosUserTransactionManager", destroyMethod = "close", initMethod = "init")
    public UserTransactionManager getUserTransactionManager() {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(true);
        return userTransactionManager;
    }

    @Bean(name = "atomikosUserTransaction")
    public UserTransactionImp getUserTransactionImp() throws SystemException {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        userTransactionImp.setTransactionTimeout(300);
        return userTransactionImp;
    }

    @Bean(name = "transactionManager")
    public JtaTransactionManager getJtaTransactionManager(@Qualifier("atomikosUserTransactionManager") UserTransactionManager atomikosUserTransactionManager, @Qualifier("atomikosUserTransaction") UserTransaction userTransaction) {
        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
        jtaTransactionManager.setUserTransaction(userTransaction);
        jtaTransactionManager.setTransactionManager(atomikosUserTransactionManager);
        return jtaTransactionManager;
    }

}
