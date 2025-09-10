package com.monstersinc.stock101.community.controller;

import com.monstersinc.stock101.common.model.dto.BaseResponseDto;
import com.monstersinc.stock101.common.model.dto.ItemsResponseDto;
import com.monstersinc.stock101.community.model.dto.PostRequestDto;
import com.monstersinc.stock101.community.model.dto.PostResponseDto;
import com.monstersinc.stock101.community.model.service.CommunityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class CommunityController {
    private final CommunityService communityService;

    // 게시물 등록
    @PostMapping("/posts")
    public ResponseEntity<BaseResponseDto<PostResponseDto>> create(
            @Valid @RequestBody PostRequestDto requestDto) {

        int newId = communityService.save(requestDto);
        PostResponseDto body = communityService.getAPost(newId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponseDto<>(HttpStatus.CREATED, body));
    }

    // 게시물 상세 조회
    @GetMapping("/posts/{postId}")
    public ResponseEntity<BaseResponseDto<PostResponseDto>> detail(@PathVariable int postId) {
        PostResponseDto dto = communityService.getPostDetail(postId);
        return ResponseEntity.ok(new BaseResponseDto<>(HttpStatus.OK, dto));
    }

    // 종목 별 게시물 리스트 조회
    @GetMapping("/posts")
    public ResponseEntity<ItemsResponseDto<PostResponseDto>> list(
            @RequestParam int stockId) {

        List<PostResponseDto> items = communityService.getPostListByStock(stockId);
        return ResponseEntity.ok(ItemsResponseDto.ofAll(HttpStatus.OK, items));
    }
}
