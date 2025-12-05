package com.example.demo_java6_asm.service;

import com.example.demo_java6_asm.dto.request.CuaHangRequest;
import com.example.demo_java6_asm.dto.response.CuaHangResponse;

import java.util.List;

public interface CuaHangService {
    List<CuaHangResponse> getAll();

    CuaHangResponse getById(Integer id);

    CuaHangResponse create(CuaHangRequest request);

    CuaHangResponse update(Integer id, CuaHangRequest request);

    void delete(Integer id);
}
