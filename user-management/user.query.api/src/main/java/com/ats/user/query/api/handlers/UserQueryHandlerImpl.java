package com.ats.user.query.api.handlers;

import com.ats.user.query.api.dto.UserDataResponse;
import com.ats.user.query.api.queries.FindAllUsersQuery;
import com.ats.user.query.api.queries.FindUserByIdQuery;
import com.ats.user.query.api.queries.SearchUserQuery;
import com.ats.user.query.api.repositories.UserRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserQueryHandlerImpl implements UserQueryHandler{

    private final UserRepository userRepository;

    @Autowired
    public UserQueryHandlerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @QueryHandler
    @Override
    public UserDataResponse getUserById(FindUserByIdQuery query) {
        var user = userRepository.findById(query.getId());
        return user.map(UserDataResponse::new).orElse(null);
    }

    @QueryHandler
    @Override
    public UserDataResponse getAllUsers(FindAllUsersQuery query) {
        return new UserDataResponse(new HashSet<>(userRepository.findAll()));
    }

    @QueryHandler
    @Override
    public UserDataResponse searchUsers(SearchUserQuery query) {
        return new UserDataResponse(new HashSet<>(userRepository.findByFilter(query.getFilter())));
    }
}
