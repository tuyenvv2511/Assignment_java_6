package com.example.demo_java6_asm.repository;

import com.example.demo_java6_asm.entity.KichThuoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface KichThuocRepository extends JpaRepository<KichThuoc, Integer> {
    Optional<KichThuoc> findByTen(String ten);
}
