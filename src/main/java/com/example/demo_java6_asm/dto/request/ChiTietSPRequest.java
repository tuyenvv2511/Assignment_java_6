package com.example.demo_java6_asm.dto.request;

import java.math.BigDecimal;

public class ChiTietSPRequest {
    private String mauSac;
    private BigDecimal giaBan;
    private Integer soLuong;

    public ChiTietSPRequest() {
    }

    public ChiTietSPRequest(String mauSac, BigDecimal giaBan, Integer soLuong) {
        this.mauSac = mauSac;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }
}
