package com.ats.user.cmd.api.commands;

import com.ats.user.core.models.User;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.Valid;

@Data
@Builder
public class RegisterUserCommand {
    @TargetAggregateIdentifier
    private String id;
    @Valid
    private User user;
}
