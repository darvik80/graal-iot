package xyz.crearts.iot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(
        exclude = SpringDataWebAutoConfiguration.class,
        proxyBeanMethods = false
)
@EnableScheduling
public class GraalIotApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraalIotApplication.class, args);
    }

}
