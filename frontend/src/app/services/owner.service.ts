import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {OwnerModel} from "../main-module/models/owner.model";
import {Content} from "../main-module/models/content";
import {CustomerModel} from "../main-module/models/customer.model";

@Injectable({
    providedIn: 'root'
})
export class OwnerService {

    private _controllerUrlPrefix: string = '/api/owners';

    constructor(private http: HttpClient) {
    }

    getOwners(page: number, size: number): Observable<Content<OwnerModel>> {
        return this.http.get<Content<OwnerModel>>(this._controllerUrlPrefix + '?page=' + page + '&size=' + size);
    }

    updateOwnerDetails(owner: OwnerModel): Observable<CustomerModel> {
        return this.http.put<CustomerModel>(this._controllerUrlPrefix + '/details', owner);
    }

    saveOwner(owner: OwnerModel): Observable<OwnerModel> {
        return this.http.post<OwnerModel>(this._controllerUrlPrefix, owner);
    }

    deleteOwner(id: string): Observable<void> {
        return this.http.delete<void>(this._controllerUrlPrefix + '/' + id);
    }

    getOwnerByUserId(): Observable<OwnerModel> {
        return this.http.get<OwnerModel>(this._controllerUrlPrefix + '/users');
    }
}
