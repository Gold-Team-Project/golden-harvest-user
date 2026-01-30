package com.teamgold.goldenharvestuser.domain.user.query.application.service;

import com.teamgold.goldenharvestuser.domain.user.query.application.dto.reponse.UserAdminResponse;
import com.teamgold.goldenharvestuser.domain.user.query.application.dto.reponse.UserUpdateApprovalResponse;

import java.util.List;

public interface AdminUserQueryService {

    List<UserAdminResponse> getAllUsersForAdmin();

    List<UserUpdateApprovalResponse> getPendingUpdateRequests();
}
