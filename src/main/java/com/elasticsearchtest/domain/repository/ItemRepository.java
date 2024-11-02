package com.elasticsearchtest.domain.repository;

import com.elasticsearchtest.domain.model.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ItemRepository extends ElasticsearchRepository<Item, String>{
}
