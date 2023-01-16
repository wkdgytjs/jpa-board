package org.spring.jpa.board.service;

import lombok.RequiredArgsConstructor;
import org.spring.jpa.board.dto.BoardDto;
import org.spring.jpa.board.entity.BoardEntity;
import org.spring.jpa.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    //1. DI(의존성 주입) 방법 **
    /*@Autowired
    private BoardRepository boardRepository;*/

    //2. 많이 쓰이지 않음
    /*private final BoardRepository boardRepository;
    private BoardService(BoardRepository boardRepository){
        this.boardRepository=boardRepository;
    }*/

    //3.  @RequiredArgsConstructor 와 함께 쓰임  **
    private final BoardRepository boardRepository;

    @Transactional
    public void insertBoard(BoardDto boardDto) {

        BoardEntity boardEntity = BoardEntity.toBoardEntity(boardDto);

        boardRepository.save(boardEntity);
    }


    public List<BoardDto> boardListDo() {
        List<BoardDto> boardDtoList=new ArrayList<>();

        List<BoardEntity> optionalEntity=boardRepository.findAll();

        for(BoardEntity boardEntity:optionalEntity){
            boardDtoList.add(BoardDto.toGetBoardListDo(boardEntity));
        }
        return boardDtoList;

    }
    @Transactional
    public BoardDto boardDetail(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);

        if(optionalBoardEntity.isPresent()){

            BoardDto boardDto=BoardDto.toGetBoardListDo(optionalBoardEntity.get());

            return boardDto;

            /*return BoardDto.toGetBoardDto(optionalBoardEntity.get());*/
        }else {
            return null;
        }

    }
    @Transactional // insert, update, delete
    public int upHit(Long id) {
        // 직접 SQL작성 Repository
        return boardRepository.upHitCount(id);
    }


    public Page<BoardDto> boardPagingList(Pageable pageable) {
        // DB -> Entity
        Page<BoardEntity> boardEntityList=boardRepository.findAll(pageable);
        // Dto List
        Page<BoardDto> boardDtoList=boardEntityList.map(BoardDto::toGetBoardListDo);

        return boardDtoList; // List Dto
    }
}
