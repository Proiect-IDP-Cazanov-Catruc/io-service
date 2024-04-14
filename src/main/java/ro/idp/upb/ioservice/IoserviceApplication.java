package ro.idp.upb.ioservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class IoserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(IoserviceApplication.class, args);
    }

}
