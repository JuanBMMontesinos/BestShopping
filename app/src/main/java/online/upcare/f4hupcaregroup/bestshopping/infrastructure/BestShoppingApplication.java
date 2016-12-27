package online.upcare.f4hupcaregroup.bestshopping.infrastructure;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.firebase.client.Firebase;
import com.squareup.otto.Bus;

import online.upcare.f4hupcaregroup.bestshopping.live.Module;

public class BestShoppingApplication extends Application{
    private Bus bus;

    public BestShoppingApplication(){
        bus = new Bus();
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);
        Module.Register(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

    public Bus getBus(){
        return bus;
    }
}
