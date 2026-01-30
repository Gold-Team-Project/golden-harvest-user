package com.teamgold.goldenharvestuser.domain.user.command.application.controller;

import com.teamgold.goldenharvestuser.common.response.ApiResponse;
import com.teamgold.goldenharvestuser.domain.user.command.application.dto.request.UserApproveRequest;
import com.teamgold.goldenharvestuser.domain.user.command.application.service.AdminUserCommandService;
import com.teamgold.goldenharvestuser.domain.user.command.domain.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/user")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserCommandService adminUserCommandService;

    // 신규 가입 승인
    @PatchMapping("/{email}/approve")
    public ResponseEntity<ApiResponse<Void>> approveUser(
            @PathVariable String email,
            @RequestBody UserApproveRequest request) {

        adminUserCommandService.approveUser(email, request.getUserStatus());
        return ResponseEntity.ok(ApiResponse.success(null));
    }
    // 정보 수정 승인
    @PatchMapping("/update-requests/{requestId}/approve")
    public ResponseEntity<ApiResponse<Void>> approveProfileUpdate(@PathVariable Long requestId) {
        adminUserCommandService.approveProfileUpdate(requestId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PatchMapping("/{targetEmail}/status")
    public ResponseEntity<ApiResponse<Void>> updateUserStatus(
            @PathVariable String targetEmail,
            @RequestParam UserStatus newStatus) {

        // 임시 하드코딩 (게이트웨이 완성 전)
        String adminEmail = "admin@goldenharvest.com";

        adminUserCommandService.updateUserStatus(targetEmail, newStatus, adminEmail);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PatchMapping("/{targetEmail}/role")
    public ResponseEntity<ApiResponse<Void>> updateUserRole(
            @PathVariable String targetEmail,
            @RequestParam String newRole) {

        String adminEmail = "admin@goldenharvest.com";

        adminUserCommandService.updateUserRole(targetEmail, newRole, adminEmail);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

	@PostMapping("/publish/all")
	public ResponseEntity<ApiResponse<?>> publishUserDetails() {
		adminUserCommandService.publishAllUserDetailsEvent();
		return ResponseEntity.ok(ApiResponse.success(null));
	}
}
