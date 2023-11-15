package hainb21127.poly.appfastfood_admin.DTO;

public class Chat {
    String Id;
    KhachHang id_user;

    public Chat() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public KhachHang getId_user() {
        return id_user;
    }

    public void setId_user(KhachHang id_user) {
        this.id_user = id_user;
    }
}
