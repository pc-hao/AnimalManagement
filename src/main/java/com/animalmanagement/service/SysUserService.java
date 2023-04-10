package com.animalmanagement.service;

import com.animalmanagement.entity.SysRole;
import com.animalmanagement.entity.SysUser;
import com.animalmanagement.example.SysUserExample;
import com.animalmanagement.mapper.SysRoleMapper;
import com.animalmanagement.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserService {
    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysRoleMapper sysRoleMapper;

    /**
     * 根据用户名查询实体
     */
    public SysUser selectUserByName(String username) {
        SysUserExample example = new SysUserExample();
        example.createCriteria().andUsernameEqualTo(username);
        return sysUserMapper.selectOneByExample(example);
    }

    /**
     * 通过用户ID查询角色集合
     */
    public List<SysRole> selectSysRoleByUserId(Integer userId) {
        return sysRoleMapper.selectSysRoleByUserId(userId);
    }
}
