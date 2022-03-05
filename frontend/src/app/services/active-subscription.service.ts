import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ActiveSubscription} from "../main-module/models/active-subscription";
import {ListWrapper} from "../main-module/models/list-wrapper";

@Injectable({
    providedIn: 'root'
})
export class ActiveSubscriptionService {

    constructor(private http: HttpClient) {
    }

    saveActiveSubscriptions(activeSubscriptions: ListWrapper<ActiveSubscription>): Observable<ActiveSubscription[]> {
        return this.http.post<ActiveSubscription[]>('/api/active_subscription', activeSubscriptions);
    }

    getActiveSubscriptionsForCurrentCustomer(): Observable<ActiveSubscription[]> {
        return this.http.get<ActiveSubscription[]>('/api/active_subscription/customer');
    }

    deleteActiveSubscriptionById(id: string): Observable<ActiveSubscription> {
        return this.http.delete<ActiveSubscription>('/api/active_subscription/' + id);
    }
}
