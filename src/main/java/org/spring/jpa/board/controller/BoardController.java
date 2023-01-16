package org.spring.jpa.board.controller;

import lombok.RequiredArgsConstructor;
import org.spring.jpa.board.dto.BoardDto;
import org.spring.jpa.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@Controller // -> 요청 -> Date 담아 View페이지로 전송
@RequestMapping("/board") // -> 요청 ->
@RequiredArgsConstructor
public class BoardController {

    /*@Autowired
    private BoardService boardService;*/

    private final BoardService boardService;

    // /board/  , /board/index
    @GetMapping({"","/index"}) // -> {}안에 두개의 uri를 요청할수 있음
    public String index(){
        return "board/index";
    }

    @GetMapping("/write")
    public String write(){
        return "board/write";
    }

    @PostMapping("/write")
    public String writeInsert(@ModelAttribute BoardDto boardDto){

        boardService.insertBoard(boardDto);
        // 글목록으로 바로 이동
        return "redirect:list";
    }

    @GetMapping("/list")
    public String list(Model model){

        List<BoardDto> boardlist=boardService.boardListDo();

        model.addAttribute("lists", boardlist);

        return "/board/boardList";
    }

    @GetMapping("/detail/{id}")
    public String boardDetail(@PathVariable Long id, Model model){

        // 게시글 조회수 1증가
        boardService.upHit(id);
        BoardDto board=boardService.boardDetail(id);
        if(board!=null){
            System.out.println("게시글 Ok -> 글상세보기로 이동");
            model.addAttribute("board",board);
            return "/board/boardDetail";
        }else{
            System.out.println("게시글 Null -> 글작성페이지로 이동");
            return "redirect:/board/write";
        }

    }

    @GetMapping("/pagingList")
    public String pagingList(Model model, @PageableDefault (page=0,size=3,
                             sort="id", direction=Sort.Direction.DESC) Pageable pageable){
        Page<BoardDto> boardList=boardService.boardPagingList(pageable);

        int bockNum=4;
        int nowPage=boardList.getNumber()+1; // 현재 페이지
        int startPage=Math.max(1,boardList.getNumber()-bockNum);
        int endPage=boardList.getTotalPages();

        model.addAttribute("boardList",boardList);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        return "/board/pagingList";
    }


}
