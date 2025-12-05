package com.example.demo_java6_asm.dto.request;

import java.math.BigDecimal;

public class HoaDonChiTietRequest {
    private Integer idChiTietSP;
    private Integer soLuong;
    private BigDecimal donGia;

    public HoaDonChiTietRequest() {
    }

    public HoaDonChiTietRequest(Integer idChiTietSP, Integer soLuong, BigDecimal donGia) {
        this.idChiTietSP = idChiTietSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public Integer getIdChiTietSP() {
        return idChiTietSP;
    }

    public void setIdChiTietSP(Integer idChiTietSP) {
        this.idChiTietSP = idChiTietSP;
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
}
