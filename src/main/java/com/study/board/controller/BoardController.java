package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

        boardService.write(board);

        return "BoardWrite";
    }
}
