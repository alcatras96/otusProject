import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';

import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';

import {AppComponent} from './app.component';
import {ShopComponent} from './main-module/components/shop/shop.component';
import {notFoundComponent} from './main-module/components/not-found/not-found.component';
import {RegistrationComponent} from "./main-module/components/registration/registration.component";
import {HeaderComponent} from "./main-module/components/header/header.component";
import {LoginModalComponent} from "./main-module/components/header/login-modal/login.component";
import {EditCustomerModalComponent} from "./main-module/components/admin-panel/edit-customer-modal/edit-customer-modal.component";
import {EditSubscriptionModalComponent} from "./main-module/components/owner-account-info/edit-subscription-modal/edit-subscription-modal.component";
import {EditOwnerModalComponent} from "./main-module/components/admin-panel/edit-owner-modal/edit-owner-modal.component";
import {ShoppingListComponent} from "./main-module/components/shopping-list/shopping-list.component";
import {AdminPanelComponent} from "./main-module/components/admin-panel/admin-panel.component";
import {ConfirmModalComponent} from "./main-module/components/confirm-modal/confirm-modal.component";
import {WalletModalComponent} from "./main-module/components/header/wallet-modal/wallet.component";
import {OwnerAccountInfoComponent} from "./main-module/components/owner-account-info/owner-account-info.component";
import {CustomerAccountInfoComponent} from "./main-module/components/customer-account-info/customer-account-info.component";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {TokenStorage} from "./services/token.storage";
import {AuthInterceptor} from "./services/auth-interceptor.service.";
import {AdminPanelGuard} from "./main-module/components/admin-panel/admin-panel.guard";
import {RegistrationGuard} from "./main-module/components/registration/registration.guard";
import {AdminNavBarComponent} from "./main-module/components/admin-panel/admin-navbar/adminNavBar.component";
import {OwnersListComponent} from "./main-module/components/admin-panel/owners-list/owners-list.component";
import {AdminSideBarComponent} from "./main-module/components/admin-panel/admin-sidebar/adminSideBar.component";
import {NgxSpinnerModule} from "ngx-spinner";
import {CollapseModule} from "ngx-bootstrap/collapse";
import {TabsModule} from "ngx-bootstrap/tabs";
import {AlertModule} from "ngx-bootstrap/alert";
import {PaginationModule} from "ngx-bootstrap/pagination";
import {ModalModule} from "ngx-bootstrap/modal";
import {ButtonsModule} from "ngx-bootstrap/buttons";

const appRoutes: Routes = [
    {path: '', component: ShopComponent},
    {path: 'registration', component: RegistrationComponent, canActivate: [RegistrationGuard]},
    {path: 'shoppingList', component: ShoppingListComponent},
    {path: 'ownerAccountInfo/:id', component: OwnerAccountInfoComponent},
    {path: 'customerAccountInfo/:id', component: CustomerAccountInfoComponent},
    {path: 'adminPanel', component: AdminPanelComponent, canActivate: [AdminPanelGuard]},
    {path: 'adminPanel/Owners', component: OwnersListComponent, canActivate: [AdminPanelGuard]},
    {path: 'login', component: LoginModalComponent},
    {path: '**', component: notFoundComponent}
];

@NgModule({
    declarations: [
        AppComponent,
        ShopComponent,
        notFoundComponent,
        RegistrationComponent,
        HeaderComponent,
        LoginModalComponent,
        EditOwnerModalComponent,
        EditSubscriptionModalComponent,
        EditCustomerModalComponent,
        ShoppingListComponent,
        AdminPanelComponent,
        WalletModalComponent,
        OwnerAccountInfoComponent,
        CustomerAccountInfoComponent,
        ConfirmModalComponent,
        AdminNavBarComponent,
        OwnersListComponent,
        AdminSideBarComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule,
        RouterModule.forRoot(appRoutes),
        NgxSpinnerModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,
        CollapseModule,
        TabsModule,
        AlertModule,
        PaginationModule,
        ModalModule.forRoot(),
        ButtonsModule
    ],
    providers: [
        TokenStorage,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true
        },
        AdminPanelGuard,
        RegistrationGuard
    ],
    bootstrap: [AppComponent],
    entryComponents: [LoginModalComponent, WalletModalComponent, EditOwnerModalComponent, EditCustomerModalComponent, EditSubscriptionModalComponent]
})
export class AppModule {
}
