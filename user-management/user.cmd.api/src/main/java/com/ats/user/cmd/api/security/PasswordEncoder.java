package com.ats.user.cmd.api.security;

public interface PasswordEncoder {
    String hashPassword(String password);
}
