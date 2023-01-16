package org.spring.jpa.board.repository;

import org.spring.jpa.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
                //테이블명이 아닌 entity명으로 써줘야함
    @Modifying
    @Query(value = "update BoardEntity b set b.hit = b.hit + 1 where b.id = :id")
    int upHitCount(Long id);

}
