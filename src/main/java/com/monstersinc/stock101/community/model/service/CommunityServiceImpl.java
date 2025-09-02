package com.monstersinc.stock101.community.model.service;

import com.monstersinc.stock101.community.model.mapper.CommunityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
    private final CommunityMapper communityMapper;
}
