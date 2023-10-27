package hainb21127.poly.appfastfood_admin.DTO;

public class User {

    String email;
    String name;
    String phone;
    int level;

    String passwd;
    String image;


    public User() {
    }

    public User(String email, String name, String phone, int level, String passwd, String image) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.level = level;
        this.passwd = passwd;
        this.image = image;
    }

    public User(String name, String phone, int level, String image, String email) {
        this.name = name;
        this.phone = phone;
        this.level = level;
        this.image = image;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
