package com.teamgold.goldenharvestuser.domain.user.command.application.service;

import com.teamgold.goldenharvestuser.common.exception.BusinessException;
import com.teamgold.goldenharvestuser.common.exception.ErrorCode;
import com.teamgold.goldenharvestuser.domain.user.command.application.event.UserStatusUpdateEventPublisher;
import com.teamgold.goldenharvestuser.domain.user.command.application.event.dto.UserStatusUpdatedEvent;
import com.teamgold.goldenharvestuser.domain.user.command.domain.*;
import com.teamgold.goldenharvestuser.domain.user.command.infrastructure.repository.RoleRepository;
import com.teamgold.goldenharvestuser.domain.user.command.infrastructure.repository.UserRepository;
import com.teamgold.goldenharvestuser.domain.user.command.infrastructure.repository.UserUpdateApprovalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminUserCommandServiceImpl implements AdminUserCommandService {

    private final UserRepository userRepository;
    private final UserUpdateApprovalRepository userUpdateApprovalRepository;
    private final UserStatusUpdateEventPublisher userUpdateEventPublisher;
    private final RoleRepository roleRepository;

    @Override
    public void approveUser(String email, UserStatus newStatus) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (user.getStatus() != UserStatus.PENDING) {
            throw  new BusinessException(ErrorCode.USER_ONLY_PENDING_CAN_BE_APPROVED);
        }

        // newStatus가 ACTIVE면 승인, REJECTED(또는 INACTIVE)면 반려
        user.updateStatus(newStatus);
    }

    @Override
    @Transactional
    public void processProfileUpdate(Long approvalId, RequestStatus status) {
        // 1. "승인 대기 건(엔티티)"을 찾습니다.
        UserUpdateApproval approval = userUpdateApprovalRepository.findById(approvalId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_REQUEST));

        if (approval.getStatus() != RequestStatus.PENDING) {
            throw new BusinessException(ErrorCode.DUPLICATE_REQUEST);
        }

        // 2. 승인/반려 처리
        if (status == RequestStatus.APPROVED) {
            // 승인 로직
            approval.approve(); // 요청 엔티티 상태 변경 (APPROVED)

            // 실제 유저 엔티티 정보 업데이트
            User user = approval.getUser();
            user.updateBusinessInfo(
                    approval.getRequestCompany(),
                    approval.getRequestBusinessNumber(),
                    approval.getRequestFileUrl()
            );
        } else if (status == RequestStatus.REJECTED) {
            // 반려 로직
            approval.reject();
            // 반려 시에는 유저 정보(user.updateBusinessInfo)를 업데이트하지 않습니다.
        }
    }

    @Override
    @Transactional
    public void updateUserStatus(String targetEmail, UserStatus newStatus, String adminEmail) {
        // 본인 계정인지 확인
        if (targetEmail.equals(adminEmail)) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST);
        }

        User user = userRepository.findByEmail(targetEmail)
                .orElseThrow(()->new BusinessException(ErrorCode.USER_NOT_FOUND));

        user.updateStatus(newStatus);
    }

    @Override
    public void publishAllUserDetailsEvent() {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            userUpdateEventPublisher.publishUserStatusUpdatedEvent(
                    UserStatusUpdatedEvent.builder()
                            .phoneNumber(user.getPhoneNumber())
                            .postalCode(user.getPostalCode())
                            .addressLine1(user.getAddressLine1())
                            .addressLine2(user.getAddressLine2())
                            .businessNumber(user.getBusinessNumber())
                            .name(user.getName())
                            .company(user.getCompany())
                            .phoneNumber(user.getPhoneNumber())
                            .build()
            );
        }
    }

    @Override
    public void updateUserRole(String targetEmail, String newRole, String adminEmail) {
        // 1. 대상 유저 조회
        User user = userRepository.findByEmail(targetEmail)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // 2. 관리자 본인의 권한 변경 방지 (보안 로직)
        if (targetEmail.equals(adminEmail)) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST);
        }

        Role role = roleRepository.findById(newRole)
                .orElseThrow(() -> new BusinessException(ErrorCode.FORBIDDEN_ACCESS));

        user.updateRole(role);

        // @Transactional이 걸려있으므로 메서드 종료 시 자동 더티 체킹(save) 됩니다.
        log.info("Admin {} changed role of {} to {}", adminEmail, targetEmail, newRole);
    }
}

