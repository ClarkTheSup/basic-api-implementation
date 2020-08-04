package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.model.Rs;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class RsController {
  private List<Rs> rsList = new ArrayList<Rs>();

  {
    rsList.add(new Rs("第一条事件", "猪肉"));
    rsList.add(new Rs("第二条事件", "牛肉"));
    rsList.add(new Rs("第三条事件", "羊肉"));
  }

  @GetMapping("/rs/list")
  public List<Rs> getRsListString() {
    return rsList;
  }

  @GetMapping("/rs/{index}")
  public Rs getRsString(@PathVariable int index) {
    return rsList.get(index-1);
  }

  @GetMapping("/rs/sublist")
  public List<Rs> getRsListBetweenString(@RequestParam(required = false) Integer start, @RequestParam Integer end) {
    if (start == null) {
      return rsList.subList(0, end-1);
    } else {
      return rsList.subList(start-1, end-1);
    }
  }

  @PostMapping("/rs/addRs")
  public void addRsToList(@RequestBody Rs rs) {
    rsList.add(rs);
  }

  @PostMapping("/rs/modifyRs/{index}")
  public void modifyRsInList(@PathVariable Integer index, @RequestBody Rs rs) {
    rsList.set(index-1, rs);
  }

}
