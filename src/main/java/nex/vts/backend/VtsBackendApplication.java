package nex.vts.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class VtsBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(VtsBackendApplication.class, args);
    }
}
