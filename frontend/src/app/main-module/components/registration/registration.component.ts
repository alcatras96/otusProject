import {Component} from '@angular/core';
import {CustomerModel} from "../../models/customer.model";
import {OwnerModel} from "../../models/owner.model";
import {User} from "../../models/user";
import {CustomerService} from "../../../services/customer.service";
import {OwnerService} from "../../../services/owner.service";
import {UserService} from "../../../services/user.service";
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from "@angular/router";
import {NgxSpinnerService} from "ngx-spinner";
import {finalize} from "rxjs";

@Component({
    selector: 'app-registration',
    templateUrl: './registration.component.html',
    styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {
    currentRegisterOption = 'Customer';
    isOpen = false;
    password = 'password';
    confirmPassword = 'password';

    passwordForm: FormGroup;
    usersForm: FormGroup;
    ownersForm: FormGroup;
    customersForm: FormGroup;
    newUser: User = new User();
    newCustomer: CustomerModel = new CustomerModel();
    newOwner: OwnerModel = new OwnerModel();

    constructor(private loadingService: NgxSpinnerService, private usersService: UserService,
                private customersService: CustomerService, private ownersService: OwnerService,
                private formBuilder: FormBuilder, private router: Router) {
        this.passwordForm = formBuilder.group({
            password: ['', Validators.required],
            confirmPassword: ['', Validators.required]
        }, {validator: this.checkPasswords});

        this.usersForm = formBuilder.group({
            login: ['', Validators.required],
            email: ['', Validators.required]
        });

        this.ownersForm = formBuilder.group({
            orgName: ['']
        });

        this.customersForm = formBuilder.group({
            firstName: [''],
            address: ['']
        });
    }

    public _addCustomer(): void {
        this.loadingService.show();

        this.newUser.role.id = '2';
        this.newCustomer.user = this.newUser;

        this.customersService.createCustomer(this.newCustomer)
            .pipe(finalize(() => {
                this.loadingService.hide();
            }))
            .subscribe(() => {
                this.refreshCustomer();
                this.passwordForm.reset();
                this.usersForm.reset();
                this.router.navigateByUrl('/');
            });
    }

    public _addOwner(): void {
        this.loadingService.show();

        this.newUser.role.id = '3';
        this.newOwner.user = this.newUser;

        this.ownersService.saveOwner(this.newOwner)
            .pipe(finalize(() => {
                this.loadingService.hide();
            }))
            .subscribe(() => {
                this.refreshOwner();
                this.passwordForm.reset();
                this.usersForm.reset();
                this.router.navigateByUrl('/');
            });
    }

    public _isRegisterButtonDisabled(currentRegisterOption: string): boolean {
        return this.passwordForm.invalid || this.usersForm.invalid ||
            (currentRegisterOption == 'Owner' ? this.ownersForm.invalid : this.customersForm.invalid);
    }

    private refreshCustomer(): void {
        this.newUser = new User();
        this.newCustomer = new CustomerModel();
    }

    private refreshOwner(): void {
        this.newUser = new User();
        this.newOwner = new OwnerModel();
    }


    checkPasswords(group: FormGroup) {
        let pass = group.controls['password'].value;
        let confirmPass = group.controls['confirmPassword'].value;

        return pass === confirmPass ? null : {notSame: true}
    }

    showPassword(): void {
        this.password = (this.password == 'password') ? 'text' : 'password';
    }

    showConfirmPassword(): void {
        this.confirmPassword = (this.confirmPassword == 'password') ? 'text' : 'password';
    }
}
