package online.upcare.f4hupcaregroup.bestshopping.dialog;

import android.app.DialogFragment;
import android.os.Bundle;

import com.squareup.otto.Bus;

import online.upcare.f4hupcaregroup.bestshopping.infrastructure.BestShoppingApplication;

public class BaseDialog extends DialogFragment{
    protected BestShoppingApplication application;
    protected Bus bus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (BestShoppingApplication) getActivity().getApplication();
        bus = application.getBus();
        bus.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }
}
