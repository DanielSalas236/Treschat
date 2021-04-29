package com.uniajc.treschat.config

import com.uniajc.treschat.TreschatApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@Configuration
@EnableSwagger2
class Swagger2Config {
    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(
                RequestHandlerSelectors
                    .basePackage(TreschatApplication::class.java.getPackage().name + ".controllers")
            )
            .paths(PathSelectors.regex("/.*"))
            .build().apiInfo(apiInfo())
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder().title("Tresox Rest").description("Proyecto base para los servicios Rest")
            .termsOfServiceUrl("http://tresox.com/").license("MIT License")
            .contact(
                Contact("Tresox", "http://tresox.com/", "tresox@gmail.com")
            )
            .version("1.0").build()
    }
}