package com.example.demo_java6_asm.service.impl;

import com.example.demo_java6_asm.dto.request.CuaHangRequest;
import com.example.demo_java6_asm.dto.response.CuaHangResponse;
import com.example.demo_java6_asm.entity.CuaHang;
import com.example.demo_java6_asm.exception.ResourceNotFoundException;
import com.example.demo_java6_asm.repository.CuaHangRepository;
import com.example.demo_java6_asm.service.CuaHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class CuaHangServiceImpl implements CuaHangService {

    @Autowired
    private CuaHangRepository cuaHangRepository;

    @Override
    public List<CuaHangResponse> getAll() {
        return cuaHangRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CuaHangResponse getById(Integer id) {
        CuaHang cuaHang = cuaHangRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cửa hàng không tồn tại với id: " + id));
        return mapToResponse(cuaHang);
    }

    @Override
    public CuaHangResponse create(CuaHangRequest request) {
        CuaHang cuaHang = new CuaHang();
        cuaHang.setMa(request.getMa());
        cuaHang.setTen(request.getTen());
        cuaHang.setDiaChi(request.getDiaChi());
        cuaHang.setThanhPho(request.getThanhPho());
        cuaHang.setQuocGia(request.getQuocGia());
        CuaHang savedCuaHang = cuaHangRepository.save(cuaHang);
        return mapToResponse(savedCuaHang);
    }

    @Override
    public CuaHangResponse update(Integer id, CuaHangRequest request) {
        CuaHang cuaHang = cuaHangRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cửa hàng không tồn tại với id: " + id));
        cuaHang.setMa(request.getMa());
        cuaHang.setTen(request.getTen());
        cuaHang.setDiaChi(request.getDiaChi());
        cuaHang.setThanhPho(request.getThanhPho());
        cuaHang.setQuocGia(request.getQuocGia());
        CuaHang updatedCuaHang = cuaHangRepository.save(cuaHang);
        return mapToResponse(updatedCuaHang);
    }

    @Override
    public void delete(Integer id) {
        if (!cuaHangRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cửa hàng không tồn tại với id: " + id);
        }
        cuaHangRepository.deleteById(id);
    }

    private CuaHangResponse mapToResponse(CuaHang cuaHang) {
        return new CuaHangResponse(
                cuaHang.getId(),
                cuaHang.getMa(),
                cuaHang.getTen(),
                cuaHang.getDiaChi(),
                cuaHang.getThanhPho(),
                cuaHang.getQuocGia());
    }
}
