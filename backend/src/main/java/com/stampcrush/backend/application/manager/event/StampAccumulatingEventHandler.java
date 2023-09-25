package com.stampcrush.backend.application.manager.event;

import com.slack.api.Slack;
import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.slack.api.webhook.WebhookPayloads.payload;

@Component
public class StampAccumulatingEventHandler {

    @Value("${slack.webhook.url}")
    private String webHookUrl;

    private final Slack slackClient = Slack.getInstance();

    @TransactionalEventListener
    public void process(StampCreateEvent stampCreateEvent) {
        String userPhone = stampCreateEvent.getUserPhone();
        int stampCount = stampCreateEvent.getStampCount();
        try {
            slackClient.send(webHookUrl, payload(p -> p
                    .text("스탬프 적립 발생")
                    .attachments(List.of(createStampInfo(userPhone, stampCount)))
            ));
        } catch (IOException e) {
            throw new RuntimeException(String.format("%s 번호 스탬프 %d개 적립 알림 전송 실패", userPhone, stampCount), e);
        }
    }

    private Attachment createStampInfo(String phone, int stampCount) {
        String requestTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now());
        return Attachment.builder()
                .title("스탬프 적립 시간" + requestTime)
                .fields(List.of(
                                generateField("유저 핸드폰", phone),
                                generateField("적립 스탬프", String.valueOf(stampCount))
                        )
                )
                .build();
    }

    private Field generateField(String title, String value) {
        return Field.builder()
                .title(title)
                .value(value)
                .valueShortEnough(true)
                .build();
    }
}
