package com.example.demo_java6_asm.entity;

import java.io.Serializable;
import java.util.Objects;

public class GioHangChiTietId implements Serializable {
    private Integer idGioHang;
    private Integer idChiTietSP;

    public GioHangChiTietId() {
    }

    public GioHangChiTietId(Integer idGioHang, Integer idChiTietSP) {
        this.idGioHang = idGioHang;
        this.idChiTietSP = idChiTietSP;
    }

    public Integer getIdGioHang() {
        return idGioHang;
    }

    public void setIdGioHang(Integer idGioHang) {
        this.idGioHang = idGioHang;
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
        GioHangChiTietId that = (GioHangChiTietId) o;
        return Objects.equals(idGioHang, that.idGioHang) && Objects.equals(idChiTietSP, that.idChiTietSP);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGioHang, idChiTietSP);
    }
}
