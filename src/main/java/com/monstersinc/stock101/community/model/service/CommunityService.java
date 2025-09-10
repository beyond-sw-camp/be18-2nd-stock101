package com.monstersinc.stock101.community.model.service;

import com.monstersinc.stock101.community.model.dto.PostRequestDto;
import com.monstersinc.stock101.community.model.dto.PostResponseDto;

public interface CommunityService {
    int save(PostRequestDto dto);                         // 생성
    PostResponseDto getAPost(int postId);             // 생성 직후/목록용
}
