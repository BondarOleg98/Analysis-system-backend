package com.ats.user.query.api.handlers;

import com.ats.user.core.events.UserRegisteredEvent;
import com.ats.user.core.events.UserRemovedEvent;
import com.ats.user.core.events.UserUpdatedEvent;
import com.ats.user.query.api.repositories.UserRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@ProcessingGroup("user-group")
public class UserEventHandlerImpl implements UserEventHandler {

    private final UserRepository userRepository;

    @Autowired
    public UserEventHandlerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventHandler
    @Override
    public void on(UserRegisteredEvent userRegisteredEvent) {
        userRepository.save(userRegisteredEvent.getUser());
    }

    @EventHandler
    @Override
    public void on(UserUpdatedEvent userUpdatedEvent) {
        userRepository.save(userUpdatedEvent.getUser());
    }

    @EventHandler
    @Override
    @Transactional
    public void on(UserRemovedEvent userRemovedEvent) {
        userRepository.deleteByUserId(userRemovedEvent.getId());
    }
}
