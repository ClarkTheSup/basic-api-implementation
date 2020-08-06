package com.thoughtworks.rslist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "vote")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteDto {
    @Id
    @GeneratedValue
    int id;
    int voteNum;
    int userId;
    String voteTime;
}
