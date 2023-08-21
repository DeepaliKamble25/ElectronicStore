package com.electronic.store.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@ToString
@Builder
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
   private String roleId;
   private String roleName;



}
