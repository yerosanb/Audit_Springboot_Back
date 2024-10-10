package com.afr.fms.Security;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.afr.fms.Admin.Entity.Branch;
import com.afr.fms.Admin.Entity.Region;
import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Security.UserSecurity.entity.UserSecurity;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Long id;

  private String email;

  private boolean status;

  private String photoUrl;

  private String category;

  private Branch branch;

  private Region region;

  private String ing;

  



  private UserSecurity userSecurity;

  @JsonIgnore
  private String password;
  private Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(Long id, String username, String email, String password,
      boolean status, String photoUrl, String category, Branch branch, Region region, String ing, UserSecurity userSecurity,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.status = status;
    this.photoUrl = photoUrl;
    this.category = category;
    this.branch = branch;
    this.region = region;
    this.userSecurity = userSecurity;
    this.ing = ing;
    this.authorities = authorities;
  }

  public static UserDetailsImpl build(User user) {

    List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());

    return new UserDetailsImpl(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getPassword(),
        user.isStatus(),
        user.getPhotoUrl(),
        user.getCategory(),
        user.getBranch(),
        user.getRegion(),
        user.geting(),
        user.getUser_security(),
        authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public String geting() {
    return ing;
  }

  public void seting(String ing) {
    this.ing = ing;
  }

  public Long getId() {
    return id;
  }

  public String getPhotoUrl() {
    return photoUrl;
  }

  public String getCategory() {
    return category;
  }

  public String getEmail() {
    return email;
  }

  public Branch getBranch() {
    return branch;
  }

  public void setBranch(Branch branch) {
    this.branch = branch;
  }

  public Region getRegion() {
    return region;
  }

  public void setRegion(Region region) {
    this.region = region;
  }

  public UserSecurity getUserSecurity() {
    return userSecurity;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return userSecurity.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return userSecurity.isAccountNonLocked();
    // return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return userSecurity.isCredentialsNonExpired();
    // return true;
  }

  @Override
  public boolean isEnabled() {
    return status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }

}
