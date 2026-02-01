package com.teamgold.goldenharvestuser.domain.user.command.application.event;

import com.teamgold.goldenharvestuser.domain.user.command.application.event.dto.UserStatusUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserStatusUpdateEventPublisher {

	private final ApplicationEventPublisher publisher;

	public void publishUserStatusUpdatedEvent(UserStatusUpdatedEvent event) {
		publisher.publishEvent(event);
	}
}
