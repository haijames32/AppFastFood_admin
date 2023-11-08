package hainb21127.poly.appfastfood_admin.DTO;

public class OrderItemDetail {
    String id;
    String id_order;
    Products id_products;
    int soluong;
    int tongTien;

    public OrderItemDetail() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_order() {
        return id_order;
    }

    public void setId_order(String id_order) {
        this.id_order = id_order;
    }

    public Products getId_products() {
        return id_products;
    }

    public void setId_products(Products id_products) {
        this.id_products = id_products;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
}
