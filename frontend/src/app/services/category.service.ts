import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {SubscriptionCategoryModel} from "../main-module/models/subscription-category.model";

@Injectable({
    providedIn: 'root'
})
export class CategoryService {

    constructor(private http: HttpClient) {
    }

    getCategories(): Observable<SubscriptionCategoryModel[]> {
        return this.http.get<SubscriptionCategoryModel[]>('/api/categories');
    }
}
