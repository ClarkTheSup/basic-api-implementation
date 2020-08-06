package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.dto.UserDto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RsRepository extends CrudRepository<UserDto, Integer> {
    @Override
    List<UserDto> findAll();
}
