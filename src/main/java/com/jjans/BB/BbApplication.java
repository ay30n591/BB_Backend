package com.jjans.BB;

import com.jjans.BB.Repository.SearchFeedRepository;
//import com.jjans.BB.Repository	.SearchUsersRepository;
import com.jjans.BB.Repository.SearchPlaylistRepository;
import com.jjans.BB.Repository.SearchTotalRepository;
import com.jjans.BB.Repository.SearchUsersRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;

@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
		type = FilterType.ASSIGNABLE_TYPE,
		classes = { SearchFeedRepository.class, SearchUsersRepository.class, SearchPlaylistRepository.class, SearchTotalRepository.class}
		))
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
