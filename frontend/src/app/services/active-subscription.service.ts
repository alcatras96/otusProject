import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ActiveSubscriptionModel} from "../main-module/models/active-subscription.model";
import {ListWrapper} from "../main-module/models/list-wrapper";

@Injectable({
    providedIn: 'root'
})
export class ActiveSubscriptionService {

    private _controllerUrlPrefix: string = '/api/active-subscriptions';

    constructor(private http: HttpClient) {
    }

    saveActiveSubscriptions(activeSubscriptions: ListWrapper<ActiveSubscriptionModel>): Observable<ActiveSubscriptionModel[]> {
        return this.http.post<ActiveSubscriptionModel[]>(this._controllerUrlPrefix, activeSubscriptions);
    }

    getActiveSubscriptionsForCurrentCustomer(): Observable<ActiveSubscriptionModel[]> {
        return this.http.get<ActiveSubscriptionModel[]>(this._controllerUrlPrefix + '/customers');
    }

    deleteActiveSubscriptionById(id: string): Observable<ActiveSubscriptionModel> {
        return this.http.delete<ActiveSubscriptionModel>(this._controllerUrlPrefix + '/' + id);
    }
}
