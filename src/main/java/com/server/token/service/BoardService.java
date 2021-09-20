package com.server.token.service;

import com.server.token.domain.dto.BoardCreateRequestDto;
import com.server.token.domain.dto.BoardUpdateRequestDto;

import javax.servlet.http.HttpServletRequest;

public interface BoardService {
    void create(BoardCreateRequestDto boardCreateRequestDto, HttpServletRequest httpServletRequest);
    Long update(Long idx, BoardUpdateRequestDto boardUpdateRequestDto);
}
