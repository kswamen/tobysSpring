package com.example.tobysspring.tobySrc.chapter7.a;


import com.example.tobysspring.tobySrc.chapter7.a.service.SimpleSqlService;
import com.example.tobysspring.tobySrc.chapter7.a.service.SqlService;
import com.example.tobysspring.tobySrc.chapter7.a.service.TestUserServiceImpl;
import com.example.tobysspring.tobySrc.chapter7.a.service.UserServiceImpl;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.sql.Driver;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class DaoFactory {
    @Bean
    public UserDao userDaoJdbc() {
        return new UserDaoJdbc();
    }

    @Bean
    public UserServiceImpl userService() {
        return new UserServiceImpl();
    }

    @Bean
    public TestUserServiceImpl testUserService() {
        return new TestUserServiceImpl(userDaoJdbc());
    }

    @Bean
    public JdbcContext jdbcContext() {
        JdbcContext jdbcContext = new JdbcContext();
        jdbcContext.setDataSource(dataSource());
        return jdbcContext;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        try {
            SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

            dataSource.setDriverClass((Class<? extends Driver>) Class.forName("com.mysql.jdbc.Driver"));
            dataSource.setUrl("jdbc:mysql://localhost:3306/toby");
            dataSource.setUsername("root");
            dataSource.setPassword("root");

            return dataSource;
        } catch (Exception e) {
            System.out.println("Class not found Exception");
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    public MailSender mailSender() {
        return new DummyMailSender();
    }

//    @Bean
//    public TransactionAdvice transactionAdvice() {
//        TransactionAdvice advice = new TransactionAdvice();
//        advice.setTransactionManager(transactionManager());
//        return advice;
//    }

    @Bean
    public TransactionInterceptor transactionAdvice() {
        TransactionInterceptor advice = new TransactionInterceptor();
        Properties properties = new Properties();
        properties.put("get*", "PROPAGATION_REQUIRED, readOnly");
        properties.put("*", "PROPAGATION_REQUIRED");
        advice.setTransactionManager(transactionManager());
        advice.setTransactionAttributes(properties);
        return advice;
    }

    @Bean
    public AspectJExpressionPointcut transactionPointcut() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("bean(*Service)");
        return pointcut;
    }

    @Bean
    public DefaultPointcutAdvisor transactionAdvisor() {
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setAdvice(transactionAdvice());
        advisor.setPointcut(transactionPointcut());
        return advisor;
    }

//    @Bean
//    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
//        return new DefaultAdvisorAutoProxyCreator();
//    }

    @Bean
    public Map<String, String> sqlMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("userAdd", "insert into users(id, name, password, level, login, recommend) " +
                "values(?, ?, ?, ?, ?, ?)");
        map.put("userGet", "select * from users where id = ?");
        map.put("userGetAll", "select * from users order by id");
        map.put("userDeleteAll", "delete from users");
        map.put("userGetCount", "select count(*) from users");
        map.put("userUpdate", "update users set name = ?, password = ?, level = ?, login = ?, " +
                "recommend = ? where id = ?");
        return map;
    }

    @Bean
    public SqlService sqlService() {
        return new SimpleSqlService();
    }
}
