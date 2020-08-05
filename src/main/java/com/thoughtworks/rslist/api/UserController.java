package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.model.Error;
import com.thoughtworks.rslist.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private List<User> userList = new ArrayList<User>();

    @PostMapping("/user")
    public ResponseEntity registerUser(@RequestBody @Valid User user){
        Integer index = null;
        if (!userList.contains(user)) {
            userList.add(user);
            index = userList.indexOf(user);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("index", String.valueOf(index)).build();
    }

    @GetMapping("/users")
    public ResponseEntity getUserList() {
        return ResponseEntity.ok(userList);
    }

    @ExceptionHandler
    public ResponseEntity exceptionHandler(Exception e) {
        Error error = new Error();
        error.setError("invalid user");
        return ResponseEntity.badRequest().body(error);
    }
}
