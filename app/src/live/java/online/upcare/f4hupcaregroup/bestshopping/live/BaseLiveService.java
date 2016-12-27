package online.upcare.f4hupcaregroup.bestshopping.live;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.otto.Bus;

import online.upcare.f4hupcaregroup.bestshopping.infrastructure.BestShoppingApplication;


public class BaseLiveService {
    protected Bus bus;
    protected BestShoppingApplication application;
    protected FirebaseAuth auth;

    public BaseLiveService(BestShoppingApplication application) {
        this.application = application;
        bus = application.getBus();
        bus.register(this);
        auth = FirebaseAuth.getInstance();
    }
}
