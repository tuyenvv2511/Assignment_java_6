package com.example.demo_java6_asm.dto.response;

import java.math.BigDecimal;

public class HoaDonChiTietResponse {
    private Integer idChiTietSP;
    private String tenSP;
    private Integer soLuong;
    private BigDecimal donGia;
    private BigDecimal thanhTien;

    public HoaDonChiTietResponse() {
    }

    public HoaDonChiTietResponse(Integer idChiTietSP, String tenSP, Integer soLuong, BigDecimal donGia,
            BigDecimal thanhTien) {
        this.idChiTietSP = idChiTietSP;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
    }

    public Integer getIdChiTietSP() {
        return idChiTietSP;
    }

    public void setIdChiTietSP(Integer idChiTietSP) {
        this.idChiTietSP = idChiTietSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }

    public BigDecimal getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(BigDecimal thanhTien) {
        this.thanhTien = thanhTien;
    }
}
