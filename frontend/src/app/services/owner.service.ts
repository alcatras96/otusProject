import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Owner} from "../main-module/models/owner";
import {Content} from "../main-module/models/content";
import {Customer} from "../main-module/models/customer";

@Injectable({
    providedIn: 'root'
})
export class OwnerService {

    private _controllerUrlPrefix: string = '/api/owners';

    constructor(private http: HttpClient) {
    }

    getOwners(page: number, size: number): Observable<Content<Owner>> {
        return this.http.get<Content<Owner>>(this._controllerUrlPrefix + '?page=' + page + '&size=' + size);
    }

    updateOwnerDetails(owner: Owner): Observable<Customer> {
        return this.http.put<Customer>(this._controllerUrlPrefix + '/details', owner);
    }

    saveOwner(owner: Owner): Observable<Owner> {
        return this.http.post<Owner>(this._controllerUrlPrefix, owner);
    }

    deleteOwner(id: string): Observable<void> {
        return this.http.delete<void>(this._controllerUrlPrefix + '/' + id);
    }

    getOwnerByUserId(): Observable<Owner> {
        return this.http.get<Owner>(this._controllerUrlPrefix + '/user');
    }
}
