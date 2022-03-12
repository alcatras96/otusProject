import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ActiveSubscription} from "../main-module/models/active-subscription";
import {ListWrapper} from "../main-module/models/list-wrapper";

@Injectable({
    providedIn: 'root'
})
export class ActiveSubscriptionService {

    private _controllerUrlPrefix: string = '/api/active-subscriptions';

    constructor(private http: HttpClient) {
    }

    saveActiveSubscriptions(activeSubscriptions: ListWrapper<ActiveSubscription>): Observable<ActiveSubscription[]> {
        return this.http.post<ActiveSubscription[]>(this._controllerUrlPrefix, activeSubscriptions);
    }

    getActiveSubscriptionsForCurrentCustomer(): Observable<ActiveSubscription[]> {
        return this.http.get<ActiveSubscription[]>(this._controllerUrlPrefix + '/customers');
    }

    deleteActiveSubscriptionById(id: string): Observable<ActiveSubscription> {
        return this.http.delete<ActiveSubscription>(this._controllerUrlPrefix + '/' + id);
    }
}
