import {Component, OnInit} from '@angular/core';
import {BillingAccount} from "../../../models/billing-account";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {BsModalRef} from "ngx-bootstrap/modal";
import {BillingAccountService} from "../../../../services/billing-account.service";

@Component({
    selector: 'modal-wallet',
    templateUrl: './wallet.component.html',
    styleUrls: ['./wallet.component.css']
})
export class WalletModalComponent implements OnInit {

    year = ['2022', '2023', '2024', '2025', '2026'];
    month = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];

    walletForm: FormGroup;
    newBa: BillingAccount = new BillingAccount();

    constructor(private billingAccountService: BillingAccountService,
                public bsModalRef: BsModalRef, private formBuilder: FormBuilder, private router: Router) {
    }

    register() {
        if (localStorage.getItem('currentUserRole') == 'owner') {
            this.newBa.validity = this.walletForm.controls['month'].value + ' ' + this.walletForm.controls['year'].value;
            this.billingAccountService.saveOwnerBillingAccount(this.newBa).subscribe(ba => {
                localStorage.setItem('wallet', ba.id);
            });
        }
        if (localStorage.getItem('currentUserRole') == 'customer') {
            this.newBa.validity = this.walletForm.controls['month'].value + ' ' + this.walletForm.controls['year'].value;
            this.billingAccountService.saveCustomerBillingAccount(this.newBa).subscribe(ba => {
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
