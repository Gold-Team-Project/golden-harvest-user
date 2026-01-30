package com.teamgold.goldenharvestuser.domain.user.command.application.event;

import com.teamgold.goldenharvestuser.domain.user.command.application.event.dto.UserUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUpdateEventPublisher {

	private final ApplicationEventPublisher publisher;

	public void publishUpdatedUserDetails(UserUpdatedEvent event) {
		publisher.publishEvent(event);
	}
}
