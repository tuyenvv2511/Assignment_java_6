package com.example.demo_java6_asm.repository;

import com.example.demo_java6_asm.entity.NSX;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NSXRepository extends JpaRepository<NSX, Integer> {
}
