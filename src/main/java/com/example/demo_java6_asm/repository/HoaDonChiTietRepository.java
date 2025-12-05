package com.example.demo_java6_asm.repository;

import com.example.demo_java6_asm.entity.HoaDonChiTiet;
import com.example.demo_java6_asm.entity.HoaDonChiTietId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, HoaDonChiTietId> {
    List<HoaDonChiTiet> findByHoaDonId(Integer hoaDonId);
}
