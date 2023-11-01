package hainb21127.poly.appfastfood_admin.DTO;

public class Cart {
    String date;
    String name;
    String address;
    String phone;
    String status;
    int Tong;

    public Cart() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
