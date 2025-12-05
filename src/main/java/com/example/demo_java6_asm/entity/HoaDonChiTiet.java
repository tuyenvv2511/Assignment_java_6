package com.example.demo_java6_asm.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "HoaDonChiTiet")
public class HoaDonChiTiet {
    @EmbeddedId
    private HoaDonChiTietId id;

    @ManyToOne
    @MapsId("idHoaDon")
    @JoinColumn(name = "IdHoaDon")
    private HoaDon hoaDon;

    @ManyToOne
    @MapsId("idChiTietSP")
    @JoinColumn(name = "IdChiTietSP")
    private ChiTietSP chiTietSP;

    @Column(name = "SoLuong")
    private Integer soLuong;

    @Column(name = "DonGia", precision = 20, scale = 0)
    private BigDecimal donGia;

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(HoaDonChiTietId id, HoaDon hoaDon, ChiTietSP chiTietSP, Integer soLuong, BigDecimal donGia) {
        this.id = id;
        this.hoaDon = hoaDon;
        this.chiTietSP = chiTietSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public HoaDonChiTietId getId() {
        return id;
    }

    public void setId(HoaDonChiTietId id) {
        this.id = id;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
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
}
