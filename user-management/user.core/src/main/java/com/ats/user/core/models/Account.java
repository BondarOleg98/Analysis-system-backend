package com.ats.user.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    private String id;
    @Size(min = 2, message = "username must have a minimum of 2 characters")
    private String userName;
    @Size(min = 8, message = "password must have a minimum of 8 characters")
    private String password;
    @ManyToOne
    @NotNull(message = "specify at least 1 user role")
    private Role roles;
}