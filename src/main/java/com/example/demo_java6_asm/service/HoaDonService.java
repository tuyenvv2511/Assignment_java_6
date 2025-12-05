package com.example.demo_java6_asm.service;

import com.example.demo_java6_asm.dto.request.HoaDonRequest;
import com.example.demo_java6_asm.dto.response.HoaDonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HoaDonService {
    Page<HoaDonResponse> getAll(Pageable pageable);

    Page<HoaDonResponse> search(String keyword, Pageable pageable);

    HoaDonResponse getById(Integer id);

    HoaDonResponse create(HoaDonRequest request);

    HoaDonResponse update(Integer id, HoaDonRequest request);

    void delete(Integer id);
}
