package com.example.demo_java6_asm.service;

import com.example.demo_java6_asm.dto.request.SanPhamRequest;
import com.example.demo_java6_asm.dto.response.SanPhamResponse;

import java.util.List;

public interface SanPhamService {
    List<SanPhamResponse> getAll();

    SanPhamResponse getById(Integer id);

    SanPhamResponse create(SanPhamRequest request);

    SanPhamResponse update(Integer id, SanPhamRequest request);

    void delete(Integer id);

    org.springframework.data.domain.Page<SanPhamResponse> search(String keyword,
            org.springframework.data.domain.Pageable pageable);
}
