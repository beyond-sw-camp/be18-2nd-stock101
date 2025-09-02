package com.monstersinc.stock101.community.controller;

import com.monstersinc.stock101.community.model.service.CommunityService;
import com.monstersinc.stock101.user.model.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user-service")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;
}
