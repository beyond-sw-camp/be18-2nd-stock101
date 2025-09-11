package com.monstersinc.stock101.user.model.service;

import com.monstersinc.stock101.user.model.dto.UserRegisterRequestDto;
import com.monstersinc.stock101.user.model.vo.User;

public interface UserService {
    User registerUser(UserRegisterRequestDto userRegisterRequestDto);
}
