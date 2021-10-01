package com.ats.user.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @JsonIgnore
    private String id;
    @Size(min = 2, message = "username must have a minimum of 2 characters")
    private String userName;
    @Size(min = 8, message = "password must have a minimum of 8 characters")
    @JsonIgnore
    private String password;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "accounts_roles",
            joinColumns = @JoinColumn(name = "account_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    @NotNull(message = "specify at least 1 user role")
    private Set<Role> roles;
}
