package com.server.token.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.server.token.domain.entity.Board;
import com.server.token.domain.entity.QBoard;
import com.server.token.domain.entity.QUser;
import com.server.token.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class UserQueryDslRepository {
//    private final JPAQueryFactory query;

//    public User findAllUser(String userEmail){
//        QUser qUser = QUser.user;
//
//        User user = query
//                .selectFrom(qUser)
//                .where(qUser.userEmail.eq(userEmail))
//                .fetchOne();
//        return user;
//    }
//
//    public Board findBoard(String userEmail){
//        QBoard qBoard = QBoard.board;
//        QUser qUser = QUser.user;
//
//        query
//                .selectFrom(qBoard);
//    }


}