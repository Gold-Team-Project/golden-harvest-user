package com.teamgold.goldenharvestuser.domain.user.command.application.controller;

import com.teamgold.goldenharvestuser.common.response.ApiResponse;
import com.teamgold.goldenharvestuser.domain.user.command.application.dto.request.PasswordChangeRequest;
import com.teamgold.goldenharvestuser.domain.user.command.application.dto.request.UserProfileUpdateRequest;
import com.teamgold.goldenharvestuser.domain.user.command.application.dto.request.UserUpdateRequest;
import com.teamgold.goldenharvestuser.domain.user.command.application.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 마이페이지 비밀번호 변경
    @PatchMapping("/password")
    public ResponseEntity<ApiResponse<String>> changePassword(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody @Valid PasswordChangeRequest request) {

        userService.changePassword(jwt.getSubject(), request);
        return ResponseEntity.ok(ApiResponse.success("비밀번호가 성공적으로 변경되었습니다."));
    }

    // 회원 정보 수정
    @PatchMapping("/profile")
    public ResponseEntity<ApiResponse<String>> updateAddress(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody UserProfileUpdateRequest userProfileUpdateRequest) {

        userService.updateProfile(jwt.getSubject(), userProfileUpdateRequest);

        return ResponseEntity.ok(ApiResponse.success("회원 정보가 성공적으로 수정되었습니다."));
    }

    // 사업자 정보 수정 요청
    @PostMapping(value = "/business-update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<String>> requestBusinessUpdate(
            @AuthenticationPrincipal Jwt jwt,
            @RequestPart("data") @Valid UserUpdateRequest userUpdateRequest,
            @RequestPart("file") MultipartFile file
    ) throws IOException {
        userService.requestBusinessUpdate(jwt.getSubject(), userUpdateRequest, file);

        return ResponseEntity.ok(ApiResponse.success("사업자 정보 수정 요청이 성공적으로 접수되었습니다."));
    }
}