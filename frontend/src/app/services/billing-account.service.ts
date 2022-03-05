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

    getCustomerBillingAccounts(): Observable<BillingAccount[]> {
        return this.http.get<BillingAccount[]>('/api/billing_accounts');
    }

    saveBillingAccount(billingAccount: BillingAccount): Observable<BillingAccount> {
        return this.http.put<BillingAccount>('/api/billing_accounts', billingAccount);
    }
}
