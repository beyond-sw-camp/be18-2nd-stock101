package com.monstersinc.stock101.community.controller;

import com.monstersinc.stock101.common.model.dto.BaseResponseDto;
import com.monstersinc.stock101.community.model.dto.PostRequestDto;
import com.monstersinc.stock101.community.model.dto.PostResponseDto;
import com.monstersinc.stock101.community.model.service.CommunityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
}
