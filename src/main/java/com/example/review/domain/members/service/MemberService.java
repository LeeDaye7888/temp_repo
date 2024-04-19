package com.example.review.domain.members.service;

import com.example.review.domain.members.dto.MemberResponse;
import com.example.review.domain.members.dto.UpdateMemberPaswordRequest;
import java.util.List;
import org.springframework.security.core.userdetails.User;

public interface MemberService {
    MemberResponse getOne(User user);
    List<MemberResponse> getAll();
    void deleteUser(User user);
    void deleteSeller(User user);
    void updatePassword(User user, UpdateMemberPaswordRequest request);
}
