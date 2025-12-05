package com.example.demo_java6_asm.repository;

import com.example.demo_java6_asm.entity.ChiTietSP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietSPRepository extends JpaRepository<ChiTietSP, Integer> {
    java.util.List<ChiTietSP> findBySanPhamId(Integer sanPhamId);
}
