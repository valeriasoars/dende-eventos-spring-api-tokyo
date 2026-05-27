package tokyo_spring_api.dende_eventos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Dedê Eventos API - Equipe Tokyo")
                        .version("1.0.0")
                        .description("Sistema de gerenciamento de eventos."));
    }
}