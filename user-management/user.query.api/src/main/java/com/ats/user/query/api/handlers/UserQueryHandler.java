package com.ats.user.query.api.handlers;

import com.ats.user.query.api.dto.UserDataResponse;
import com.ats.user.query.api.queries.FindAllUsersQuery;
import com.ats.user.query.api.queries.FindUserByIdQuery;
import com.ats.user.query.api.queries.SearchUserQuery;

public interface UserQueryHandler {
    UserDataResponse getUserById(FindUserByIdQuery query);
    UserDataResponse getAllUsers(FindAllUsersQuery query);
    UserDataResponse searchUsers(SearchUserQuery query);
}
