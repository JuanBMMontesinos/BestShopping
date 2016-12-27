package online.upcare.f4hupcaregroup.bestshopping.infrastructure;

public class Utils {
    public static final String FIREBASE_URL = "https://best-shopping-71004.firebaseio.com/";
    public static final String FIREBASE_USER_REFERENCE = FIREBASE_URL + "users/";

    public static String encodeEmail(String userEmail){
        return userEmail.replace(".",",");
    }
}
