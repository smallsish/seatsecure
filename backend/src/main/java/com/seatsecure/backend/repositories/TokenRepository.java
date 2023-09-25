package com.seatsecure.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.seatsecure.backend.entities.Token;

public interface TokenRepository extends JpaRepository<Token, Long>{
    @Query(
            "Select t From Token t inner join User u on t.user.id = u.id where u.id = :userId and (t.expired = false or t.revoked = false)" 
    )
    List<Token> findAllValidTokensByUser(Long userId);

    Optional<Token> findByToken(String token);
}
