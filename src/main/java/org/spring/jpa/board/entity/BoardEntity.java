package org.spring.jpa.board.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.spring.jpa.board.dto.BoardDto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Entity
@Table(name = "board_jpa_tb") // table 이름 기본값 클래스 이름
@Builder
public class BoardEntity {

    @Id //기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동1증가
    @Column(name = "board_id") // id 이름
    private Long Id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 255)
    private String content;

    @Column(nullable = false)
    private String writer;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int hit;

    @Column(nullable = false)
    private String boardPw; // board_pw 컬럼 이름 기본값

    @CreationTimestamp // 자동 생성
    @Column(updatable = false) // update시에는 적용X
    private LocalDateTime createTime; // create_time

    @UpdateTimestamp // 자동 수정
    @Column(insertable = false) // create시에는 적용X
    private LocalDateTime updateTime;

    // Dto -> Entity 변환
    // 추가 -> 글 등록
    // id, createTime, updateTime -> 자동 추가
    public static BoardEntity toBoardEntity(BoardDto boardDto){

        BoardEntity boardEntity=new BoardEntity();

        boardEntity.setTitle(boardDto.getTitle());
        boardEntity.setContent(boardDto.getContent());
        boardEntity.setWriter(boardDto.getWriter());
        boardEntity.setHit(boardDto.getHit());
        boardEntity.setBoardPw(boardDto.getBoardPw());

        return boardEntity;
    }

    // Builder 적용
    public static BoardEntity toGetBoardEntity(BoardDto boardDto){
        return BoardEntity.builder()
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .writer(boardDto.getWriter())
                .hit(boardDto.getHit())
                .boardPw(boardDto.getBoardPw())
                .build();
    }
}
