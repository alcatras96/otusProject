import {Component, OnInit} from '@angular/core';
import {BillingAccount} from "../../../models/billing-account";
import {CustomerService} from "../../../../services/customer.service";
import {OwnerService} from "../../../../services/owner.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {BsModalRef} from "ngx-bootstrap/modal";

@Component({
    selector: 'modal-wallet',
    templateUrl: './wallet.component.html',
    styleUrls: ['./wallet.component.css']
})
export class WalletModalComponent implements OnInit {

    year = ['2018', '2019', '2020', '2021', '2022', '2023'];
    month = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];

    walletForm: FormGroup;
    newBa: BillingAccount = new BillingAccount();

    constructor(private customersService: CustomerService, private ownersService: OwnerService,
                public bsModalRef: BsModalRef, private formBuilder: FormBuilder, private router: Router) {
    }

    register() {
        if (localStorage.getItem('currentUserRole') == 'owner') {
            this.newBa.validity = this.walletForm.controls['month'].value + ' ' + this.walletForm.controls['year'].value;
            this.ownersService.saveBillingAccount(this.newBa).subscribe(ba => {
                localStorage.setItem('wallet', ba.id);
            });
        }
        if (localStorage.getItem('currentUserRole') == 'customer') {
            this.newBa.validity = this.walletForm.controls['month'].value + ' ' + this.walletForm.controls['year'].value;
            this.customersService.saveBillingAccount(this.newBa).subscribe(ba => {
                    localStorage.setItem('wallet', ba.id);
                }
            );
        }
        this.bsModalRef.hide();
        this.router.navigateByUrl('/');
    }

    ngOnInit(): void {
        this.walletForm = this.formBuilder.group({
            number: ['', Validators.required],
            cvv: ['', Validators.required],
            month: ['January', Validators.required],
            year: ['2018', Validators.required]
        });
    }
}
