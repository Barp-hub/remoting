package io.github.riwcwt.mapper;

import io.github.riwcwt.entity.Role;
import sun.plugin.javascript.navig.LinkArray;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    List<Role> getRoles();

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}