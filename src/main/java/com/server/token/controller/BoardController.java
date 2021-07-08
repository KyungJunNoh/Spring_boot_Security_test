package com.server.token.controller;

import com.server.token.domain.dto.BoardCreateRequestDto;
import com.server.token.domain.dto.BoardUpdateRequestDto;
import com.server.token.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/create")
    public Long create(@RequestBody BoardCreateRequestDto boardCreateRequestDto){
        return boardService.create(boardCreateRequestDto);
    }

    @PutMapping("/update/{id}")
    public Long update(@PathVariable Long idx, @RequestBody BoardUpdateRequestDto boardUpdateRequestDto){
        return boardService.update(idx,boardUpdateRequestDto);
    }
}
