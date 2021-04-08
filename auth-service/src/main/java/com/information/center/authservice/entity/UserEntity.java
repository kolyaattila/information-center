package com.information.center.authservice.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "user",schema = "public")
public class UserEntity implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
  @SequenceGenerator(name = "user_generator", sequenceName = "S_USER_0", allocationSize = 1)
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
  private List<RoleEntity> roles = new ArrayList<>();


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
