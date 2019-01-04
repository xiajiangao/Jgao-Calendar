package top.jgao.config.db;

import com.github.pagehelper.PageInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class MybatisInterceptor {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public PageInterceptor initPageInterceptor() {
        logger.debug("初始化分页插件拦截器");
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }
}
