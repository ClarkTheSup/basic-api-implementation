package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoteController {
    @Autowired
    VoteRepository voteRepository;

    @GetMapping("/vote/listBetweenTime")
    public ResponseEntity getVoteListBetweenTime(@RequestParam int startTime, @RequestParam int endTime) {
        return ResponseEntity.status(HttpStatus.OK).body(
                voteRepository.findAllBetweenTime(startTime, endTime));
    }

}
