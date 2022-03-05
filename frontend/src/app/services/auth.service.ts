import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AuthInfo} from "../main-module/models/auth-info";
import {TokenStorage} from "./token.storage";

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    constructor(private http: HttpClient, private tokenStorage: TokenStorage) {
    }

    attemptAuth(loginUser: AuthInfo): Observable<any> {
        return this.http.post<any>('/api/token/generate-token', loginUser);
    }

    logout() {
        this.tokenStorage.signOut();
        localStorage.clear();
    }
}

