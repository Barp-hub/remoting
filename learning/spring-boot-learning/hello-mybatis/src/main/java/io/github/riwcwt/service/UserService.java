package io.github.riwcwt.service;

import io.github.riwcwt.entity.AuthUser;
import io.github.riwcwt.mapper.AuthUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private AuthUserMapper userMapper = null;

    @Transactional(value = "console-transactionManager")
    public AuthUser addUser(AuthUser user) {
        this.userMapper.insertSelective(user);
        return user;
    }

}
