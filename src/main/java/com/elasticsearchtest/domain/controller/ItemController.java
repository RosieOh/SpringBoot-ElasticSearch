package com.elasticsearchtest.domain.controller;

import com.elasticsearchtest.domain.model.Item;
import com.elasticsearchtest.domain.service.ItemService;
import com.elasticsearchtest.global.common.ApiRequest;
import com.elasticsearchtest.global.common.ApiResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemController {

    private final ItemService is;

    // 이름 기반 검색
    @GetMapping
    @ApiOperation(value = "이름으로 아이템 검색", notes = "주어진 키워드에 맞는 아이템을 검색합니다.")
    public ApiResponse<List<Item>> search(@RequestParam("keyword") String keyword) {
        return ApiResponse.onSuccess(is.getItemByName(keyword));
    }

    // 설명 기반 검색
    @GetMapping("/search/description")
    @ApiOperation(value = "설명으로 아이템 검색", notes = "주어진 설명에 맞는 아이템을 검색합니다.")
    public ApiResponse<List<Item>> searchByDescription(@RequestParam("description") String description) {
        return ApiResponse.onSuccess(is.searchByDescription(description));
    }

    // 모든 아이템 조회
    @GetMapping
    @ApiOperation(value = "모든 아이템 조회", notes = "등록된 모든 아이템을 조회합니다.")
    public ApiResponse<List<Item>> getAllItems() {
        return ApiResponse.onSuccess(is.getAllItems());
    }

    @PostMapping
    @ApiOperation(value = "아이템 생성", notes = "새로운 아이템을 등록합니다.")
    public ApiResponse<Item> create(@RequestBody ApiRequest<Item> request) {
        if (!request.isValid()) {
            return ApiResponse.onFailure("상품을 등록할 수 없습니다.");
        }
        return ApiResponse.onSuccess(is.createItem(request.getData()));
    }

    // 업데이트
    @PutMapping("/{id}")
    @ApiOperation(value = "아이템 업데이트", notes = "주어진 ID에 해당하는 아이템을 업데이트합니다.")
    public ApiResponse<Item> update(@PathVariable String id, @RequestBody Item updatedItem) {
        return ApiResponse.onSuccess(is.updateItem(id, updatedItem));
    }

    // 삭제
    @DeleteMapping("/{id}")
    @ApiOperation(value = "아이템 삭제", notes = "주어진 ID에 해당하는 아이템을 삭제합니다.")
    public ApiResponse<Void> delete(@PathVariable String id) {
        is.deleteItem(id);
        return ApiResponse.onSuccess(null);
    }

    // 가격 범위로 아이템 조회
    @GetMapping("/search/price")
    @ApiOperation(value = "가격 범위로 아이템 조회", notes = "주어진 가격 범위에 해당하는 아이템을 검색합니다.")
    public ApiResponse<List<Item>> getItemsByPriceRange(@RequestParam int minPrice, @RequestParam int maxPrice) {
        return ApiResponse.onSuccess(is.getItemByPriceRange(minPrice, maxPrice));
    }
}
