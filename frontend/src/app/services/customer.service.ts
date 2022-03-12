import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Customer} from "../main-module/models/customer";
import {Content} from "../main-module/models/content";

@Injectable({
    providedIn: 'root'
})
export class CustomerService {

    private _controllerUrlPrefix: string = '/api/customers';

    constructor(private http: HttpClient) {
    }

    getCustomers(page: number, size: number): Observable<Content<Customer>> {
        return this.http.get<Content<Customer>>(this._controllerUrlPrefix + '?page=' + page + '&size=' + size);
    }

    updateCustomerDetails(customer: Customer): Observable<Customer> {
        return this.http.put<Customer>(this._controllerUrlPrefix + '/details', customer);
    }

    createCustomer(customer: Customer): Observable<Customer> {
        return this.http.post<Customer>(this._controllerUrlPrefix, customer);
    }

    deleteCustomer(id: string): Observable<void> {
        return this.http.delete<void>(this._controllerUrlPrefix + '/' + id);
    }

    getCustomerByUserId(): Observable<Customer> {
        return this.http.get<Customer>(this._controllerUrlPrefix + '/user');
    }
}
