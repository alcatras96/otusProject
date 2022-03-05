import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {SubscriptionCategory} from "../main-module/models/subscription-category";

@Injectable({
    providedIn: 'root'
})
export class CategoryService {

    constructor(private http: HttpClient) {
    }

    getCategories(): Observable<SubscriptionCategory[]> {
        return this.http.get<SubscriptionCategory[]>('/api/category');
    }
}
