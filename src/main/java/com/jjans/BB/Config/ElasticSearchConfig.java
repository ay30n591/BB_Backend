package com.jjans.BB.Config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.jjans.BB.Repository") // Elasticsearch repository 패키지 설정
public class ElasticSearchConfig {
    @Value("${elasticHostname}")
    private String hostname;
    @Bean(destroyMethod = "close")
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(hostname, 9200, "http") // Elasticsearch 호스트 및 포트 설정
                )
        );
    }
}
