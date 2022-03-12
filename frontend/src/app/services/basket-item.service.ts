import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {BasketItem} from "../main-module/models/basket-item";
import {ListWrapper} from "../main-module/models/list-wrapper";

@Injectable({
    providedIn: 'root'
})
export class BasketItemService {

    private _controllerUrlPrefix: string = '/api/basket-items';

    constructor(private http: HttpClient) {
    }

    saveBasketItem(basketItem: ListWrapper<BasketItem>): Observable<void> {
        return this.http.post<void>(this._controllerUrlPrefix, basketItem);
    }

    getBasketItemsByCustomerId(): Observable<BasketItem[]> {
        return this.http.get<BasketItem[]>(this._controllerUrlPrefix + '/customers');
    }

    getCount(): Observable<number> {
        return this.http.get<number>(this._controllerUrlPrefix + '/count');
    }

    deleteBasketItemById(id: string): Observable<BasketItem> {
        return this.http.delete<BasketItem>(this._controllerUrlPrefix + '/' + id);
    }

    deleteAllBasketItemsByCustomerId(): Observable<void> {
        return this.http.delete<void>(this._controllerUrlPrefix + '/customers');
    }
}
