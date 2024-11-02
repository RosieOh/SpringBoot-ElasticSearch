package com.elasticsearchtest.domain.service;

import com.elasticsearchtest.domain.model.Item;
import com.elasticsearchtest.domain.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository ir;

    public Item createItem(Item item) {
        return ir.save(item);
    }

    // update
    public Item updateItem(String id, Item updatedItem) {
        Item existingItem = ir.findById(id)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
        existingItem.update(updatedItem);
        return ir.save(existingItem);
    }

    public void deleteItem(String id) {
        if (!ir.existsById(id)) {
            throw new RuntimeException("상품을 찾을 수 없습니다.");
        }
        ir.deleteById(id);
    }

    // 이름으로 조회
    public List<Item> getItemByName(String keyword) {
        List<Item> searchByName = ir.findByName(keyword);
        return searchByName;
    }

    public List<Item> searchByDescription(String description) {
        return ir.searchByDescription(description);
    }

    // 모든 아이템 조회
    public List<Item> getAllItems() {
        return (List<Item>) ir.findAll();
    }

    // 모든 상품 조회
    public List<Item> getItemByPriceRange(int minPrice, int maxPrice) {
        return ir.findByPriceBetween(minPrice, maxPrice);
    }
}
