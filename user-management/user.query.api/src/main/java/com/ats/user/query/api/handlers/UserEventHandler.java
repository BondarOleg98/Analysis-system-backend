package com.ats.user.query.api.handlers;

import com.ats.user.core.events.UserRegisteredEvent;
import com.ats.user.core.events.UserRemovedEvent;
import com.ats.user.core.events.UserUpdatedEvent;

public interface UserEventHandler {
    void on(UserRegisteredEvent userRegisteredEvent);
    void on(UserUpdatedEvent userUpdatedEvent);
    void on(UserRemovedEvent userRemovedEvent);
}
