package com.example.demo_java6_asm.repository;

import com.example.demo_java6_asm.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Integer>,
        org.springframework.data.jpa.repository.JpaSpecificationExecutor<NhanVien> {
    Optional<NhanVien> findByMa(String ma);
}
