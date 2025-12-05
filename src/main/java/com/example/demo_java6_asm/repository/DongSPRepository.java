package com.example.demo_java6_asm.repository;

import com.example.demo_java6_asm.entity.DongSP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DongSPRepository extends JpaRepository<DongSP, Integer> {
}
