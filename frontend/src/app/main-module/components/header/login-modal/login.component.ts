import {Component, EventEmitter, Output} from '@angular/core';
import {AuthInfoModel} from "../../../models/auth-info.model";
import {AuthService} from "../../../../services/auth.service";
import {TokenStorage} from "../../../../services/token.storage";
import {UserService} from "../../../../services/user.service";
import {OwnerService} from "../../../../services/owner.service";
import {CustomerService} from "../../../../services/customer.service";
import {BillingAccountModel} from "../../../models/billing-account.model";

@Component({
    selector: 'modal-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginModalComponent {

    alertVisible = false;
    @Output() onChange = new EventEmitter();

    loginUser: AuthInfoModel = new AuthInfoModel();

    constructor(private authService: AuthService, private token: TokenStorage, private userService: UserService,
                private ownersService: OwnerService, private customersService: CustomerService) {
    }

    //todo: refactor
    login(): void {
        this.authService.attemptAuth(this.loginUser).subscribe(data => {
                this.token.saveToken(data.token);
                localStorage.setItem('currentUser', this.loginUser.login);

                this.userService.getUserByLogin().subscribe(user => {
                    const role = user.role.name;
                    localStorage.setItem('currentUserRole', role);
                    this.onChange.emit();

                    if (role == 'owner') {
                        this.ownersService.getOwnerByUserId().subscribe(owner => {
                            localStorage.setItem('ownerId', owner.id);
                            this._setBillingAccountIdToLocalStorage(owner.billingAccount);
                        });
                    } else if (role == 'customer') {
                        this.customersService.getCustomerByUserId().subscribe(customer => {
                            localStorage.setItem('customerId', customer.id);
                            localStorage.setItem('status', customer.status);
                            this._setBillingAccountIdToLocalStorage(customer.billingAccount);
                        });
                    }
                });
            },
            () => {
                this.alertVisible = true;
            }
        );

    }

    close() {
        this.onChange.emit();
    }

    private _setBillingAccountIdToLocalStorage(billingAccount: BillingAccountModel) {
        localStorage.setItem('wallet', billingAccount ? billingAccount.id : 'unregistered');
    }
}
