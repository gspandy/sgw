package cn.wanglin.sgw.exchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SgwApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SgwApplication.class);
        application.addListeners(new ExportApplicationListener());
        application.run(args);
    }
}
