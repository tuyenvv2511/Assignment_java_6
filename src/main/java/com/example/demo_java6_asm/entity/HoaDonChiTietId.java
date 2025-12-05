package com.example.demo_java6_asm.entity;

import java.io.Serializable;
import java.util.Objects;

public class HoaDonChiTietId implements Serializable {
    private Integer idHoaDon;
    private Integer idChiTietSP;

    public HoaDonChiTietId() {
    }

    public HoaDonChiTietId(Integer idHoaDon, Integer idChiTietSP) {
        this.idHoaDon = idHoaDon;
        this.idChiTietSP = idChiTietSP;
    }

    public Integer getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(Integer idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public Integer getIdChiTietSP() {
        return idChiTietSP;
    }

    public void setIdChiTietSP(Integer idChiTietSP) {
        this.idChiTietSP = idChiTietSP;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        HoaDonChiTietId that = (HoaDonChiTietId) o;
        return Objects.equals(idHoaDon, that.idHoaDon) && Objects.equals(idChiTietSP, that.idChiTietSP);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idHoaDon, idChiTietSP);
    }
}
