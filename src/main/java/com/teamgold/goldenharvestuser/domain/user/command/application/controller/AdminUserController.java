package com.teamgold.goldenharvestuser.domain.user.command.application.controller;

import com.teamgold.goldenharvestuser.common.response.ApiResponse;
import com.teamgold.goldenharvestuser.domain.user.command.application.dto.request.UserApproveRequest;
import com.teamgold.goldenharvestuser.domain.user.command.application.service.AdminUserCommandService;
import com.teamgold.goldenharvestuser.domain.user.command.domain.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/user")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserCommandService adminUserCommandService;

    // 신규 가입 승인
    @PatchMapping("/{email}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> approveUser(
            @PathVariable String email,
            @RequestBody UserApproveRequest request) {

        adminUserCommandService.approveUser(email, request.getUserStatus());
        return ResponseEntity.ok(ApiResponse.success(null));
    }
    // 정보 수정 승인
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update-requests/{requestId}/approve")
    public ResponseEntity<ApiResponse<Void>> approveProfileUpdate(@PathVariable Long requestId) {
        adminUserCommandService.approveProfileUpdate(requestId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PatchMapping("/{targetEmail}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> updateUserStatus(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable String targetEmail,
            @RequestParam UserStatus newStatus) {

        adminUserCommandService.updateUserStatus(targetEmail, newStatus, jwt.getSubject());

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PatchMapping("/{targetEmail}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> updateUserRole(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable String targetEmail,
            @RequestParam String newRole) {

        adminUserCommandService.updateUserRole(targetEmail, newRole, jwt.getSubject());

        return ResponseEntity.ok(ApiResponse.success(null));
    }

	@PostMapping("/publish/all")
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<?>> publishUserDetails() {
		adminUserCommandService.publishAllUserDetailsEvent();
		return ResponseEntity.ok(ApiResponse.success(null));
	}
}
