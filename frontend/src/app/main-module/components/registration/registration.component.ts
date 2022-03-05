import {Component} from '@angular/core';
import {Customer} from "../../models/customer";
import {Owner} from "../../models/owner";
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
    radioModel = 'Customer';
    isOpen = false;
    password = 'password';
    confirmPassword = 'password';

    passwordForm: FormGroup;
    othersForm: FormGroup;
    // private subCustomer: Subscription[] = [];
    // private subOwner: Subscription[] = [];
    newUser: User = new User();
    newCustomer: Customer = new Customer();
    newOwner: Owner = new Owner();

    constructor(private loadingService: NgxSpinnerService, private usersService: UserService,
                private customersService: CustomerService, private ownersService: OwnerService,
                private formBuilder: FormBuilder, private router: Router) {
        this.passwordForm = formBuilder.group({
            password: ['', Validators.required],
            confirmPassword: ['', Validators.required]
        }, {validator: this.checkPasswords});

        this.othersForm = formBuilder.group({
            firstName: [''],
            orgName: [''],
            login: ['', Validators.required],
            email: ['', Validators.required],
            address: ['']
        })
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
                this.othersForm.reset();
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
                this.othersForm.reset();
                this.router.navigateByUrl('/');
            });
    }

    private refreshCustomer(): void {
        this.newUser = new User();
        this.newCustomer = new Customer();
    }

    private refreshOwner(): void {
        this.newUser = new User();
        this.newOwner = new Owner();
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
