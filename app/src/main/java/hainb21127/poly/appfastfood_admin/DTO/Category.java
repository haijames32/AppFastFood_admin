package hainb21127.poly.appfastfood_admin.DTO;

public class Category {
    String imageCat;
    String nameCat;
    public Category() {
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
}

