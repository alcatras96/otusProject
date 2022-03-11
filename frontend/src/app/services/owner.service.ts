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

    constructor(private http: HttpClient) {
    }

    getOwners(page: number, size: number): Observable<Content<Owner>> {
        return this.http.get<Content<Owner>>('/api/owners?page=' + page + '&size=' + size);
    }

    saveEditedOwner(owner: Owner): Observable<Owner> {
        return this.http.put<Owner>('/api/owners', owner);
    }

    updateOwnerDetails(owner: Owner): Observable<Customer> {
        return this.http.put<Customer>('/api/owners/details', owner);
    }

    saveOwner(owner: Owner): Observable<Owner> {
        return this.http.post<Owner>('/api/owners', owner);
    }

    deleteOwner(id: string): Observable<void> {
        return this.http.delete<void>('/api/owners/' + id);
    }

    getOwnerByUserId(): Observable<Owner> {
        return this.http.get<Owner>('/api/owners/user/');
    }
}
