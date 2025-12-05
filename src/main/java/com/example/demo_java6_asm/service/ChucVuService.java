package com.example.demo_java6_asm.service;

import com.example.demo_java6_asm.dto.request.ChucVuRequest;
import com.example.demo_java6_asm.dto.response.ChucVuResponse;

import java.util.List;

public interface ChucVuService {
    List<ChucVuResponse> getAll();

    ChucVuResponse getById(Integer id);

    ChucVuResponse create(ChucVuRequest request);

    ChucVuResponse update(Integer id, ChucVuRequest request);

    void delete(Integer id);
}
