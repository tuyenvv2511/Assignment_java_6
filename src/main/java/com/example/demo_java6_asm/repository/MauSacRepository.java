package com.example.demo_java6_asm.repository;

import com.example.demo_java6_asm.entity.MauSac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac, Integer> {
    Optional<MauSac> findByTen(String ten);
}
