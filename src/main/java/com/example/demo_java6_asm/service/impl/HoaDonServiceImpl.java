package com.example.demo_java6_asm.service.impl;

import com.example.demo_java6_asm.dto.request.HoaDonRequest;
import com.example.demo_java6_asm.dto.request.HoaDonChiTietRequest;
import com.example.demo_java6_asm.dto.response.HoaDonResponse;
import com.example.demo_java6_asm.dto.response.HoaDonChiTietResponse;
import com.example.demo_java6_asm.entity.*;
import com.example.demo_java6_asm.exception.ResourceNotFoundException;
import com.example.demo_java6_asm.repository.*;
import com.example.demo_java6_asm.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HoaDonServiceImpl implements HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private ChiTietSPRepository chiTietSPRepository;

    @Override
    public Page<HoaDonResponse> getAll(Pageable pageable) {
        return hoaDonRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    public Page<HoaDonResponse> search(String keyword, Pageable pageable) {
        org.springframework.data.jpa.domain.Specification<HoaDon> spec = (root, query, cb) -> {
            if (keyword == null || keyword.isEmpty()) {
                return null;
            }
            String likePattern = "%" + keyword + "%";
            return cb.or(
                    cb.like(root.get("ma"), likePattern),
                    cb.like(root.get("tenNguoiNhan"), likePattern),
                    cb.like(root.get("sdt"), likePattern));
        };
        return hoaDonRepository.findAll(spec, pageable).map(this::mapToResponse);
    }

    @Override
    public HoaDonResponse getById(Integer id) {
        HoaDon hoaDon = hoaDonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hóa đơn không tồn tại"));
        return mapToResponse(hoaDon);
    }

    @Override
    @Transactional
    public HoaDonResponse create(HoaDonRequest request) {
        HoaDon hoaDon = new HoaDon();
        mapRequestToEntity(request, hoaDon);
        HoaDon savedHoaDon = hoaDonRepository.save(hoaDon);

        if (request.getItems() != null) {
            for (HoaDonChiTietRequest itemReq : request.getItems()) {
                ChiTietSP chiTietSP = chiTietSPRepository.findById(itemReq.getIdChiTietSP())
                        .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại"));

                HoaDonChiTietId id = new HoaDonChiTietId(savedHoaDon.getId(), chiTietSP.getId());
                HoaDonChiTiet detail = new HoaDonChiTiet();
                detail.setId(id);
                detail.setHoaDon(savedHoaDon);
                detail.setChiTietSP(chiTietSP);
                detail.setSoLuong(itemReq.getSoLuong());
                detail.setDonGia(itemReq.getDonGia());

                hoaDonChiTietRepository.save(detail);
            }
        }

        return mapToResponse(savedHoaDon);
    }

    @Override
    @Transactional
    public HoaDonResponse update(Integer id, HoaDonRequest request) {
        HoaDon hoaDon = hoaDonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hóa đơn không tồn tại"));
        mapRequestToEntity(request, hoaDon);
        HoaDon updatedHoaDon = hoaDonRepository.save(hoaDon);
        return mapToResponse(updatedHoaDon);
    }

    @Override
    public void delete(Integer id) {
        if (!hoaDonRepository.existsById(id)) {
            throw new ResourceNotFoundException("Hóa đơn không tồn tại");
        }
        hoaDonRepository.deleteById(id);
    }

    private void mapRequestToEntity(HoaDonRequest request, HoaDon hoaDon) {
        hoaDon.setMa(request.getMa());
        hoaDon.setNgayTao(request.getNgayTao());
        hoaDon.setTinhTrang(request.getTinhTrang());
        hoaDon.setTenNguoiNhan(request.getTenNguoiNhan());
        hoaDon.setDiaChi(request.getDiaChi());
        hoaDon.setSdt(request.getSdt());

        if (request.getIdKH() != null) {
            KhachHang kh = khachHangRepository.findById(request.getIdKH()).orElse(null);
            hoaDon.setKhachHang(kh);
        }
        if (request.getIdNV() != null) {
            NhanVien nv = nhanVienRepository.findById(request.getIdNV()).orElse(null);
            hoaDon.setNhanVien(nv);
        }
    }

    private HoaDonResponse mapToResponse(HoaDon hoaDon) {
        List<HoaDonChiTiet> details = hoaDonChiTietRepository.findByHoaDonId(hoaDon.getId());
        List<HoaDonChiTietResponse> itemResponses = details.stream().map(d -> {
            BigDecimal thanhTien = d.getDonGia().multiply(BigDecimal.valueOf(d.getSoLuong()));
            return new HoaDonChiTietResponse(
                    d.getChiTietSP().getId(),
                    d.getChiTietSP().getSanPham().getTen(), // Assuming SanPham is fetched
                    d.getSoLuong(),
                    d.getDonGia(),
                    thanhTien);
        }).collect(Collectors.toList());

        BigDecimal tongTien = itemResponses.stream()
                .map(HoaDonChiTietResponse::getThanhTien)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new HoaDonResponse(
                hoaDon.getId(),
                hoaDon.getMa(),
                hoaDon.getKhachHang() != null ? hoaDon.getKhachHang().getTen() : null,
                hoaDon.getNhanVien() != null ? hoaDon.getNhanVien().getTen() : null,
                hoaDon.getNgayTao(),
                hoaDon.getTinhTrang(),
                tongTien,
                itemResponses);
    }
}
