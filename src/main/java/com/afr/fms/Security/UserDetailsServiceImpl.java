package com.afr.fms.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Admin.Mapper.RoleMapper;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserMapper userMapper;

  @Autowired
  RoleMapper userRoleMapper;

  User user;

  String username;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      this.username = username;
      user = userMapper.findByUsername(username);
      user.setUsername(user.getEmail());
    
      return UserDetailsImpl.build(user);
    } catch (Exception e) {
      System.out.println(e);
      return null;
    }
  }

  public User getUser() {
    return user;
  }

  public String getUsername()
  {
    return username;
  }
}
