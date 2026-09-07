package com.mink.projecttrip.alarm.repository;

import com.mink.projecttrip.alarm.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    List<Alarm> findByUserIdOrderByCreatedAtDesc(long userId);
    Optional<Alarm> findByUserIdAndFromUserIdAndTypeAndAction(long userId, long fromUserId, String type, String action);
}
