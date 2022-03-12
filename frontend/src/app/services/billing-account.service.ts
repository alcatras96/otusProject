import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {BillingAccount} from "../main-module/models/billing-account";

@Injectable({
    providedIn: 'root'
})
export class BillingAccountService {

    private _controllerUrlPrefix: string = '/api/billing-accounts';

    constructor(private http: HttpClient) {
    }

    createCustomerBillingAccount(billingAccount: BillingAccount): Observable<BillingAccount> {
        return this.http.post<BillingAccount>(this._controllerUrlPrefix + '/customer', billingAccount);
    }

    createOwnerBillingAccount(billingAccount: BillingAccount): Observable<BillingAccount> {
        return this.http.post<BillingAccount>(this._controllerUrlPrefix + '/owner', billingAccount);
    }

    addMoneyOnBillingAccount(money: number): Observable<BillingAccount> {
        return this.http.put<BillingAccount>(this._controllerUrlPrefix + '/money/add', money);
    }
}
