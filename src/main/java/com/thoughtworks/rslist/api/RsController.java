package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.dto.RsDto;
import com.thoughtworks.rslist.dto.VoteDto;
import com.thoughtworks.rslist.exception.RsIndexNotValidException;
import com.thoughtworks.rslist.exception.StartEndParamException;
import com.thoughtworks.rslist.domain.Error;
import com.thoughtworks.rslist.domain.Rs;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.repository.RsRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController {
  private List<Rs> rsList = new ArrayList<Rs>();
  private Logger logger = LoggerFactory.getLogger(RsController.class);
  @Autowired
  private RsRepository rsRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private VoteRepository voteRepository;

  {
    User user1 = new User("clark", "男", 19, "lkn@163.com",
            "11111111111", 10);
    User user2 = new User("jack", "女", 25, "jack@163.com",
            "11111111112", 10);
    User user3 = new User("amy", "男", 28, "amy@163.com",
            "11111111113", 10);
    rsList.add(new Rs("第一条事件", "猪肉", 1));
    rsList.add(new Rs("第二条事件", "牛肉", 2));
    rsList.add(new Rs("第三条事件", "羊肉", 3));
  }

  @GetMapping("/rs/list")
  public ResponseEntity getRsListString() {
    return ResponseEntity.ok(rsRepository.findAll());
  }

  @GetMapping("/rs/{index}")
  public ResponseEntity getRsString(@PathVariable int index) {
    return ResponseEntity.ok(rsRepository.findById(index));
  }


  @PostMapping("/rs/addRs")
  public ResponseEntity addRs(@RequestBody @Valid Rs rs) {
    if(userRepository.findUserById(rs.getUserId()) == null){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    RsDto rsDto = RsDto.builder().name(rs.getName()).keyword(rs.getKeyword()).userId(rs.getUserId()).build();
    rsRepository.save(rsDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @DeleteMapping("/rs/deleteRs/{index}")
  public ResponseEntity deleteRsInList(@PathVariable Integer index) {
    rsList.remove(index-1);
    return ResponseEntity.ok(null);
  }

  @PostMapping("/rs/vote/{rsEventId}")
  @Transactional
  public ResponseEntity vote(@RequestBody Vote vote) {
    if (userRepository.findUserById(vote.getUserId()).getVoteNum() < vote.getVoteNum()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    VoteDto voteDto = VoteDto.builder().voteNum(vote.getVoteNum())
            .userId(vote.getUserId()).voteTime(vote.getVoteTime()).build();
    voteRepository.save(voteDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @ExceptionHandler
  public ResponseEntity handleException(Exception e) {
    Error error = new Error();
    String message = null;
    if(e instanceof StartEndParamException || e instanceof RsIndexNotValidException) {
      message = e.getMessage();
      error.setError(message);
    } else {
      message = "invalid param";
      error.setError(message);
    }
    logger.error(message);
    return ResponseEntity.badRequest().body(error);
  }

}
