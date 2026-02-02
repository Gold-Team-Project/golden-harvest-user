package com.teamgold.goldenharvestuser.domain.user.command.application.event;

import com.teamgold.goldenharvestuser.common.broker.KafkaProducerHelper;
import com.teamgold.goldenharvestuser.domain.user.command.application.event.dto.UserStatusUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserStatusUpdatedEventRelay {

    private final KafkaProducerHelper producer;

    @Async
    @EventListener
    public void userStatusUpdatedEventRelay(UserStatusUpdatedEvent event) {
        log.info("Publishing userStatusUpdatedEvent");

        producer.send("user.status.updated",
                UUID.randomUUID().toString(),
                event,
                null);
        // Todo: onFailure callback
    }
}
