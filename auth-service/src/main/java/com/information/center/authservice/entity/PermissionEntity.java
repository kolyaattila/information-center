package com.information.center.authservice.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "permission")
public class PermissionEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_generator")
  @SequenceGenerator(name = "permission_generator", sequenceName = "S_PERMISSION_0", allocationSize = 1)
  private long id;

  @Column(nullable = false, unique = true, name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "created")
  private Date created;

  @ManyToMany(mappedBy = "permissions")
  private List<RoleEntity> roleEntities = new ArrayList<>();
}
