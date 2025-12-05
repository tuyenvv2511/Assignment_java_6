package com.example.demo_java6_asm.service.impl;

import com.example.demo_java6_asm.dto.request.KhachHangRequest;
import com.example.demo_java6_asm.dto.response.KhachHangResponse;
import com.example.demo_java6_asm.entity.KhachHang;
import com.example.demo_java6_asm.exception.ResourceNotFoundException;
import com.example.demo_java6_asm.repository.KhachHangRepository;
import com.example.demo_java6_asm.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class KhachHangServiceImpl implements KhachHangService {

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Override
    public org.springframework.data.domain.Page<KhachHangResponse> getAll(
            org.springframework.data.domain.Pageable pageable) {
        return khachHangRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    public org.springframework.data.domain.Page<KhachHangResponse> search(String keyword,
            org.springframework.data.domain.Pageable pageable) {
        org.springframework.data.jpa.domain.Specification<com.example.demo_java6_asm.entity.KhachHang> spec = (root,
                query, cb) -> {
            if (keyword == null || keyword.isEmpty()) {
                return null;
            }
            String likePattern = "%" + keyword + "%";
            return cb.or(
                    cb.like(root.get("ten"), likePattern),
                    cb.like(root.get("ma"), likePattern),
                    cb.like(root.get("sdt"), likePattern));
        };
        return khachHangRepository.findAll(spec, pageable).map(this::mapToResponse);
    }

    @Override
    public List<KhachHangResponse> getAll() {
        return khachHangRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public KhachHangResponse getById(Integer id) {
        KhachHang khachHang = khachHangRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Khách hàng không tồn tại với id: " + id));
        return mapToResponse(khachHang);
    }

    @Override
    public KhachHangResponse create(KhachHangRequest request) {
        KhachHang khachHang = new KhachHang();
        mapRequestToEntity(request, khachHang);
        KhachHang savedKhachHang = khachHangRepository.save(khachHang);
        return mapToResponse(savedKhachHang);
    }

    @Override
    public KhachHangResponse update(Integer id, KhachHangRequest request) {
        KhachHang khachHang = khachHangRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Khách hàng không tồn tại với id: " + id));
        mapRequestToEntity(request, khachHang);
        KhachHang updatedKhachHang = khachHangRepository.save(khachHang);
        return mapToResponse(updatedKhachHang);
    }

    @Override
    public void delete(Integer id) {
        if (!khachHangRepository.existsById(id)) {
            throw new ResourceNotFoundException("Khách hàng không tồn tại với id: " + id);
        }
        khachHangRepository.deleteById(id);
    }

    private void mapRequestToEntity(KhachHangRequest request, KhachHang khachHang) {
        khachHang.setMa(request.getMa());
        khachHang.setTen(request.getTen());
        khachHang.setTenDem(request.getTenDem());
        khachHang.setHo(request.getHo());
        khachHang.setNgaySinh(request.getNgaySinh());
        khachHang.setSdt(request.getSdt());
        khachHang.setDiaChi(request.getDiaChi());
        khachHang.setThanhPho(request.getThanhPho());
        khachHang.setQuocGia(request.getQuocGia());
        khachHang.setMatKhau(request.getMatKhau());
    }

    private KhachHangResponse mapToResponse(KhachHang khachHang) {
        return new KhachHangResponse(
                khachHang.getId(),
                khachHang.getMa(),
                khachHang.getTen(),
                khachHang.getTenDem(),
                khachHang.getHo(),
                khachHang.getNgaySinh(),
                khachHang.getSdt(),
                khachHang.getDiaChi(),
                khachHang.getThanhPho(),
                khachHang.getQuocGia(),
                khachHang.getMatKhau());
    }
}
