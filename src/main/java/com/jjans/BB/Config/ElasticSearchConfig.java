package com.jjans.BB.Config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.jjans.BB.Repository") // Elasticsearch repository 패키지 설정
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {
//    @Value("${elasticHostname}")
//    private String hostname;
//    @Bean(destroyMethod = "close")
//    public RestHighLevelClient restHighLevelClient() {
//        return new RestHighLevelClient(
//                RestClient.builder(
//                        new HttpHost(hostname, 9200, "http") // Elasticsearch 호스트 및 포트 설정
//                )
//        );
//    }
    //localhost:9200에 떠있는 ES와 연결
        @Override
        public RestHighLevelClient elasticsearchClient() {
            ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                    .connectedTo("localhost:9200")
                    .build();
            return RestClients.create(clientConfiguration).rest();
        }
}
