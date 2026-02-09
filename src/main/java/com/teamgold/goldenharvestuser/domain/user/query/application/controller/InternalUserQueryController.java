package com.teamgold.goldenharvestuser.domain.user.query.application.controller;

import com.teamgold.goldenharvestuser.common.response.ApiResponse;
import com.teamgold.goldenharvestuser.domain.user.query.application.dto.reponse.UserProfileResponse;
import com.teamgold.goldenharvestuser.domain.user.query.application.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/internal/users")
@RequiredArgsConstructor
public class InternalUserQueryController {

    private final UserQueryService userQueryService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getUserProfile(@PathVariable String userId) {
        // userId is treated as email in this context
        UserProfileResponse userProfileResponse = userQueryService.getUserProfile(userId);
        return ResponseEntity.ok(ApiResponse.success(userProfileResponse));
    }
}
