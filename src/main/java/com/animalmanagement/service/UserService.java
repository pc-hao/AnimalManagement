package com.animalmanagement.service;

import com.animalmanagement.bean.bo.ModifyUserInfoBo;
import com.animalmanagement.bean.bo.RegisterBo;
import com.animalmanagement.entity.RoleUser;
import com.animalmanagement.entity.SysRole;
import com.animalmanagement.entity.SysUser;
import com.animalmanagement.entity.UserInfo;
import com.animalmanagement.enums.RoleEnum;
import com.animalmanagement.example.RoleUserExample;
import com.animalmanagement.example.SysUserExample;
import com.animalmanagement.example.UserInfoExample;
import com.animalmanagement.mapper.RoleUserMapper;
import com.animalmanagement.mapper.SysRoleMapper;
import com.animalmanagement.mapper.SysUserMapper;
import com.animalmanagement.mapper.UserInfoMapper;
import com.animalmanagement.utils.EncodeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired
    RoleUserMapper roleUserMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    EncodeUtil encodeUtil;

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
        RoleUserExample example = new RoleUserExample();
        example.createCriteria().andUserIdEqualTo(userId);
        return roleUserMapper.selectByExample(example)
                .stream()
                .map(e ->
                        sysRoleMapper.selectByPrimaryKey(e.getRoleId()))
                .collect(Collectors.toList());
    }

    public boolean changePasswordByEmail(String email, String newPassword) {
        UserInfoExample example = new UserInfoExample();
        example.createCriteria().andEmailEqualTo(email);
        UserInfo userInfo = userInfoMapper.selectOneByExample(example);
        if (Objects.isNull(userInfo)) {
            return false;
        }
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userInfo.getId());
        sysUser.setPassword(encodeUtil.encodePassword(newPassword));
        sysUserMapper.updateByPrimaryKeySelective(sysUser);
        return true;
    }

    public void register(RegisterBo registerBo) {
        checkUsername(registerBo.getUsername());
        checkPassword(registerBo.getPassword(), registerBo.getPasswordConfirm());
        checkEmail(registerBo.getEmail());
        checkPhone(registerBo.getPhone());

        SysUser insertUser = SysUser.builder()
                .username(registerBo.getUsername())
                .password(registerBo.getPassword())
                .status("NORMAL").build();
        sysUserMapper.insertSelective(insertUser);
        SysUserExample example = new SysUserExample();

        example.createCriteria().andUsernameEqualTo(insertUser.getUsername());
        SysUser sysUser = sysUserMapper.selectOneByExample(example);
        RoleUser roleUser = RoleUser.builder()
                .roleId(RoleEnum.USER.getCode())
                .userId(sysUser.getId()).build();
        roleUserMapper.insertSelective(roleUser);

        UserInfo userInfo = UserInfo.builder().id(sysUser.getId()).build();
        BeanUtils.copyProperties(registerBo, userInfo);
        userInfoMapper.insertSelective(userInfo);
    }

    public void modifyUserInfo(ModifyUserInfoBo modifyUserInfoBo) {
        checkUserId(Integer.parseInt(modifyUserInfoBo.getUserId()));

        SysUser sysUser = sysUserMapper.selectByPrimaryKey(Integer.parseInt(modifyUserInfoBo.getUserId()));
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(Integer.parseInt(modifyUserInfoBo.getUserId()));

        if(!modifyUserInfoBo.getUsername().isEmpty()) {
            checkUsername(modifyUserInfoBo.getUsername());
        }
        if(!modifyUserInfoBo.getPassword().isEmpty()) {
            checkPassword(modifyUserInfoBo.getPassword(), modifyUserInfoBo.getPasswordConfirm());
            sysUser.setPassword(encodeUtil.encodePassword(modifyUserInfoBo.getPassword()));
        }
        if(!modifyUserInfoBo.getPhone().isEmpty()) {
            checkPhone(modifyUserInfoBo.getPhone());
            userInfo.setPhone(modifyUserInfoBo.getPhone());
        }
        if(!modifyUserInfoBo.getBio().isEmpty()) {
            userInfo.setBio(modifyUserInfoBo.getBio());
        }
        
        sysUserMapper.updateByPrimaryKeySelective(sysUser);
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    public void checkUsername(String username) {
        if (Objects.isNull(username)) {
            throw new RuntimeException("Username Is Empty");
        }
        if (username.length() > 20) {
            throw new RuntimeException("Username Is Too Long");
        }
        SysUserExample example = new SysUserExample();
        example.createCriteria().andUsernameEqualTo(username);
        if (Objects.nonNull(sysUserMapper.selectOneByExample(example))) {
            throw new RuntimeException("Username Already Exists");
        }
    }

    public void checkPassword(String pw, String pwc) {
        if (Objects.isNull(pw)) {
            throw new RuntimeException("Password Is Empty");
        }
        if (!Objects.equals(pw, pwc)) {
            throw new RuntimeException("Password Is Not Consistent With Password Confirmation");
        }
        if (pw.length() < 6 || pw.length() > 18) {
            throw new RuntimeException("Password Length Not Between 6 and 18");
        }
    }

    public void checkEmail(String email) {
        if (Objects.isNull(email)) {
            throw new RuntimeException("Email Is Empty");
        }
        if (!email.endsWith("@buaa.edu.cn") || email.length() <= "@buaa.edu.cn".length()) {
            throw new RuntimeException("Incorrect Email Format");
        }
    }

    public void checkPhone(String phone) {
        if (Objects.isNull(phone)) {
            throw new RuntimeException("PhoneNumber Is Empty");
        }
        if (!phone.startsWith("1") ||
                phone.length() != 11 ||
                !phone.matches("[0-9]+")) {
            throw new RuntimeException("Incorrect Phone Number Format");
        }
    }

    public void checkUserId(Integer userId) {
        SysUserExample example = new SysUserExample();
        example.createCriteria().andIdEqualTo(userId);
        if (!Objects.nonNull(sysUserMapper.selectOneByExample(example))) {
            throw new RuntimeException("UserId Does Not Exist");
        }
    }
}
