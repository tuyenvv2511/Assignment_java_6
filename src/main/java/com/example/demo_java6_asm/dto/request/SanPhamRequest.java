package com.example.demo_java6_asm.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

public class SanPhamRequest {
    @NotBlank(message = "Mã không được để trống")
    private String ma;

    @NotBlank(message = "Tên không được để trống")
    private String ten;

    private Integer soLuong;
    private BigDecimal giaBan;

    private List<ChiTietSPRequest> variants;

    public SanPhamRequest() {
    }

    public SanPhamRequest(String ma, String ten, Integer soLuong, BigDecimal giaBan,
            List<ChiTietSPRequest> variants) {
        this.ma = ma;
        this.ten = ten;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.variants = variants;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public List<ChiTietSPRequest> getVariants() {
        return variants;
    }

    public void setVariants(List<ChiTietSPRequest> variants) {
        this.variants = variants;
    }
}
