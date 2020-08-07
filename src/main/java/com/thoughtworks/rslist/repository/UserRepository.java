package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.dto.UserDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends CrudRepository<UserDto, Integer> {
    @Override
    List<UserDto> findAll();

    UserDto findUserById(int id);

    void deleteById(int id);

    @Modifying
    @Transactional
    @Query(value = "update user u set u.vote_num = ? where u.id = ?", nativeQuery = true)
    void updateUserVoteNumById(int voteNum, int id);
}
