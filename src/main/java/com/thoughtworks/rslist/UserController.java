package com.thoughtworks.rslist;

import com.thoughtworks.rslist.model.User;
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
        System.out.println("user:" + user);
        userList.add(user);
    }
}
