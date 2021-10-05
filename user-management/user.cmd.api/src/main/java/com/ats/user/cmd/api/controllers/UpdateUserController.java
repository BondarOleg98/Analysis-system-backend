package com.ats.user.cmd.api.controllers;

import com.ats.user.cmd.api.commands.UpdateUserCommand;
import com.ats.user.cmd.api.dto.BaseResponse;
import com.ats.user.cmd.api.dto.RegisterUserResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/updateUser")
public class UpdateUserController {
    private final CommandGateway commandGateway;

    @Autowired
    public UpdateUserController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<BaseResponse> updateUser(@PathVariable(name = "id") String id,
                                                   @Valid @RequestBody UpdateUserCommand updateUserCommand) {
        try {
            updateUserCommand.setId(id);
            updateUserCommand.getUser().getAccount().setId(updateUserCommand.getId());
            commandGateway.send(updateUserCommand);
            return new ResponseEntity<>(new BaseResponse("User successfully updated"), HttpStatus.OK);
        } catch (Exception exception) {
            var safeErrorMessage = "Error while processing updating user request for id - " + id;
            return new ResponseEntity<>(new RegisterUserResponse(id, safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
