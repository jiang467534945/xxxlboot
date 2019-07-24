package com.xxxlboot.auth.vo;


import com.xxxlboot.common.constats.CommonConstant;
import com.xxxlboot.common.vo.AdminRole;
import com.xxxlboot.common.vo.UserVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @auther: Easy
 * @Date: 18-9-21 22:33
 * @Description:
 */
public class UserDetailsVo implements UserDetails {
    private static final long serialVersionUID = 1L;
    private String userId;
    private String username;
    private String password;
    private String status;
    private List<AdminRole> roleList;

    public UserDetailsVo(UserVO userVo) {
        this.userId = userVo.getId();
        this.username = userVo.getUserName();
        this.password = userVo.getPassword();
        this.status = userVo.getDelFlag();
        roleList = userVo.getRoleList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        System.out.println(roleList.size());
        if(!roleList.isEmpty()){
            for (AdminRole role : roleList) {
                authorityList.add(new SimpleGrantedAuthority(role.getRoleCode()));
            }
        }

        authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorityList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }


    public String getUserId() {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<AdminRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<AdminRole> roleList) {
        this.roleList = roleList;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !StringUtils.equals(CommonConstant.STATUS_LOCK, status);
    }
    @Override

    public boolean isEnabled() {
        return StringUtils.equals(CommonConstant.STATUS_NORMAL, status);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
