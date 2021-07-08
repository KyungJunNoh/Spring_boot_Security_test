package com.server.token.service.Impl;

import com.server.token.domain.dto.BoardCreateRequestDto;
import com.server.token.domain.dto.BoardUpdateRequestDto;
import com.server.token.domain.entity.Board;
import com.server.token.exception.UserNotFoundException;
import com.server.token.repository.BoardRepository;
import com.server.token.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    @Override
    public Long create(BoardCreateRequestDto boardCreateRequestDto) {
        return boardRepository.save(boardCreateRequestDto.toEntity()).getIdx();
    }

    @Transactional
    @Override
    public Long update(Long idx, BoardUpdateRequestDto boardUpdateRequestDto) {
        Board board = boardRepository.findById(idx).orElseThrow(IllegalArgumentException ::new);

        board.update(boardUpdateRequestDto.getTitle(), boardUpdateRequestDto.getContent());
        return idx;
    }

}
