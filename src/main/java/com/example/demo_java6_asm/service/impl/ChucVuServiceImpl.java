package com.example.demo_java6_asm.service.impl;

import com.example.demo_java6_asm.dto.request.ChucVuRequest;
import com.example.demo_java6_asm.dto.response.ChucVuResponse;
import com.example.demo_java6_asm.entity.ChucVu;
import com.example.demo_java6_asm.exception.ResourceNotFoundException;
import com.example.demo_java6_asm.repository.ChucVuRepository;
import com.example.demo_java6_asm.service.ChucVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class ChucVuServiceImpl implements ChucVuService {

    @Autowired
    private ChucVuRepository chucVuRepository;

    @Override
    public List<ChucVuResponse> getAll() {
        return chucVuRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ChucVuResponse getById(Integer id) {
        ChucVu chucVu = chucVuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chức vụ không tồn tại với id: " + id));
        return mapToResponse(chucVu);
    }

    @Override
    public ChucVuResponse create(ChucVuRequest request) {
        ChucVu chucVu = new ChucVu();
        chucVu.setMa(request.getMa());
        chucVu.setTen(request.getTen());
        ChucVu savedChucVu = chucVuRepository.save(chucVu);
        return mapToResponse(savedChucVu);
    }

    @Override
    public ChucVuResponse update(Integer id, ChucVuRequest request) {
        ChucVu chucVu = chucVuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chức vụ không tồn tại với id: " + id));
        chucVu.setMa(request.getMa());
        chucVu.setTen(request.getTen());
        ChucVu updatedChucVu = chucVuRepository.save(chucVu);
        return mapToResponse(updatedChucVu);
    }

    @Override
    public void delete(Integer id) {
        if (!chucVuRepository.existsById(id)) {
            throw new ResourceNotFoundException("Chức vụ không tồn tại với id: " + id);
        }
        chucVuRepository.deleteById(id);
    }

    private ChucVuResponse mapToResponse(ChucVu chucVu) {
        return new ChucVuResponse(chucVu.getId(), chucVu.getMa(), chucVu.getTen());
    }
}
