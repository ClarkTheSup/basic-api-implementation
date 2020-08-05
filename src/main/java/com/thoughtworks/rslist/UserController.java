package com.thoughtworks.rslist;

import com.thoughtworks.rslist.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private List<User> userList = new ArrayList<User>();

    @PostMapping("/user")
    public ResponseEntity registerUser(@RequestBody @Valid User user) {
        if (!userList.contains(user)) {
            userList.add(user);
        }
        return ResponseEntity.created(null).build();
    }

    @GetMapping("/user/list")
    public ResponseEntity getUserList() {
        return ResponseEntity.ok(userList);
    }
}
