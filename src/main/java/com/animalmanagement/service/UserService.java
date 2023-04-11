package com.animalmanagement.service;

import com.animalmanagement.bean.BaseResponse;
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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.prefs.BackingStoreException;
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
    public void trySendEmail(String email) {
        UserInfoExample userInfoExample = new UserInfoExample();
        userInfoExample.createCriteria().andEmailEqualTo(email);
        long count = userInfoMapper.countByExample(userInfoExample);
        if (count == 0) {
            throw new RuntimeException("Email Does Not Exist");
        } else if (count == 1) {
            System.out.printf("sdadasdasdasda");
            // 将verification的信息写入数据库中，同时发送邮件
            String veri = genVerification();
            System.out.println("veri: " + veri);
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
            // todo 可能引入bug，需要没有时insert，有时update

            //verificationMapper.updateByExample()
            mailService.sendSimpleMail(MailService.SENDER_MAIL, email, null, MailService.SUBJECT, "您的验证码为：" + veri);
        }
    }

    private String genVerification() {
        // 生成一个四位的验证码，以String的形式返回
        int veriNum = VeriNumGenerator.nextInt(10000);
        if (veriNum < 1000) {
            // 保证有四位
            veriNum+=1000;
        }
        return Integer.toString(veriNum);
    }
}
