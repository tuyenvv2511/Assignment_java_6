package com.example.demo_java6_asm.service.impl;

import com.example.demo_java6_asm.dto.request.SanPhamRequest;
import com.example.demo_java6_asm.dto.response.SanPhamResponse;
import com.example.demo_java6_asm.entity.SanPham;
import com.example.demo_java6_asm.exception.ResourceNotFoundException;
import com.example.demo_java6_asm.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SanPhamServiceImpl implements SanPhamService {

    @Autowired
    private com.example.demo_java6_asm.repository.SanPhamRepository sanPhamRepository;
    @Autowired
    private com.example.demo_java6_asm.repository.ChiTietSPRepository chiTietSPRepository;
    @Autowired
    private com.example.demo_java6_asm.repository.MauSacRepository mauSacRepository;

    @Override
    public List<SanPhamResponse> getAll() {
        return sanPhamRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SanPhamResponse getById(Integer id) {
        SanPham sanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại với id: " + id));
        return mapToResponse(sanPham);
    }

    @Override
    public SanPhamResponse create(SanPhamRequest request) {
        SanPham sanPham = new SanPham();
        sanPham.setMa(request.getMa());
        sanPham.setTen(request.getTen());
        SanPham savedSanPham = sanPhamRepository.save(sanPham);

        if (request.getVariants() != null) {
            for (com.example.demo_java6_asm.dto.request.ChiTietSPRequest variantReq : request.getVariants()) {
                com.example.demo_java6_asm.entity.ChiTietSP chiTietSP = new com.example.demo_java6_asm.entity.ChiTietSP();
                chiTietSP.setSanPham(savedSanPham);
                chiTietSP.setGiaBan(variantReq.getGiaBan());
                chiTietSP.setSoLuongTon(variantReq.getSoLuong());

                // Handle MauSac
                if (variantReq.getMauSac() != null) {
                    com.example.demo_java6_asm.entity.MauSac mauSac = mauSacRepository.findByTen(variantReq.getMauSac())
                            .orElseGet(() -> {
                                com.example.demo_java6_asm.entity.MauSac newMs = new com.example.demo_java6_asm.entity.MauSac();
                                newMs.setTen(variantReq.getMauSac());
                                newMs.setMa("MS" + System.currentTimeMillis()); // Simple code generation
                                return mauSacRepository.save(newMs);
                            });
                    chiTietSP.setMauSac(mauSac);
                }

                chiTietSPRepository.save(chiTietSP);
            }
        }

        return mapToResponse(savedSanPham);
    }

    @Override
    public SanPhamResponse update(Integer id, SanPhamRequest request) {
        SanPham sanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại với id: " + id));
        sanPham.setMa(request.getMa());
        sanPham.setTen(request.getTen());
        SanPham updatedSanPham = sanPhamRepository.save(sanPham);
        return mapToResponse(updatedSanPham);
    }

    @Override
    public void delete(Integer id) {
        if (!sanPhamRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sản phẩm không tồn tại với id: " + id);
        }
        sanPhamRepository.deleteById(id);
    }

    @Override
    public org.springframework.data.domain.Page<SanPhamResponse> search(String keyword,
            org.springframework.data.domain.Pageable pageable) {
        org.springframework.data.jpa.domain.Specification<SanPham> spec = (root, query, cb) -> {
            if (keyword == null || keyword.isEmpty()) {
                return null;
            }
            String likePattern = "%" + keyword + "%";
            return cb.or(
                    cb.like(root.get("ten"), likePattern),
                    cb.like(root.get("ma"), likePattern));
        };
        return sanPhamRepository.findAll(spec, pageable).map(this::mapToResponse);
    }

    private SanPhamResponse mapToResponse(SanPham sanPham) {
        List<com.example.demo_java6_asm.entity.ChiTietSP> variants = chiTietSPRepository
                .findBySanPhamId(sanPham.getId());
        List<com.example.demo_java6_asm.dto.response.ChiTietSPResponse> variantResponses = variants.stream()
                .map(v -> new com.example.demo_java6_asm.dto.response.ChiTietSPResponse(
                        v.getId(),
                        v.getMauSac() != null ? v.getMauSac().getTen() : null,
                        v.getGiaBan(),
                        v.getSoLuongTon()))
                .collect(Collectors.toList());
        return new SanPhamResponse(sanPham.getId(), sanPham.getMa(), sanPham.getTen(), variantResponses);
    }
}
