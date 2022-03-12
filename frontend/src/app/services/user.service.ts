import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../main-module/models/user";

@Injectable({
    providedIn: 'root'
})
export class UserService {

    private _controllerUrlPrefix: string = '/api/users';

    constructor(private http: HttpClient) {
    }

    saveUser(user: User): Observable<User> {
        return this.http.post<User>(this._controllerUrlPrefix, user);
    }

    getUserById(id: string): Observable<User> {
        return this.http.get<User>(this._controllerUrlPrefix + '/' + id);
    }

    getUserByLogin(): Observable<User> {
        return this.http.get<User>(this._controllerUrlPrefix + '/login');
    }
}
