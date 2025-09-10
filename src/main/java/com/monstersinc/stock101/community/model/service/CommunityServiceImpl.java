package com.monstersinc.stock101.community.model.service;

import com.monstersinc.stock101.community.model.dto.PostRequestDto;
import com.monstersinc.stock101.community.model.dto.PostResponseDto;
import com.monstersinc.stock101.community.model.mapper.CommunityMapper;
import com.monstersinc.stock101.community.model.vo.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
    private final CommunityMapper communityMapper;

    @Override
    @Transactional
    public int save(PostRequestDto dto) {
        Post post = dto.toPost();
        communityMapper.insertPost(post);
        return post.getPostId();
    }

    @Override
    public PostResponseDto getAPost(int postId) {
        Post post = communityMapper.selectPostById(postId);
        if (post == null) {
            throw new IllegalArgumentException("Post not found: " + postId);
        }
        return PostResponseDto.of(post);
    }

    @Override
    public PostResponseDto getPostDetail(int postId) {
        Post post = communityMapper.selectPostById(postId);
        if (post == null) {
            throw new IllegalArgumentException("Post not found: " + postId);
        }
        return PostResponseDto.of(post);
    }
}
