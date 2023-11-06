package hainb21127.poly.appfastfood_admin.DTO;

public class Order {
    String id;
    String date;
    KhachHang id_user;
    String status;
    int Tong;

    public Order() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public KhachHang getId_user() {
        return id_user;
    }

    public void setId_user(KhachHang id_user) {
        this.id_user = id_user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTong() {
        return Tong;
    }

    public void setTong(int tong) {
        Tong = tong;
    }
}
