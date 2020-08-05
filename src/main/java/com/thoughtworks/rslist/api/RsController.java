package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.model.Error;
import com.thoughtworks.rslist.model.Rs;
import com.thoughtworks.rslist.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController {
  private List<Rs> rsList = new ArrayList<Rs>();

  {
    User user1 = new User("clark", "男", 19, "lkn@163.com",
            "11111111111", 10);
    User user2 = new User("jack", "女", 25, "jack@163.com",
            "11111111112", 10);
    User user3 = new User("amy", "男", 28, "amy@163.com",
            "11111111113", 10);
    rsList.add(new Rs("第一条事件", "猪肉", user1));
    rsList.add(new Rs("第二条事件", "牛肉", user2));
    rsList.add(new Rs("第三条事件", "羊肉", user3));
  }

  @GetMapping("/rs/list")
  public ResponseEntity getRsListString() {
    return ResponseEntity.ok(rsList);
  }

  @GetMapping("/rs/{index}")
  public ResponseEntity getRsString(@PathVariable int index) {
    if (index < 1 || index > rsList.size()) {
      throw new RsNotValidException("invalid index");
    }
    return ResponseEntity.ok(rsList.get(index-1));
  }

  @GetMapping("/rs/sublist")
  public ResponseEntity getRsListBetweenString(@RequestParam(required = false) Integer start, @RequestParam Integer end) {
    if (start == null) {
      return ResponseEntity.ok(rsList.subList(0, end-1));
    } else {
      return ResponseEntity.ok(rsList.subList(start-1, end-1));
    }
  }

  @PostMapping("/rs/addRs")
  public ResponseEntity addRsToList(@RequestBody @Valid Rs rs) {
    rsList.add(rs);
    int index = rsList.indexOf(rs);
    System.out.println(rs);
    return ResponseEntity.status(HttpStatus.CREATED)
            .header("index", String.valueOf(index)).build();
  }

  @PostMapping("/rs/modifyRs/{index}")
  public ResponseEntity modifyRsInList(@PathVariable Integer index, @RequestBody Rs rs) {
    if (rs.getName() == null && rs.getKeyword() == null) {
      throw new RuntimeException("no new params");
    }

    if (rs.getName() != null) {
      rsList.get(index-1).setName(rs.getName());
    }

    if (rs.getKeyword() != null) {
      rsList.get(index-1).setKeyword(rs.getKeyword());
    }
    return ResponseEntity.created(null).build();
  }

  @DeleteMapping("/rs/deleteRs/{index}")
  public ResponseEntity deleteRsInList(@PathVariable Integer index) {
    rsList.remove(index-1);
    return ResponseEntity.ok(null);
  }

  @ExceptionHandler
  public ResponseEntity handleException(Exception e) {
    Error error = new Error();
    error.setError(e.getMessage());
    return ResponseEntity.badRequest().body(error);
  }

}
