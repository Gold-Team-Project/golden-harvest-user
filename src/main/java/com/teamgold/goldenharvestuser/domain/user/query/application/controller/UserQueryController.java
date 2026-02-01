package com.teamgold.goldenharvestuser.domain.user.query.application.controller;

import com.teamgold.goldenharvestuser.common.response.ApiResponse;
import com.teamgold.goldenharvestuser.domain.user.query.application.dto.reponse.UserProfileResponse;
import com.teamgold.goldenharvestuser.domain.user.query.application.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserQueryController {

    private final UserQueryService userQueryService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getMyProfile(
            @AuthenticationPrincipal Jwt jwt
            ) {
        UserProfileResponse userProfileResponse =
                userQueryService.getUserProfile(jwt.getSubject());

        return ResponseEntity.ok(ApiResponse.success(userProfileResponse));
    }
}
