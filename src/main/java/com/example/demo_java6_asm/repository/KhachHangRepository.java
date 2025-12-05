package com.example.demo_java6_asm.repository;

import com.example.demo_java6_asm.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer>,
        org.springframework.data.jpa.repository.JpaSpecificationExecutor<KhachHang> {
}
