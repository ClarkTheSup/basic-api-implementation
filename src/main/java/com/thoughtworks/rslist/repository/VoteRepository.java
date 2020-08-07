package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.dto.VoteDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoteRepository extends CrudRepository<VoteDto, Integer> {
    @Query(nativeQuery=true, value="select * from vote v where v.vote_time >= ? and v.vote_time <= ?")
    List<VoteDto> findAllBetweenTime(String startTime,String endTime);
}
