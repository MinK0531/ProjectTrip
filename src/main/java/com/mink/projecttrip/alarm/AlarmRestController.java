package com.mink.projecttrip.alarm;

import com.mink.projecttrip.alarm.dto.AlarmDetail;
import com.mink.projecttrip.alarm.service.AlarmService;
import com.mink.projecttrip.common.dto.ApiResponse;
import com.mink.projecttrip.friend.service.FriendService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alarm")
@RequiredArgsConstructor
public class AlarmRestController {

    private final FriendService friendService;
    private final AlarmService alarmService;

    @GetMapping("/list")
    public ApiResponse<List<AlarmDetail>> list(HttpSession session) {

        if (session.getAttribute("userId") == null) {
            return ApiResponse.fail("로그인이 필요합니다.");
        }

        long userId = (long) session.getAttribute("userId");

        List<AlarmDetail> alarms = alarmService.getAlarmList(userId);

        return ApiResponse.success("알람 성공",alarms);
    }

    @PostMapping("/read/{alarmId}")
    public ApiResponse<Void> read(
            @PathVariable Long alarmId,
            HttpSession session) {

        if (session.getAttribute("userId") == null) {
            return ApiResponse.fail("로그인이 필요합니다.");
        }

        long userId = (long) session.getAttribute("userId");

        alarmService.readAlarm(alarmId, userId);

        return ApiResponse.success("읽음 처리 완료", null);
    }

}
