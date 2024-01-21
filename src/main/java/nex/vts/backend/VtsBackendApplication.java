package nex.vts.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

import java.util.Date;

@SpringBootApplication
@EnableRetry
public class VtsBackendApplication {
    public static void main(String[] args) {

        SpringApplication.run(VtsBackendApplication.class, args);

        System.out.println("\n============================================");
        System.out.println("Welcome to the VTS APIs Management System");
        System.out.println("Date in GMT: " + new Date());
        System.out.println("============================================\n");

        System.out.println("APIs Service start...\n");

    }
}
