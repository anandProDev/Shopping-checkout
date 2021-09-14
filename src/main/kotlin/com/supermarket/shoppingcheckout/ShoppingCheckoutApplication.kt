package com.supermarket.shoppingcheckout

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.swagger2.annotations.EnableSwagger2
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@SpringBootApplication
class ShoppingCheckoutApplication

fun main(args: Array<String>) {
	runApplication<ShoppingCheckoutApplication>(*args)
}

@Configuration
@EnableSwagger2
class SwaggerConfiguration {

	@Bean
	fun api(): Docket = Docket(DocumentationType.SWAGGER_2)
		.select()
		.apis( RequestHandlerSelectors.basePackage( "com.supermarket.shoppingcheckout" ) )
		.paths(PathSelectors.any())
		.build()
}
