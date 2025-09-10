package com.monstersinc.stock101.community.model.service;

import com.monstersinc.stock101.community.model.dto.PostRequestDto;
import com.monstersinc.stock101.community.model.dto.PostResponseDto;

import java.util.List;

public interface CommunityService {
    int save(PostRequestDto dto);                         // 생성

    PostResponseDto getAPost(int postId);             // 생성 직후/목록용

    PostResponseDto getPostDetail(int postId);

    List<PostResponseDto> getPostListByStock(int stockId);
}
