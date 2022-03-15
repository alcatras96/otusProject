import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AuthInfoModel} from "../main-module/models/auth-info.model";
import {TokenStorage} from "./token.storage";

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    constructor(private http: HttpClient, private tokenStorage: TokenStorage) {
    }

    attemptAuth(authInfo: AuthInfoModel): Observable<any> {
        return this.http.post<any>('/api/token/generate', authInfo);
    }

    logout() {
        this.tokenStorage.signOut();
        localStorage.clear();
    }
}

