package top.jgao.config.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

/**
 * MyBatis扫描接口，使用的tk.mybatis.spring.mapper.MapperScannerConfigurer，
 * 如果你不使用通用Mapper，可以改为org.xxx...
 */
@Configuration
//TODO 注意，由于MapperScannerConfigurer执行的比较早，所以必须有下面的注解
@AutoConfigureBefore({PrimaryDBConfig.class})
public class MyBatisMapperScannerConfig {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public MapperScannerConfigurer primaryMapperScannerConfigurer() {
        logger.info("设置primaryMapperScannerConfigurer");
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("primarySqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.example.demo.common.persistence.mapper.primary");
        Properties properties = new Properties();
        properties.setProperty("mappers", "MyMapper");
        properties.setProperty("notEmpty", "false");
        properties.setProperty("IDENTITY", "MYSQL");
        mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;
    }
}
