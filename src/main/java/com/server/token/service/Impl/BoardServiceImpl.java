package com.server.token.service.Impl;

import com.server.token.domain.dto.BoardCreateRequestDto;
import com.server.token.domain.dto.BoardUpdateRequestDto;
import com.server.token.domain.entity.Board;
import com.server.token.exception.UserNotFoundException;
import com.server.token.repository.BoardRepository;
import com.server.token.repository.UserRepository;
import com.server.token.security.JwtTokenProvider;
import com.server.token.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    public void create(BoardCreateRequestDto boardCreateRequestDto, HttpServletRequest httpServletRequest) {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String name = jwtTokenProvider.getUsername(token);
        boardCreateRequestDto.setUser(userRepository.findByUserEmail(name));
        boardRepository.save(boardCreateRequestDto.toEntity());
    }

    @Transactional
    @Override
    public Long update(Long idx, BoardUpdateRequestDto boardUpdateRequestDto) {
        Board board = boardRepository.findById(idx).orElseThrow(IllegalArgumentException ::new);

        board.update(boardUpdateRequestDto.getTitle(), boardUpdateRequestDto.getContent());
        return idx;
    }

}
