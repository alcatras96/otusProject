import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {SubscriptionModel} from "../main-module/models/subscription.model";
import {Content} from "../main-module/models/content";

@Injectable({
    providedIn: 'root'
})
export class SubscriptionService {

    private _controllerUrlPrefix: string = '/api/subscriptions';

    constructor(private http: HttpClient) {
    }

    getSubscriptionsPaged(page: number, size: number): Observable<Content<SubscriptionModel>> {
        return this.http.get<Content<SubscriptionModel>>(this._controllerUrlPrefix + '?page=' + page + '&size=' + size);
    }

    getSubscriptionsByOwnerId(id: string): Observable<SubscriptionModel[]> {
        return this.http.get<SubscriptionModel[]>(this._controllerUrlPrefix + '/owners/' + id);
    }

    getSubscriptionByCategoryId(id: string, page: number, size: number): Observable<Content<SubscriptionModel>> {
        return this.http.get<Content<SubscriptionModel>>(this._controllerUrlPrefix + '/categories/' + id + '?page=' + page + '&size=' + size);
    }

    saveSubscription(subscription: SubscriptionModel): Observable<SubscriptionModel> {
        return this.http.post<SubscriptionModel>(this._controllerUrlPrefix, subscription);
    }

    getSubscriptionsByNameLike(name: string, page: number, size: number): Observable<Content<SubscriptionModel>> {
        return this.http.get<Content<SubscriptionModel>>(this._controllerUrlPrefix + '/search?name=' + name + "&page=" + page + "&size=" + size);
    }

    deleteSubscription(id: string): Observable<void> {
        return this.http.delete<void>(this._controllerUrlPrefix + '/' + id);
    }
}
