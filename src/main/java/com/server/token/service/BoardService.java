package com.server.token.service;

import com.server.token.domain.dto.BoardCreateRequestDto;
import com.server.token.domain.dto.BoardUpdateRequestDto;

public interface BoardService {
    Long create(BoardCreateRequestDto boardCreateRequestDto);
    Long update(Long idx, BoardUpdateRequestDto boardUpdateRequestDto);
}
