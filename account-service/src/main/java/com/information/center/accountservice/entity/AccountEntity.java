package com.information.center.accountservice.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Account")
public class AccountEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_generator")
  @SequenceGenerator(name = "account_generator", sequenceName = "S_ACCOUNT_0", allocationSize = 1)
  private long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "username")
  private String username;

  @Column(name = "birthday")
  private Date birthday;

  @Column(name = "created")
  private Date created;

  @Column(name = "photo")
  private byte[] photo;

  @Column(name = "uid")
  private String uid;
}
