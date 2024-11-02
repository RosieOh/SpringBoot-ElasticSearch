package com.elasticsearchtest.domain.repository;

import com.elasticsearchtest.domain.model.Item;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ItemRepository extends ElasticsearchRepository<Item, String>{
    List<Item> findByName(String keyword);
    List<Item> findByPriceBetween(int minPrice, int maxPrice);

    // ElasticSearch 커스텀 쿼리
    @Query("{\"match\": {\"description\": \"?0\"}}")
    List<Item> searchByDescription(String description);
}
