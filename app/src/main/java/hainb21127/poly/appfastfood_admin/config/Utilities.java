package hainb21127.poly.appfastfood_admin.config;

public class Utilities {
    public static String addDots(Number strr) {
        String str = strr.toString();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            if (i > 0 && i % 3 == str.length() % 3) {
                result.append(".");
            }
            result.append(str.charAt(i));
        }

        return result.toString();
    }

    public static String removeDots(String input) {
        return input.replace(".", "");
    }
}
