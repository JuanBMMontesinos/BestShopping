package online.upcare.f4hupcaregroup.bestshopping.live;

import online.upcare.f4hupcaregroup.bestshopping.infrastructure.BestShoppingApplication;

public class Module {
    public static void Register(BestShoppingApplication application) {
        new LiveAccountService(application);
    }
}
