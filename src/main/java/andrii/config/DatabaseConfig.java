package andrii.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@Component
@EnableTransactionManagement
public class DatabaseConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConfig.class);

    @Value("${db.password}")
    private String dbPassword;

    @Bean
    public DataSource dataSource() {
        return  createDataSource("127.0.0.1", "3306", "messenger", "root", dbPassword);
    }

    private static DataSource createDataSource(String address,
                                               String port,
                                               String dbName,
                                               String dbUser,
                                               String dbPassword) {
        try {
            ComboPooledDataSource c3p0 = new ComboPooledDataSource();
            c3p0.setDriverClass("com.mysql.jdbc.Driver");
            c3p0.setJdbcUrl(String.format("jdbc:mysql://%s:%s/%s", address, port, dbName));
            c3p0.setUser(dbUser);
            c3p0.setPassword(dbPassword);
            return c3p0;
        } catch (PropertyVetoException e) {
            e.printStackTrace();
            LOGGER.error("Something happened with DataSource creating", e);
            throw new RuntimeException(e);
        }
    }

    @Bean
    public LocalSessionFactoryBean localSessionFactoryBean() {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource());
        localSessionFactoryBean.setPackagesToScan("andrii");
        localSessionFactoryBean.setHibernateProperties(hibernateProperties());
        return localSessionFactoryBean;
    }

    private Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto", "validate");
                setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
                setProperty("hibernate.show_sql", "true");
            }
        };
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager (SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

}
