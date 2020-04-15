package spring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.integration.config.EnableIntegration


@EnableIntegration
@SpringBootApplication
class Application

/**
 * https://docs.spring.io/spring-integration/reference/html/ip.html
 */
fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

