package com.example.capstone2.reservation.dao;

import com.example.capstone2.guesthouse.entity.Room;
import com.example.capstone2.reservation.entitiy.Reservation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.example.capstone2.reservation.entitiy.QReservation.reservation;
import static com.example.capstone2.user.entity.QUser.user;

@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<Reservation> findAllRelated(Room room, LocalDate date) {
        return queryFactory.selectFrom(reservation)
                .where(reservation.room.eq(room)
                        .and(reservation.checkInDate.loe(date))
                        .and(reservation.checkOutDate.gt(date)))
                .innerJoin(reservation.user, user)
                .fetchJoin()
                .fetch();
    }
}
