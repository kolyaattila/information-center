package com.information.center.authservice.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Getter
@Setter
@Table(name = "role")
public class RoleEntity implements GrantedAuthority {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, unique = true, name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @ManyToMany(mappedBy = "roles")
  private List<UserEntity> users = new ArrayList<>();

  @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
  @JoinTable(
      name = "role_permission",
      joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
  private List<PermissionEntity> permissions = new ArrayList<>();

  @Override
  public String getAuthority() {
    return name;
  }
}
