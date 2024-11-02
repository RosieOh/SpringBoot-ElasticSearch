package com.elasticsearchtest.global.elasticsearch.indicator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
REST API를 통해 수동으로 요청 보내기 - GPT 왈
파일의 내용을 직접 읽어 인덱스를 생성하는 대신, REST API 클라이언트를 사용해 수동으로 요청 가능

curl 사용 예시
curl -X PUT "http://localhost:9200/your_index_name" -H 'Content-Type: application/json' -d @resources/static/elastic-mapping.json
이렇게 하면 elastic-mapping.json 파일의 내용으로 인덱스가 생성됩니다.

3. 인덱스 생성 후 확인
인덱스가 제대로 생성되었는지 확인하려면 다음과 같은 요청을 보내어 인덱스의 매핑을 조회할 수 있습니다.
curl -X GET "http://localhost:9200/your_index_name/_mapping"
 */
@Component
public class ElasticIndexCreator implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ElasticIndexCreator.class);

    // 분석기 설정
    @Value("classpath:static/elastic-mapping.json")
    private Resource resource;

    private final RestTemplate restTemplate;

    public ElasticIndexCreator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        String jsonMapping = readMappingFromFile(resource);
        String indexName = "test_Index";
        String url = "http://localhost:9200/" + indexName;

        try {
            // PUT 요청을 보내 인덱스 생성
            restTemplate.put(url, jsonMapping);
            logger.info("인덱스 생성 완료: {}", indexName);  // 로그 떨어뜨리기
        } catch (Exception e) {
            logger.error("인덱스 생성 중 오류 발생: {}", e.getMessage(), e);  // 오류 로그 떨어뜨리기
        }

    }

    private String readMappingFromFile(Resource resource) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }
        return content.toString();
    }
}
