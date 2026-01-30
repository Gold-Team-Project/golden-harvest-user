package com.teamgold.goldenharvestuser.domain.user.query.application.service;

import com.teamgold.goldenharvestuser.domain.user.query.application.dto.reponse.UserProfileResponse;

public interface UserQueryService {

    UserProfileResponse getUserProfile(String email);
}
