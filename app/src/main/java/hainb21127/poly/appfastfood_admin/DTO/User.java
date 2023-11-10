package hainb21127.poly.appfastfood_admin.DTO;

public class User {
    String id;
    String email;
    String name;
    String phone;
    int level;

    String passwd;
    String image;

    public User() {
    }

    public User(String name, String phone, int level, String image, String email,String passwd) {
        this.name = name;
        this.phone = phone;
        this.level = level;
        this.image = image;
        this.email = email;
        this.passwd = passwd;
    }

//    public User( String name, String phone, int level, String image,String email) {
//        this.email = email;
//        this.name = name;
//        this.phone = phone;
//        this.level = level;
//        this.image = image;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
