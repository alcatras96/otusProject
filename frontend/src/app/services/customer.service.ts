import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Customer} from "../main-module/models/customer";
import {BillingAccount} from "../main-module/models/billing-account";
import {Content} from "../main-module/models/content";

@Injectable({
    providedIn: 'root'
})
export class CustomerService {

    constructor(private http: HttpClient) {
    }

    getCustomers(page: number, size: number): Observable<Content<Customer>> {
        return this.http.get<Content<Customer>>('/api/customers?page=' + page + '&size=' + size);
    }

    saveCustomer(customer: Customer): Observable<Customer> {
        return this.http.put<Customer>('/api/customers', customer);
    }

    updateCustomerDetails(customer: Customer): Observable<Customer> {
        return this.http.put<Customer>('/api/customers/details', customer);
    }

    createCustomer(customer: Customer): Observable<Customer> {
        return this.http.post<Customer>('/api/customers', customer);
    }

    deleteCustomer(id: string): Observable<void> {
        return this.http.delete<void>('/api/customers/' + id);
    }

    addMoneyOnBillingAccount(money: number): Observable<BillingAccount> {
        return this.http.put<BillingAccount>('/api/customers/ba/' + money, '');
    }

    getCustomerByUserId(): Observable<Customer> {
        return this.http.get<Customer>('/api/customers/user/');
    }
}
