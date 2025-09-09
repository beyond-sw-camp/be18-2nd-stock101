package com.monstersinc.stock101.community.controller;

import com.monstersinc.stock101.common.exception.PostException;
import com.monstersinc.stock101.common.exception.message.ExceptionMessage;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class CommunityController {
    private final CommunityService communityService;

    @PostMapping("/posts")
    public ResponseEntity<BaseResponseDto<PostResponseDto>> create(
            @Valid @RequestBody PostRequestDto requestDto) {

        int newId = communityService.save(requestDto);
        PostResponseDto body = communityService.getAPost(newId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponseDto<>(HttpStatus.CREATED, body));
    }

    @GetMapping("/posts")
    public ResponseEntity<ItemsResponseDto<PostResponseDto>> list(
            @RequestParam int stockId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {

        List<PostResponseDto> items = communityService.getPostListByStock(stockId, page, size);
        long total = communityService.countByStock(stockId);

        if (!items.isEmpty()) {
            return ResponseEntity.ok(
                    new ItemsResponseDto<>(HttpStatus.OK, items, page, (int) total)
            );
        } else {
            throw new PostException(ExceptionMessage.POST_NOT_FOUND);
        }
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<BaseResponseDto<PostResponseDto>> detail(@PathVariable int postId) {
        PostResponseDto dto = communityService.getPostDetail(postId);
        return ResponseEntity.ok(new BaseResponseDto<>(HttpStatus.OK, dto));
    }
}
