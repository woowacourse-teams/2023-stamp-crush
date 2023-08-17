package com.stampcrush.backend.entity.visithistory;

import com.stampcrush.backend.entity.baseentity.BaseDate;
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
        if (visitHistories.isEmpty()) {
            return LocalDateTime.now();
        }
        VisitHistory firstVisit = visitHistories.stream()
                .min(Comparator.comparing(BaseDate::getCreatedAt))
                .get();

        return firstVisit.getCreatedAt();
    }
}
