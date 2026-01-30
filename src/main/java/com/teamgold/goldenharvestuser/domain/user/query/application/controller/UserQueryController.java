package com.teamgold.goldenharvestuser.domain.user.query.application.controller;

import com.teamgold.goldenharvestuser.common.response.ApiResponse;
import com.teamgold.goldenharvestuser.domain.user.query.application.dto.reponse.UserProfileResponse;
import com.teamgold.goldenharvestuser.domain.user.query.application.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserQueryController {

    private final UserQueryService userQueryService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getMyProfile() {

        // 게이트웨이 / 인증 연동 전 임시 사용자
        String userEmail = "user@goldenharvest.com";

        UserProfileResponse userProfileResponse =
                userQueryService.getUserProfile(userEmail);

        return ResponseEntity.ok(ApiResponse.success(userProfileResponse));
    }
}
