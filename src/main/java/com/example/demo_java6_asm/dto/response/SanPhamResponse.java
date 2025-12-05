package com.example.demo_java6_asm.dto.response;

import java.util.List;

public class SanPhamResponse {
    private Integer id;
    private String ma;
    private String ten;
    private List<ChiTietSPResponse> variants;

    public SanPhamResponse() {
    }

    public SanPhamResponse(Integer id, String ma, String ten, List<ChiTietSPResponse> variants) {
        this.id = id;
        this.ma = ma;
        this.ten = ten;
        this.variants = variants;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<ChiTietSPResponse> getVariants() {
        return variants;
    }

    public void setVariants(List<ChiTietSPResponse> variants) {
        this.variants = variants;
    }
}
