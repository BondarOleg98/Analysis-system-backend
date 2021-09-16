package com.ats.user.cmd.api.aggregates;

import com.ats.user.cmd.api.commands.RegisterUserCommand;
import com.ats.user.cmd.api.commands.RemoveUserCommand;
import com.ats.user.cmd.api.commands.UpdateUserCommand;
import com.ats.user.cmd.api.security.PasswordEncoder;
import com.ats.user.core.events.UserRegisteredEvent;
import com.ats.user.core.events.UserRemovedEvent;
import com.ats.user.core.events.UserUpdatedEvent;
import com.ats.user.core.models.User;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Aggregate
public class UserAggregate {
    @AggregateIdentifier
    private String id;
    private User user;

    @Autowired
    @CommandHandler
    public UserAggregate(RegisterUserCommand registerUserCommand, PasswordEncoder passwordEncoder) {
        var newUser = registerUserCommand.getUser();
        newUser.setId(registerUserCommand.getId());
        var password = newUser.getAccount().getPassword();
        var hashedPassword = passwordEncoder.hashPassword(password);
        newUser.getAccount().setPassword(hashedPassword);

        var event = UserRegisteredEvent.builder()
                .id(registerUserCommand.getId())
                .user(newUser)
                .build();

        AggregateLifecycle.apply(event);
    }

    @Autowired
    @CommandHandler
    public void handle(UpdateUserCommand updateUserCommand, PasswordEncoder passwordEncoder) {
        var updatedUser = updateUserCommand.getUser();
        updatedUser.setId(updateUserCommand.getId());
        var password = updatedUser.getAccount().getPassword();
        var hashedPassword = passwordEncoder.hashPassword(password);
        updatedUser.getAccount().setPassword(hashedPassword);

        var event = UserUpdatedEvent.builder()
                .id(UUID.randomUUID().toString())
                .user(updatedUser)
                .build();

        AggregateLifecycle.apply(event);
    }

    @Autowired
    @CommandHandler
    public void handle(RemoveUserCommand removeUserCommand, PasswordEncoder passwordEncoder) {
        var event = new UserRemovedEvent();
        event.setId(removeUserCommand.getId());

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(UserRegisteredEvent userRegisteredEvent) {
        this.id = userRegisteredEvent.getId();
        this.user = userRegisteredEvent.getUser();
    }

    @EventSourcingHandler
    public void on(UserUpdatedEvent userUpdatedEvent) {
        this.user = userUpdatedEvent.getUser();
    }

    @EventSourcingHandler
    public void on(UserRemovedEvent userRemovedEvent) {
        AggregateLifecycle.markDeleted();
    }
}
