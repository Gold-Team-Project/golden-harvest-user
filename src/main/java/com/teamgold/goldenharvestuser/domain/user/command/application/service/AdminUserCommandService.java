package com.teamgold.goldenharvestuser.domain.user.command.application.service;
import com.teamgold.goldenharvestuser.domain.user.command.domain.RequestStatus;
import com.teamgold.goldenharvestuser.domain.user.command.domain.UserStatus;

public interface AdminUserCommandService {

    void approveUser(String email, UserStatus newStatus);

    void processProfileUpdate(Long requestId, RequestStatus status);

    void updateUserStatus(String targetEmail, UserStatus newStatus, String adminEmail);

	void publishAllUserDetailsEvent();

    void updateUserRole(String targetEmail, String newRole, String adminEmail);
}
