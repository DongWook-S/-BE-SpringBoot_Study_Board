package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;
    @GetMapping("/board/write")
    public String BoardWriteForm() {
        return "BoardWrite";
    }

    @PostMapping("/board/writePro")
    public String BoardWritePro(Board board) {

        boardService.boardWrite(board);

        return "BoardWrite";
    }

    @GetMapping("/board/list")
    public String BoardList(Model model) {

        model.addAttribute("list", boardService.boardList());

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

        return "redirect:/board/list";
    }
}
