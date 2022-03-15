import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {BillingAccountModel} from "../main-module/models/billing-account.model";

@Injectable({
    providedIn: 'root'
})
export class BillingAccountService {

    private _controllerUrlPrefix: string = '/api/billing-accounts';

    constructor(private http: HttpClient) {
    }

    createBillingAccount(billingAccount: BillingAccountModel): Observable<BillingAccountModel> {
        return this.http.post<BillingAccountModel>(this._controllerUrlPrefix, billingAccount);
    }

    addMoneyOnBillingAccount(money: number): Observable<BillingAccountModel> {
        return this.http.put<BillingAccountModel>(this._controllerUrlPrefix + '/money/add', money);
    }
}
