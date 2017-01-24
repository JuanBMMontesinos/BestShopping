package online.upcare.f4hupcaregroup.bestshopping.infrastructure;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;

import online.upcare.f4hupcaregroup.bestshopping.activities.LoginActivity;

public class Utils {
    public static final String FIREBASE_URL = "https://best-shopping-71004.firebaseio.com/";
    public static final String FIREBASE_USER_REFERENCE = FIREBASE_URL + "users/";

    public static final String PREFERENCE  = "PREFERENCE";
    public static final String EMAIL  = "EMAIL";
    public static final String USERNAME  = "USERNAME";

    public static String encodeEmail(String userEmail){
        return userEmail.replace(".",",");
    }

    public static void LogUserOut(Context context, SharedPreferences sharedPreferences, FirebaseAuth firebaseAuth){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Utils.EMAIL, null).apply();
        editor.putString(Utils.USERNAME, null).apply();
        firebaseAuth.signOut();

        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
