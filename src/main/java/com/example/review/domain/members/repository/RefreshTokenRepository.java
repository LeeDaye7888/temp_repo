package com.example.review.domain.members.repository;

import com.example.review.domain.members.entity.Member;
import com.example.review.domain.members.entity.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMember(Member member);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
