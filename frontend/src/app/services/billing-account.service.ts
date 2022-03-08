import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {BillingAccount} from "../main-module/models/billing-account";

@Injectable({
    providedIn: 'root'
})
export class BillingAccountService {

    constructor(private http: HttpClient) {
    }

    saveBillingAccount(billingAccount: BillingAccount): Observable<BillingAccount> {
        return this.http.put<BillingAccount>('/api/billing_accounts', billingAccount);
    }

    saveCustomerBillingAccount(billingAccount: BillingAccount): Observable<BillingAccount> {
        return this.http.post<BillingAccount>('/api/billing_accounts/customer', billingAccount);
    }

    saveOwnerBillingAccount(billingAccount: BillingAccount): Observable<BillingAccount> {
        return this.http.post<BillingAccount>('/api/billing_accounts/owner', billingAccount);
    }
}
