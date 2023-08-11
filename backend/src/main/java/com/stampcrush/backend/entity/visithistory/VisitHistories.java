package com.stampcrush.backend.entity.visithistory;

import com.stampcrush.backend.entity.baseentity.BaseDate;
import com.stampcrush.backend.exception.VisitHistoryNotFoundException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class VisitHistories {

    private final List<VisitHistory> visitHistories;

    public int getVisitCount() {
        return visitHistories.size();
    }

    public LocalDateTime getFirstVisitDate() {
        VisitHistory firstVisit = visitHistories.stream()
                .min(Comparator.comparing(BaseDate::getCreatedAt))
                .orElseThrow(() -> new VisitHistoryNotFoundException("첫 방문일을 찾을 수 없습니다"));

        return firstVisit.getCreatedAt();
    }
}
