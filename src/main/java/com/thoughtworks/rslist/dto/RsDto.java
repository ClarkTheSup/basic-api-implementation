package com.thoughtworks.rslist.dto;

import com.thoughtworks.rslist.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "rsevent")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RsDto {
    @Id
    @GeneratedValue
    private int id;

    private String name;
    private String keyword;
    private int userId;
}
