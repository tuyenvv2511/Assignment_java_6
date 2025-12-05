package com.example.demo_java6_asm.dto.response;

import java.math.BigDecimal;

public class ChiTietSPResponse {
    private Integer id;
    private String mauSac;
    private BigDecimal giaBan;
    private Integer soLuongTon;

    public ChiTietSPResponse() {
    }

    public ChiTietSPResponse(Integer id, String mauSac, BigDecimal giaBan, Integer soLuongTon) {
        this.id = id;
        this.mauSac = mauSac;
        this.giaBan = giaBan;
        this.soLuongTon = soLuongTon;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(Integer soLuongTon) {
        this.soLuongTon = soLuongTon;
    }
}
