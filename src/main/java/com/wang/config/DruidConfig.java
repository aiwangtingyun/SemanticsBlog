package com.wang.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;

@Configuration
public class DruidConfig {

    // 配置 druid 数据源
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid() {
        return new DruidDataSource();
    }

    // 配置 Druid 的监控，配置好后就可以访问 druid 的管理后台：http://localhost:8080/druid/
    // 1、配置一个管理后台的 Servlet
    @Bean
    public ServletRegistrationBean statViewServlet() {
        // 使用 druid 的 Servlet 实现类 StatViewServlet
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");

        // 初始化参数
        HashMap<String, String> initParams = new HashMap<>();
        initParams.put("loginUsername", "admin");
        initParams.put("loginPassword", "123456");
        initParams.put("allow", "");    // 默认则允许所有访问
        initParams.put("deny", "");     // 默认则不拒接访问

        bean.setInitParameters(initParams);
        return bean;
    }

    // 2、配置一个 web 监控的 filter
    @Bean
    public FilterRegistrationBean webStatFilter() {
        // 使用 druid 的 filter 实现类 WebStatFilter
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());

        // 初始化参数
        HashMap<String, String> initParams = new HashMap<>();
        initParams.put("exclusions", "*.js,*.css,/druid/*");    // 不过滤静态资源

        bean.setInitParameters(initParams);
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }
}
