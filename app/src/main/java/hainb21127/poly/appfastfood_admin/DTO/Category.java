package hainb21127.poly.appfastfood_admin.DTO;

public class Category {
    String id;
    String imageCat;
    String nameCat;
    public Category() {
    }

    public Category(String id, String imageCat, String nameCat) {
        this.id = id;
        this.imageCat = imageCat;
        this.nameCat = nameCat;
    }

    public Category(String imageCat, String nameCat) {
        this.imageCat = imageCat;
        this.nameCat = nameCat;
    }

    public String getImageCat() {
        return imageCat;
    }

    public void setImageCat(String imageCat) {
        this.imageCat = imageCat;
    }

    public String getNameCat() {
        return nameCat;
    }

    public void setNameCat(String nameCat) {
        this.nameCat = nameCat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}


