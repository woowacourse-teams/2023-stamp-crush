package com.stampcrush.backend.entity.visithistory;

import com.stampcrush.backend.exception.VisitHistoryNotFoundException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class VisitHistories {

    private final List<VisitHistory> visitHistories;

    public int getVisitCount() {
        return visitHistories.size();
    }

    public LocalDateTime getFirstVisitDate() {
        VisitHistory firstVisit = visitHistories.stream()
                .min((v1, v2) -> v1.getCreatedAt().compareTo(v2.getCreatedAt()))
                .orElseThrow(() -> new VisitHistoryNotFoundException("첫 방문일을 찾을 수 없습니다"));

        return firstVisit.getCreatedAt();
    }
}
