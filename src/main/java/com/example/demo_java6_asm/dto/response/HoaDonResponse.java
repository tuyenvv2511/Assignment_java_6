package com.example.demo_java6_asm.dto.response;

import java.sql.Date;
import java.math.BigDecimal;
import java.util.List;

public class HoaDonResponse {
    private Integer id;
    private String ma;
    private String khachHang;
    private String nhanVien;
    private Date ngayTao;
    private Integer tinhTrang;
    private BigDecimal tongTien;
    private List<HoaDonChiTietResponse> items;

    public HoaDonResponse() {
    }

    public HoaDonResponse(Integer id, String ma, String khachHang, String nhanVien, Date ngayTao, Integer tinhTrang,
            BigDecimal tongTien, List<HoaDonChiTietResponse> items) {
        this.id = id;
        this.ma = ma;
        this.khachHang = khachHang;
        this.nhanVien = nhanVien;
        this.ngayTao = ngayTao;
        this.tinhTrang = tinhTrang;
        this.tongTien = tongTien;
        this.items = items;
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

    public String getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(String khachHang) {
        this.khachHang = khachHang;
    }

    public String getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(String nhanVien) {
        this.nhanVien = nhanVien;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Integer getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(Integer tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public List<HoaDonChiTietResponse> getItems() {
        return items;
    }

    public void setItems(List<HoaDonChiTietResponse> items) {
        this.items = items;
    }
}
