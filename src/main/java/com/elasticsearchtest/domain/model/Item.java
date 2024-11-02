package com.elasticsearchtest.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
@Document(indexName = "item")
@Mapping(mappingPath = "static/elastic-mapping.json")
@Setting(settingPath = "static/token/elastic-token.json")
public class Item {
    @Id
    @Field(name = "id", type = FieldType.Keyword)
    private String itemId;

    @Field(type = FieldType.Keyword)
    private String user;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Text)
    private String category;

    @Field(type = FieldType.Integer)
    private int price;

    @Field(type = FieldType.Text)
    private String itemImage;

    // 날짜 추가
    @Field(type = FieldType.Date)
    private LocalDateTime createdDate;

    @Field(type = FieldType.Date)
    private LocalDateTime updatedDate;

    public void update(Item updatedItem) {
        this.name = updatedItem.getName();
        this.description = updatedItem.getDescription();
        this.category = updatedItem.getCategory();
        this.price = updatedItem.getPrice();
        this.itemImage = updatedItem.getItemImage();
        this.updatedDate = LocalDateTime.now();
    }



}