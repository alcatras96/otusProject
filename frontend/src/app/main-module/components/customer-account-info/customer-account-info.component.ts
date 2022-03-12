import {Component, OnInit, TemplateRef} from '@angular/core';
import {CustomerService} from "../../../services/customer.service";
import {Customer} from "../../models/customer";
import {BillingAccountService} from "../../../services/billing-account.service";
import {ActiveSubscription} from "../../models/active-subscription";
import {ActiveSubscriptionService} from "../../../services/active-subscription.service";
import {BasketItemService} from "../../../services/basket-item.service";
import {BsModalRef, BsModalService} from 'ngx-bootstrap/modal';
import {NgxSpinnerService} from "ngx-spinner";

@Component({
    selector: 'app-customer-account-info',
    templateUrl: './customer-account-info.component.html',
    styleUrls: ['./customer-account-info.component.css']
})
export class CustomerAccountInfoComponent implements OnInit {

    amount: number = 0;
    modalRef: BsModalRef;
    itemsCounter: number;
    customer: Customer = new Customer();
    activeSubs: ActiveSubscription[] = [];

    constructor(private modalService: BsModalService, private loadingService: NgxSpinnerService,
                private sbService: BasketItemService, private customersService: CustomerService,
                private billingAccountService: BillingAccountService, private ASService: ActiveSubscriptionService) {
    }

    openModal(template: TemplateRef<any>) {
        this.modalRef = this.modalService.show(template);
    }

    // Calls on component init
    ngOnInit() {
        this.loadCustomer();
        this.loadActiveSubs();
        this.updateItemsCounter();
    }

    private loadCustomer(): void {
        this.loadingService.show();
        this.customersService.getCustomerByUserId().subscribe(customer => {
            // Parse json response into local array
            this.customer = customer;
            localStorage.setItem('status', customer.status.name);
            this.loadingService.hide();
        });
    }

    private loadActiveSubs(): void {
        this.loadingService.show();
        this.ASService.getActiveSubscriptionsForCurrentCustomer().subscribe(activeSubscriptions => {
            this.activeSubs = activeSubscriptions;
            this.loadingService.hide();
        });

    }

    walletIsPresent(): boolean {
        return localStorage.getItem('wallet') != 'unregistered';
    }

    fillUp(): void {
        this.billingAccountService.addMoneyOnBillingAccount(this.amount).subscribe(() => {
            this.loadCustomer();
            this.amount = 0;
            this.modalRef.hide();
        });
    }

    amountIsNegative(): boolean {
        return this.amount < 0;
    }

    delete(id: string): void {
        this.ASService.deleteActiveSubscriptionById(id).subscribe(() => {
            this.loadActiveSubs();
        })
    }

    private updateItemsCounter(): void {
        this.sbService.getCount().subscribe(count => {
            this.itemsCounter = count;
        });
    }
}
