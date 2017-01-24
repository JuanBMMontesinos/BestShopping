package online.upcare.f4hupcaregroup.bestshopping.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import online.upcare.f4hupcaregroup.bestshopping.R;
import online.upcare.f4hupcaregroup.bestshopping.infrastructure.Utils;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String toolbarName;

        if(userName.contains(" ")) {
            toolbarName = userName.substring(0, userName.indexOf(" ")) + "'s Shopping List";
        } else {
            toolbarName = userName + "'s Shopping List";
        }

        getSupportActionBar().setTitle(toolbarName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_logout:
                SharedPreferences preferences = getSharedPreferences(Utils.PREFERENCE, Context.MODE_PRIVATE);
                //Utils.LogUserOut(getApplicationContext(), sharedPreferences, auth);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(Utils.EMAIL, null).apply();
                editor.putString(Utils.USERNAME, null).apply();
                auth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
