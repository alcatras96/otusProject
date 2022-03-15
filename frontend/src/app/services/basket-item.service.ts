import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {BasketItemModel} from "../main-module/models/basket-item.model";
import {ListWrapper} from "../main-module/models/list-wrapper";

@Injectable({
    providedIn: 'root'
})
export class BasketItemService {

    private _controllerUrlPrefix: string = '/api/basket-items';

    constructor(private http: HttpClient) {
    }

    saveBasketItem(basketItem: ListWrapper<BasketItemModel>): Observable<void> {
        return this.http.post<void>(this._controllerUrlPrefix, basketItem);
    }

    getBasketItemsByCustomerId(): Observable<BasketItemModel[]> {
        return this.http.get<BasketItemModel[]>(this._controllerUrlPrefix + '/customers');
    }

    getCount(): Observable<number> {
        return this.http.get<number>(this._controllerUrlPrefix + '/count');
    }

    deleteBasketItemById(id: string): Observable<BasketItemModel> {
        return this.http.delete<BasketItemModel>(this._controllerUrlPrefix + '/' + id);
    }

    deleteAllBasketItemsByCustomerId(): Observable<void> {
        return this.http.delete<void>(this._controllerUrlPrefix + '/customers');
    }
}
