package top.jgao.config.db;


import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

@SuppressWarnings("all")
@Configuration
@Slf4j
public class PrimaryDBConfig {

    @Autowired
    private MybatisInterceptor interceptor;

    @Bean(name = "primaryDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource() {
        log.info("设置primaryDataSource");
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "primarySqlSessionFactory")
    @Primary
    public SqlSessionFactory primarySqlSessionFactory() throws Exception {
        log.info("设置primarySqlSessionFactory");
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(primaryDataSource());
        // 分页插件
        bean.setPlugins(new Interceptor[]{interceptor.initPageInterceptor()});
        try {
            // 指定mapper xml目录
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            bean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
            bean.setConfigLocation(new ClassPathResource("mybatis/mybatis-config.xml"));
            return bean.getObject();
        } catch (Exception e) {
            throw e;
        }
    }

}
