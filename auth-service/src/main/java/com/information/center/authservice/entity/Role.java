package com.information.center.authservice.entity;

import java.util.ArrayList;
import java.util.List;
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

@Entity
@Getter
@Setter
@Table(name = "role")
public class Role implements GrantedAuthority {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, unique = true, name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @ManyToMany(mappedBy = "roles")
  private List<User> users = new ArrayList<>();

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "role_permission",
      joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
  private List<Permission> permissions = new ArrayList<>();

  @Override
  public String getAuthority() {
    return name;
  }
}
