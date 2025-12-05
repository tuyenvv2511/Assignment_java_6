package com.example.demo_java6_asm.service;

import com.example.demo_java6_asm.dto.request.KhachHangRequest;
import com.example.demo_java6_asm.dto.response.KhachHangResponse;

import java.util.List;

public interface KhachHangService {
    org.springframework.data.domain.Page<KhachHangResponse> getAll(org.springframework.data.domain.Pageable pageable);

    org.springframework.data.domain.Page<KhachHangResponse> search(String keyword,
            org.springframework.data.domain.Pageable pageable);

    List<KhachHangResponse> getAll();

    KhachHangResponse getById(Integer id);

    KhachHangResponse create(KhachHangRequest request);

    KhachHangResponse update(Integer id, KhachHangRequest request);

    void delete(Integer id);
}
