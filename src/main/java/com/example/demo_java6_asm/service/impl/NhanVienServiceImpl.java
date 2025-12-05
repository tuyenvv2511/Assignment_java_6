package com.example.demo_java6_asm.service.impl;

import com.example.demo_java6_asm.dto.request.NhanVienRequest;
import com.example.demo_java6_asm.dto.response.NhanVienResponse;
import com.example.demo_java6_asm.entity.ChucVu;
import com.example.demo_java6_asm.entity.CuaHang;
import com.example.demo_java6_asm.entity.NhanVien;
import com.example.demo_java6_asm.exception.ResourceNotFoundException;
import com.example.demo_java6_asm.repository.ChucVuRepository;
import com.example.demo_java6_asm.repository.CuaHangRepository;
import com.example.demo_java6_asm.repository.NhanVienRepository;
import com.example.demo_java6_asm.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class NhanVienServiceImpl implements NhanVienService {

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private CuaHangRepository cuaHangRepository;

    @Autowired
    private ChucVuRepository chucVuRepository;

    @Override
    public org.springframework.data.domain.Page<NhanVienResponse> getAll(
            org.springframework.data.domain.Pageable pageable) {
        return nhanVienRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    public org.springframework.data.domain.Page<NhanVienResponse> search(String keyword,
            org.springframework.data.domain.Pageable pageable) {
        org.springframework.data.jpa.domain.Specification<com.example.demo_java6_asm.entity.NhanVien> spec = (root,
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
        return nhanVienRepository.findAll(spec, pageable).map(this::mapToResponse);
    }

    @Override
    public List<NhanVienResponse> getAll() {
        return nhanVienRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public NhanVienResponse getById(Integer id) {
        NhanVien nhanVien = nhanVienRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nhân viên không tồn tại với id: " + id));
        return mapToResponse(nhanVien);
    }

    @Override
    public NhanVienResponse create(NhanVienRequest request) {
        NhanVien nhanVien = new NhanVien();
        mapRequestToEntity(request, nhanVien);
        NhanVien savedNhanVien = nhanVienRepository.save(nhanVien);
        return mapToResponse(savedNhanVien);
    }

    @Override
    public NhanVienResponse update(Integer id, NhanVienRequest request) {
        NhanVien nhanVien = nhanVienRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nhân viên không tồn tại với id: " + id));
        mapRequestToEntity(request, nhanVien);
        NhanVien updatedNhanVien = nhanVienRepository.save(nhanVien);
        return mapToResponse(updatedNhanVien);
    }

    @Override
    public void delete(Integer id) {
        if (!nhanVienRepository.existsById(id)) {
            throw new ResourceNotFoundException("Nhân viên không tồn tại với id: " + id);
        }
        nhanVienRepository.deleteById(id);
    }

    private void mapRequestToEntity(NhanVienRequest request, NhanVien nhanVien) {
        nhanVien.setMa(request.getMa());
        nhanVien.setTen(request.getTen());
        nhanVien.setTenDem(request.getTenDem());
        nhanVien.setHo(request.getHo());
        nhanVien.setGioiTinh(request.getGioiTinh());
        nhanVien.setNgaySinh(request.getNgaySinh());
        nhanVien.setDiaChi(request.getDiaChi());
        nhanVien.setSdt(request.getSdt());
        nhanVien.setMatKhau(request.getMatKhau());
        nhanVien.setTrangThai(request.getTrangThai());

        if (request.getIdCH() != null) {
            CuaHang cuaHang = cuaHangRepository.findById(request.getIdCH())
                    .orElseThrow(() -> new ResourceNotFoundException("Cửa hàng không tồn tại"));
            nhanVien.setCuaHang(cuaHang);
        }

        if (request.getIdCV() != null) {
            ChucVu chucVu = chucVuRepository.findById(request.getIdCV())
                    .orElseThrow(() -> new ResourceNotFoundException("Chức vụ không tồn tại"));
            nhanVien.setChucVu(chucVu);
        }

        if (request.getIdGuiBC() != null) {
            NhanVien guiBaoCao = nhanVienRepository.findById(request.getIdGuiBC())
                    .orElseThrow(() -> new ResourceNotFoundException("Người gửi báo cáo không tồn tại"));
            nhanVien.setGuiBaoCao(guiBaoCao);
        }
    }

    private NhanVienResponse mapToResponse(NhanVien nhanVien) {
        return new NhanVienResponse(
                nhanVien.getId(),
                nhanVien.getMa(),
                nhanVien.getTen(),
                nhanVien.getTenDem(),
                nhanVien.getHo(),
                nhanVien.getGioiTinh(),
                nhanVien.getNgaySinh(),
                nhanVien.getDiaChi(),
                nhanVien.getSdt(),
                nhanVien.getMatKhau(),
                nhanVien.getCuaHang() != null ? nhanVien.getCuaHang().getId() : null,
                nhanVien.getChucVu() != null ? nhanVien.getChucVu().getId() : null,
                nhanVien.getGuiBaoCao() != null ? nhanVien.getGuiBaoCao().getId() : null,
                nhanVien.getTrangThai());
    }
}
