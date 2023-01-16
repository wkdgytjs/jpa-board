package org.spring.jpa.board.dto;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.spring.jpa.board.entity.BoardEntity;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BoardDto {

    private Long Id;
    private String title;
    private String content;
    private String writer;
    private int hit=0;
    private String boardPw;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // Dto -> Entity
    public static BoardEntity toBoardDto(BoardDto boardDto){

        BoardEntity boardEntity=new BoardEntity();

        boardEntity.setTitle(boardDto.getTitle());
        boardEntity.setContent(boardDto.getContent());
        boardEntity.setWriter(boardDto.getWriter());
        boardEntity.setBoardPw(boardDto.getBoardPw());

        return boardEntity;
    }
    // Entity -> Dto 변환
    // 추가 -> 글 등록
    // id, createTime, updateTime -> 자동 추가
    public static BoardDto toBoardDto(BoardEntity boardEntity){

        BoardDto boardDto=new BoardDto();

        boardDto.setTitle(boardEntity.getTitle());
        boardDto.setContent(boardEntity.getContent());
        boardDto.setWriter(boardEntity.getWriter());
        boardDto.setHit(boardEntity.getHit());
        boardDto.setBoardPw(boardEntity.getBoardPw());

        return boardDto;
    }

    // Builder 적용
    public static BoardDto toGetBoardDto(BoardEntity boardEntity){
        return BoardDto.builder()
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .writer(boardEntity.getWriter())
                .hit(boardEntity.getHit())
                .boardPw(boardEntity.getBoardPw())
                .build();
    }
    public static BoardDto toGetBoardListDo(BoardEntity boardEntity){

        BoardDto boardDto=new BoardDto();

        boardDto.setId(boardEntity.getId());
        boardDto.setTitle(boardEntity.getTitle());
        boardDto.setContent(boardEntity.getContent());
        boardDto.setWriter(boardEntity.getWriter());
        boardDto.setHit(boardEntity.getHit());
        boardDto.setBoardPw(boardEntity.getBoardPw());
        boardDto.setCreateTime(boardEntity.getCreateTime());

        return boardDto;
    }
}
