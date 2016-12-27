package online.upcare.f4hupcaregroup.bestshopping.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.squareup.otto.Bus;

import online.upcare.f4hupcaregroup.bestshopping.infrastructure.BestShoppingApplication;

public class BaseActivity extends AppCompatActivity{
    protected BestShoppingApplication application;
    protected Bus bus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (BestShoppingApplication) getApplication();
        bus = application.getBus();
        bus.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }
}
