import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Subscription} from "../main-module/models/subscription";
import {Content} from "../main-module/models/content";

@Injectable({
    providedIn: 'root'
})
export class SubscriptionService {

    constructor(private http: HttpClient) {
    }

    getSubscriptionsPaged(page: number, size: number): Observable<Content<Subscription>> {
        return this.http.get<Content<Subscription>>('/api/subscriptions?page=' + page + '&size=' + size);
    }

    getSubscriptionsByOwnerId(id: string): Observable<Subscription[]> {
        return this.http.get<Subscription[]>('/api/subscriptions/owner/' + id);
    }

    getSubscriptionByCategoryId(id: string, page: number, size: number): Observable<Content<Subscription>> {
        return this.http.get<Content<Subscription>>('/api/subscriptions/category/' + id + '?page=' + page + '&size=' + size);
    }

    saveSubscription(subscription: Subscription): Observable<Subscription> {
        return this.http.post<Subscription>('/api/subscriptions', subscription);
    }

    getSubscriptionsByNameLike(name: string, page: number, size: number): Observable<Content<Subscription>> {
        return this.http.get<Content<Subscription>>('/api/subscriptions/search?name=' + name + "&page=" + page + "&size=" + size);
    }

    deleteSubscription(id: string): Observable<void> {
        return this.http.delete<void>('/api/subscriptions/' + id);
    }
}
