package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.model.Rs;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class RsController {
  private List<String> rsList = Arrays.asList("第一条事件", "第二条事件", "第三条事件");

  @GetMapping("/rs/list")
  public String getRsListString() {
    return rsList.toString();
  }

  @GetMapping("/rs/{index}")
  public String getRsString(@PathVariable int index) {
    return rsList.get(index-1);
  }

  @GetMapping("/rs/sublist")
  public String getRsListBetweenString(@RequestParam(required = false) Integer start, @RequestParam Integer end) {
    if (start == null) {
      return rsList.subList(0, end-1).toString();
    } else {
      return rsList.subList(start-1, end-1).toString();
    }
  }

  @PostMapping("/rs/addRs")
  public void addRsToList(@RequestBody Rs rs) {
    rsList.add(rs.getName());
  }

}
