package task.aisa.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

@OpenAPIDefinition(
        info = @Info(
                title = "Car Service API",
                version = "1.0.0"
        )
)
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        name = "basicAuth",
        scheme = "basic")
@Configuration
public class OpenAPIConfiguration {

        @Bean
        public OperationCustomizer operationCustomizer() {
                return (operation, handlerMethod) -> {
                        Optional<PreAuthorize> preAuthorizeAnnotation = Optional.ofNullable(handlerMethod.getMethodAnnotation(PreAuthorize.class));
                        preAuthorizeAnnotation.ifPresent(
                                preAuthorize -> {
                                        if (preAuthorize.value().startsWith("hasAuthority")) {
                                                String stringBuilder = "This api requires **" +
                                                        (preAuthorize).value().replaceAll("hasAuthority|\\(|\\)|'", "") +
                                                        "** permission." +
                                                        "<br /><br />" +
                                                        operation.getDescription();
                                                operation.setDescription(stringBuilder);
                                        }
                                });

                        return operation;
                };
        }
}
