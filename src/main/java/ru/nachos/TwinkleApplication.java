package ru.nachos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Для использования на Tomcat нужно расширить абстрактный класс {@link SpringBootServletInitializer}
 */
@SpringBootApplication
public class TwinkleApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TwinkleApplication.class, args);
    }
}
