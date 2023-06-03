package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;
    @GetMapping("/board/write")
    public String BoardWriteForm() {
        return "BoardWrite";
    }
    @PostMapping("/board/writePro")
    public String BoardWritePro(Board board, Model model, MultipartFile file) {

        boardService.boardWrite(board, file);

        model.addAttribute("message", "글작성이 완료 되었습니다.");
        model.addAttribute("searchUrl", "/board/List");

        return "message";
    }
    @GetMapping("/board/List")
    public String BoardList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            String searchKeyword) {

        Page<Board> list = null;

        if (searchKeyword == null) {
            list = boardService.boardList(pageable);
        } else {
            list = boardService.boardSearchList(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "BoardList";
    }
    @GetMapping("/board/view")
    public String BoardView(Model model, Integer id) {

        model.addAttribute("board", boardService.boardView(id));

        return "BoardView";
    }
    @GetMapping("/board/del")
    public String BoardDel(Integer id) {

        boardService.boardDel(id);

        return "redirect:/board/List";
    }
    @GetMapping("/board/modify/{id}")
    public String BoardModify(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("board", boardService.boardView(id));

        return "BoardModify";
    }
    @PostMapping("/board/update/{id}")
    public String BoardUpdate(@PathVariable("id") Integer id, Board board, MultipartFile file) {

        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());
        boardTemp.setFilename(board.getFilename());
        boardTemp.setFilepath(board.getFilepath());

        boardService.boardWrite(boardTemp, file);

        return "redirect:/board/List";
    }
}
