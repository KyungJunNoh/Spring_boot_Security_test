package com.server.token.controller;

import com.server.token.domain.dto.BoardCreateRequestDto;
import com.server.token.domain.dto.BoardUpdateRequestDto;
import com.server.token.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/create")
    public String create(@RequestBody BoardCreateRequestDto boardCreateRequestDto, HttpServletRequest httpServletRequest){
        boardService.create(boardCreateRequestDto,httpServletRequest);
        return "Success";
    }

    @PutMapping("/update/{id}")
    public Long update(@PathVariable Long idx, @RequestBody BoardUpdateRequestDto boardUpdateRequestDto){
        return boardService.update(idx,boardUpdateRequestDto);
    }
}
