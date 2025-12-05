package com.example.demo_java6_asm.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "GioHangChiTiet")
public class GioHangChiTiet {
    @EmbeddedId
    private GioHangChiTietId id;

    @ManyToOne
    @MapsId("idGioHang")
    @JoinColumn(name = "IdGioHang")
    private GioHang gioHang;

    @ManyToOne
    @MapsId("idChiTietSP")
    @JoinColumn(name = "IdChiTietSP")
    private ChiTietSP chiTietSP;

    @Column(name = "SoLuong")
    private Integer soLuong;

    @Column(name = "DonGia", precision = 20, scale = 0)
    private BigDecimal donGia;

    @Column(name = "DonGiaKhiGiam", precision = 20, scale = 0)
    private BigDecimal donGiaKhiGiam;

    public GioHangChiTiet() {
    }

    public GioHangChiTiet(GioHangChiTietId id, GioHang gioHang, ChiTietSP chiTietSP, Integer soLuong, BigDecimal donGia,
            BigDecimal donGiaKhiGiam) {
        this.id = id;
        this.gioHang = gioHang;
        this.chiTietSP = chiTietSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.donGiaKhiGiam = donGiaKhiGiam;
    }

    public GioHangChiTietId getId() {
        return id;
    }

    public void setId(GioHangChiTietId id) {
        this.id = id;
    }

    public GioHang getGioHang() {
        return gioHang;
    }

    public void setGioHang(GioHang gioHang) {
        this.gioHang = gioHang;
    }

    public ChiTietSP getChiTietSP() {
        return chiTietSP;
    }

    public void setChiTietSP(ChiTietSP chiTietSP) {
        this.chiTietSP = chiTietSP;
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

    public BigDecimal getDonGiaKhiGiam() {
        return donGiaKhiGiam;
    }

    public void setDonGiaKhiGiam(BigDecimal donGiaKhiGiam) {
        this.donGiaKhiGiam = donGiaKhiGiam;
    }
}
