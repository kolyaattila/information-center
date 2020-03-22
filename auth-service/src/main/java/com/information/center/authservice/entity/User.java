package com.information.center.authservice.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@Setter
@Table(name = "user",schema = "public")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, name = "username", unique = true)
  private String username;

  @Column(nullable = false, name = "password", unique = true)
  private String password;

  @Column(nullable = false, name = "created")
  private Date created;

  @Column(nullable = false, unique = false, name = "account_expired")
  private boolean accountExpired;

  @Column(nullable = false, unique = false, name = "account_locked")
  private boolean accountLocked;

  @Column(nullable = false, unique = false, name = "credentials_expired")
  private boolean credentialsExpired;

  @Column(nullable = false, unique = false, name = "enabled")
  private boolean enabled;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_role",
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private List<Role> roles = new ArrayList<>();


  @Override
  public List<GrantedAuthority> getAuthorities() {
    return roles.stream().map(role -> (GrantedAuthority) role).collect(Collectors.toList());
  }

  @Override
  public boolean isAccountNonExpired() {
    return !accountExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !accountLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return !credentialsExpired;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }
}
