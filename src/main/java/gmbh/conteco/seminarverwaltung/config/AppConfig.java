package gmbh.conteco.seminarverwaltung.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {
    @Value("${gmbh.conteco.frontend.url}")
    public String frontendUrl;
}