package com.thoughtworks.rslist;

import com.thoughtworks.rslist.model.User;
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
    public void registerUser(@RequestBody @Valid User user) {
        if (!userList.contains(user)) {
            userList.add(user);
        }
    }

    @GetMapping("/user/list")
    public List<User> getUserList() {
        return userList;
    }
}
