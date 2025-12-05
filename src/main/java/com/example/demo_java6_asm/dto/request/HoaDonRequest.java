package com.example.demo_java6_asm.dto.request;

import java.sql.Date;
import java.util.List;

public class HoaDonRequest {
    private Integer idKH;
    private Integer idNV;
    private String ma;
    private Date ngayTao;
    private Integer tinhTrang;
    private String tenNguoiNhan;
    private String diaChi;
    private String sdt;
    private List<HoaDonChiTietRequest> items;

    public HoaDonRequest() {
    }

    public HoaDonRequest(Integer idKH, Integer idNV, String ma, Date ngayTao, Integer tinhTrang, String tenNguoiNhan,
            String diaChi, String sdt, List<HoaDonChiTietRequest> items) {
        this.idKH = idKH;
        this.idNV = idNV;
        this.ma = ma;
        this.ngayTao = ngayTao;
        this.tinhTrang = tinhTrang;
        this.tenNguoiNhan = tenNguoiNhan;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.items = items;
    }

    public Integer getIdKH() {
        return idKH;
    }

    public void setIdKH(Integer idKH) {
        this.idKH = idKH;
    }

    public Integer getIdNV() {
        return idNV;
    }

    public void setIdNV(Integer idNV) {
        this.idNV = idNV;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
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

    public String getTenNguoiNhan() {
        return tenNguoiNhan;
    }

    public void setTenNguoiNhan(String tenNguoiNhan) {
        this.tenNguoiNhan = tenNguoiNhan;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public List<HoaDonChiTietRequest> getItems() {
        return items;
    }

    public void setItems(List<HoaDonChiTietRequest> items) {
        this.items = items;
    }
}
