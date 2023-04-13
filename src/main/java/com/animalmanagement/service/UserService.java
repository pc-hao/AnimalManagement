package com.animalmanagement.service;

import com.animalmanagement.bean.bo.AdminGetUserBo;
import com.animalmanagement.bean.bo.ChangeUserStatusBo;
import com.animalmanagement.bean.bo.ModifyUserInfoBo;
import com.animalmanagement.bean.bo.RegisterBo;
import com.animalmanagement.entity.*;
import com.animalmanagement.enums.RoleEnum;
import com.animalmanagement.example.RoleUserExample;
import com.animalmanagement.example.SysUserExample;
import com.animalmanagement.example.UserInfoExample;
import com.animalmanagement.example.VerificationExample;
import com.animalmanagement.mapper.*;
import com.animalmanagement.utils.EncodeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
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
    VerificationMapper verificationMapper;

    @Autowired
    EncodeUtil encodeUtil;

    @Autowired
    MailService mailService;

    private static final Random VeriNumGenerator = new Random();

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
                .password(encodeUtil.encodePassword(registerBo.getPassword()))
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
        userInfo.setBlacked(false);
        userInfoMapper.insertSelective(userInfo);
    }

    public void modifyUserInfo(ModifyUserInfoBo modifyUserInfoBo) {
        checkUserId(modifyUserInfoBo.getUserId());

        SysUser sysUser = sysUserMapper.selectByPrimaryKey(modifyUserInfoBo.getUserId());
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(modifyUserInfoBo.getUserId());

        if (!modifyUserInfoBo.getUsername().isEmpty()) {
            checkUsername(modifyUserInfoBo.getUsername());
            sysUser.setUsername(modifyUserInfoBo.getUsername());
        }
        if (!modifyUserInfoBo.getPassword().isEmpty()) {
            checkPassword(modifyUserInfoBo.getPassword(), modifyUserInfoBo.getPasswordConfirm());
            sysUser.setPassword(encodeUtil.encodePassword(modifyUserInfoBo.getPassword()));
        }
        if (!modifyUserInfoBo.getPhone().isEmpty()) {
            checkPhone(modifyUserInfoBo.getPhone());
            userInfo.setPhone(modifyUserInfoBo.getPhone());
        }
        if (!modifyUserInfoBo.getBio().isEmpty()) {
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

    // -------------------------
    // 以下为与邮箱相关的部分
    public void sendResetPasswordVeriEmail(String email) {
        UserInfoExample userInfoExample = new UserInfoExample();
        userInfoExample.createCriteria().andEmailEqualTo(email);
        long count = userInfoMapper.countByExample(userInfoExample);
        if (count == 0) {
            throw new RuntimeException("Email Does Not Exist");
        } else if (count == 1) {
            // 将verification的信息写入数据库中，同时发送邮件
            String veri = genVerification();
            Verification newVeri = Verification.builder().email(email).veriCode(veri).build();
            VerificationExample veriExample = new VerificationExample();
            veriExample.createCriteria().andEmailEqualTo(email);
            long veriCountInDB = verificationMapper.countByExample(veriExample);
            if (veriCountInDB > 0) {
                System.out.println("原先就存在这个邮件的验证码");
                verificationMapper.updateByPrimaryKeySelective(newVeri);
            } else {
                System.out.println("原先不存在这个邮件的验证码");
                verificationMapper.insertSelective(newVeri);
            }
            mailService.sendSimpleMail(MailService.SENDER_MAIL, email, null, MailService.SUBJECT, "您的验证码为：" + veri);
        }
    }

    public void sendRegisterVeriEmail(String email) {
        // 检查格式
        checkEmail(email);
        // 检查邮箱是否已经使用过了
        UserInfoExample userInfoExample = new UserInfoExample();
        userInfoExample.createCriteria().andEmailEqualTo(email);
        long count = userInfoMapper.countByExample(userInfoExample);
        if (count != 0) {
            // 邮箱已经使用了
            throw new RuntimeException("Email Already Exist");
        } else {
            // 将verification的信息写入数据库中，同时发送邮件
            String veri = genVerification();
            Verification newVeri = Verification.builder().email(email).veriCode(veri).build();
            VerificationExample veriExample = new VerificationExample();
            veriExample.createCriteria().andEmailEqualTo(email);
            long veriCountInDB = verificationMapper.countByExample(veriExample);
            if (veriCountInDB > 0) {
                verificationMapper.updateByPrimaryKeySelective(newVeri);
            } else {
                verificationMapper.insertSelective(newVeri);
            }
            mailService.sendSimpleMail(MailService.SENDER_MAIL, email, null, MailService.SUBJECT, "您的验证码为：" + veri);
        }
    }


    private String genVerification() {
        // 生成一个四位的验证码，以String的形式返回
        int veriNum = VeriNumGenerator.nextInt(10000);
        if (veriNum < 1000) {
            // 保证有四位
            veriNum += 1000;
        }
        return Integer.toString(veriNum);
    }

    public void checkUserId(Integer userId) {
        SysUserExample example = new SysUserExample();
        example.createCriteria().andIdEqualTo(userId);
        if (!Objects.nonNull(sysUserMapper.selectOneByExample(example))) {
            throw new RuntimeException("UserId Does Not Exist");
        }
    }

    public Map<String, Object> adminGetUsers(AdminGetUserBo adminGetUserBo) {
        List<UserInfo> userList = userInfoMapper.selectByExample(new UserInfoExample());

        //只查找已拉黑的用户
        if (adminGetUserBo.getIsBlack()) {
            userList = userList.stream().filter(UserInfo::getBlacked).collect(Collectors.toList());
        }

        //搜索关键字
        if (Objects.nonNull(adminGetUserBo.getContext())) {
            userList = userList.stream()
                    .filter(e ->
                            e.getUsername().contains(adminGetUserBo.getContext()))
                    .collect(Collectors.toList());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("sumNum", userList.size());

        userList.sort(Comparator.comparingInt(UserInfo::getId));
        int start = (adminGetUserBo.getPage() - 1) * adminGetUserBo.getPageNum();
        if (start >= userList.size()) {
            map.put("users", null);
        } else {
            int end = Math.min(start + adminGetUserBo.getPageNum(), userList.size());
            map.put("users", userList.subList(start, end));
        }
        return map;
    }

    public void prohibitUser(SysUser sysUser) {
        sysUser.setStatus("PROHIBIT");
        sysUserMapper.updateByPrimaryKeySelective(sysUser);

        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(sysUser.getId());
        userInfo.setBlacked(true);
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    public void unProhibitUser(SysUser sysUser) {
        sysUser.setStatus("NORMAL");
        sysUserMapper.updateByPrimaryKeySelective(sysUser);

        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(sysUser.getId());
        userInfo.setBlacked(false);
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    public SysUser getUserById(Integer userId) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        if (Objects.isNull(sysUser)) {
            throw new RuntimeException("User Id Does Not Exist");
        }
        return sysUser;
    }

    public UserInfo getUserInfoById(Integer id) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(id);
        if (Objects.isNull(userInfo)) {
            throw new RuntimeException("User Id Does Not Exist");
        }
        return userInfo;
    }

    public void changeUserStatus(ChangeUserStatusBo changeUserStatusBo) {
        SysUser sysUser = getUserById(changeUserStatusBo.getUserId());
        checkUserIsAdmin(changeUserStatusBo.getUserId());
        if (changeUserStatusBo.getOperation() == 1) {
            prohibitUser(sysUser);
        } else {
            unProhibitUser(sysUser);
        }
    }

    public void checkUserIsAdmin(Integer userId) {
        RoleUserExample example = new RoleUserExample();
        example.createCriteria().andUserIdEqualTo(userId);
        RoleUser roleUser = roleUserMapper.selectOneByExample(example);
        if (Objects.isNull(roleUser)) {
            throw new RuntimeException("User ROLE Does Not Exist");
        }
        if (Objects.equals(roleUser.getRoleId(), RoleEnum.ADMIN.getCode())) {
            throw new RuntimeException("CANNOT CHANGE ADMIN STATUS");
        }
    }

    // public void modifyUser(UserInfo newUserInfo) {
    //     SysUser sysUser = getUserById(newUserInfo.getId());
    //     checkNewUsername(newUserInfo.getUsername(), sysUser);
    //     checkPhone(newUserInfo.getPhone());
    //     UserInfo oldUserInfo = userInfoMapper.selectByPrimaryKey(newUserInfo.getId());
    //     if (!Objects.equals(newUserInfo.getEmail(), oldUserInfo.getEmail())) {
    //         throw new RuntimeException("EMAIL CANNOT CHANGE");
    //     }
    //     if (!Objects.equals(newUserInfo.getBlacked(), oldUserInfo.getBlacked())) {
    //         throw new RuntimeException("STATUS CANNOT CHANGE");
    //     }
    //     userInfoMapper.updateByPrimaryKeySelective(newUserInfo);
    // }

    private void checkNewUsername(String username, SysUser sysUser) {
        if (Objects.isNull(username)) {
            throw new RuntimeException("Username Is Empty");
        }
        if (username.length() > 20) {
            throw new RuntimeException("Username Is Too Long");
        }
        SysUserExample example = new SysUserExample();
        example.createCriteria().andUsernameEqualTo(username);

        SysUser another = sysUserMapper.selectOneByExample(example);
        if (!Objects.equals(sysUser.getId(), another.getId())) {
            throw new RuntimeException("Username Already Exists");
        }
    }
}
