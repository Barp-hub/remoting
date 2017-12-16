package io.github.riwcwt.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.github.riwcwt.entity.AuthUser;
import io.github.riwcwt.entity.Role;
import io.github.riwcwt.mapper.RoleMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

    @Autowired
    private UserService userService = null;

    @Autowired
    private RoleMapper roleMapper = null;

    @Test
    public void addUser() {
        AuthUser user = new AuthUser();
        user.setUsername("zhujun");
        this.userService.addUser(user);

        logger.info("ID : " + user.getId());

    }

    @Test
    public void role() {
        PageHelper.startPage(1, 10);

        List<Role> list = this.roleMapper.getRoles();

        PageInfo<Role> page = new PageInfo<>(list);

        logger.info(JSON.toJSONString(page, true));

        logger.info(JSON.toJSONString(PageHelper.startPage(1, 10).doSelectPageInfo(() -> this.roleMapper.getRoles()), true));
    }

}
