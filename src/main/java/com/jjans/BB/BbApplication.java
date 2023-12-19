package com.jjans.BB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication( exclude = {
		org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration.class,
		org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration.class,
		org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration.class
})
@EnableJpaAuditing
@CrossOrigin(origins = "http://localhost:3000") // CORS 설정

public class BbApplication {

	public static void main(String[] args) {
		SpringApplication.run(BbApplication.class, args);
	}

}
