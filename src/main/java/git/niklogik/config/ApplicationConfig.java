package git.niklogik.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ApplicationConfig.EPSGProperties.class)
public class ApplicationConfig {

    @Getter
    @RequiredArgsConstructor
    @ConfigurationProperties(prefix = "app.epsg")
    public static class EPSGProperties {
        private final Integer webMercator;
        private final Integer wgs84;
    }
}
