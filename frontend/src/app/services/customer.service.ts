import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {CustomerModel} from "../main-module/models/customer.model";
import {Content} from "../main-module/models/content";

@Injectable({
    providedIn: 'root'
})
export class CustomerService {

    private _controllerUrlPrefix: string = '/api/customers';

    constructor(private http: HttpClient) {
    }

    getCustomers(page: number, size: number): Observable<Content<CustomerModel>> {
        return this.http.get<Content<CustomerModel>>(this._controllerUrlPrefix + '?page=' + page + '&size=' + size);
    }

    updateCustomerDetails(customer: CustomerModel): Observable<CustomerModel> {
        return this.http.put<CustomerModel>(this._controllerUrlPrefix + '/details', customer);
    }

    createCustomer(customer: CustomerModel): Observable<CustomerModel> {
        return this.http.post<CustomerModel>(this._controllerUrlPrefix, customer);
    }

    deleteCustomer(id: string): Observable<void> {
        return this.http.delete<void>(this._controllerUrlPrefix + '/' + id);
    }

    getCustomerByUserId(): Observable<CustomerModel> {
        return this.http.get<CustomerModel>(this._controllerUrlPrefix + '/users');
    }
}
