package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.dto.VoteDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoteRepository extends CrudRepository<VoteDto, Integer> {
    @Query(nativeQuery=true, value="select * from vote where vote_time >= ?1 and vote_time <= ?2")
    List<VoteDto> findAllBetweenTime(int startTime,int endTime);
}
