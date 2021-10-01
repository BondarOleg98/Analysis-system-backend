package com.ats.user.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    private String id;
    @NotEmpty(message = "firstname is mandatory")
    private String firstName;
    @NotEmpty(message = "lastname is mandatory")
    private String lastName;
    @Email(message = "please provide a valid email address")
    private String emailAddress;
    @Valid
    @NotNull(message = "please provide account credentials")
    @OneToOne(cascade = {CascadeType.ALL})
    private Account account;
}
