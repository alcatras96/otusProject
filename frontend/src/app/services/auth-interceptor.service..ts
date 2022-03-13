import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from "rxjs";
import {TokenStorage} from "./token.storage";
import {catchError} from "rxjs/operators";
import {Router} from "@angular/router";
import {AuthService} from "./auth.service";

const TOKEN_HEADER_KEY = 'Authorization';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(private token: TokenStorage, private router: Router, private authService: AuthService) {
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (this.token.getToken() != null) {
            request = request.clone({headers: request.headers.set(TOKEN_HEADER_KEY, 'Bearer ' + this.token.getToken())});
        }
        return next.handle(request).pipe(
            catchError(err => {
                    if (err.status === 401) {
                        this.authService.logout();
                        this.router.navigateByUrl('/');
                    }

                    const error = err.error || err.statusText;
                    return throwError(error);
                }
            ));
    }

}
