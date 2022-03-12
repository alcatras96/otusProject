import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Subscription} from "../main-module/models/subscription";
import {Content} from "../main-module/models/content";

@Injectable({
    providedIn: 'root'
})
export class SubscriptionService {

    private _controllerUrlPrefix: string = '/api/subscriptions';

    constructor(private http: HttpClient) {
    }

    getSubscriptionsPaged(page: number, size: number): Observable<Content<Subscription>> {
        return this.http.get<Content<Subscription>>(this._controllerUrlPrefix + '?page=' + page + '&size=' + size);
    }

    getSubscriptionsByOwnerId(id: string): Observable<Subscription[]> {
        return this.http.get<Subscription[]>(this._controllerUrlPrefix + '/owners/' + id);
    }

    getSubscriptionByCategoryId(id: string, page: number, size: number): Observable<Content<Subscription>> {
        return this.http.get<Content<Subscription>>(this._controllerUrlPrefix + '/categories/' + id + '?page=' + page + '&size=' + size);
    }

    saveSubscription(subscription: Subscription): Observable<Subscription> {
        return this.http.post<Subscription>(this._controllerUrlPrefix, subscription);
    }

    getSubscriptionsByNameLike(name: string, page: number, size: number): Observable<Content<Subscription>> {
        return this.http.get<Content<Subscription>>(this._controllerUrlPrefix + '/search?name=' + name + "&page=" + page + "&size=" + size);
    }

    deleteSubscription(id: string): Observable<void> {
        return this.http.delete<void>(this._controllerUrlPrefix + '/' + id);
    }
}
