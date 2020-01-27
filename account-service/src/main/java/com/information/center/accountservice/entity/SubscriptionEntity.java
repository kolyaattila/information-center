package com.information.center.accountservice.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "Subscription")
@Getter
@Setter
public class SubscriptionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "created", nullable = false)
  private Date created;

  @Column(name = "unsubscription", nullable = false)
  private boolean unsubscription;

  @Column(name = "email_validation", nullable = false)
  private boolean emailValidation;

  @Column(name = "uid", nullable = false, unique = true)
  private String uid;
}

