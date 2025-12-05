package com.example.demo_java6_asm.service;

import com.example.demo_java6_asm.dto.request.NhanVienRequest;
import com.example.demo_java6_asm.dto.response.NhanVienResponse;

import java.util.List;

public interface NhanVienService {
    org.springframework.data.domain.Page<NhanVienResponse> getAll(org.springframework.data.domain.Pageable pageable);

    org.springframework.data.domain.Page<NhanVienResponse> search(String keyword,
            org.springframework.data.domain.Pageable pageable);

    List<NhanVienResponse> getAll();

    NhanVienResponse getById(Integer id);

    NhanVienResponse create(NhanVienRequest request);

    NhanVienResponse update(Integer id, NhanVienRequest request);

    void delete(Integer id);
}
