import {Component, OnInit, TemplateRef} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {OwnerModel} from "../../models/owner.model";
import {OwnerService} from "../../../services/owner.service";
import {User} from "../../models/user";
import {SubscriptionService} from "../../../services/subscription.service";
import {SubscriptionModel} from "../../models/subscription.model";
import {BillingAccountService} from "../../../services/billing-account.service";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {NgxSpinnerService} from "ngx-spinner";

@Component({
    selector: 'app-owner-account-info',
    templateUrl: './owner-account-info.component.html',
    styleUrls: ['./owner-account-info.component.css']
})
export class OwnerAccountInfoComponent implements OnInit {

    id: string;
    modalRef: BsModalRef;
    amount: number = 0;
    owner: OwnerModel = new OwnerModel();
    subscriptions: SubscriptionModel[] = [];
    // private subOwner: SubscriptionModel[] = [];
    // private subs: SubscriptionModel[] = [];
    editableSubscription: SubscriptionModel;

    constructor(private modalService: BsModalService, private activateRoute: ActivatedRoute,
                private loadingService: NgxSpinnerService, private ownersService: OwnerService,
                private subscriptionsService: SubscriptionService, private baService: BillingAccountService) {
        this.id = activateRoute.snapshot.params['id'];
    }

    openModal(template: TemplateRef<any>) {
        this.modalRef = this.modalService.show(template);
    }

    // Calls on component init
    ngOnInit() {
        this.loadOwner();
    }

    private loadOwner(): void {
        this.loadingService.show();
        this.owner.user = new User();
        this.ownersService.getOwnerByUserId().subscribe(owner => {
            // Parse json response into local array
            this.owner = owner;
            this.loadSubscriptions();
        });
    }

    private loadSubscriptions(): void {
        this.subscriptionsService.getSubscriptionsByOwnerId(this.owner.id).subscribe(subscriptions => {
                this.subscriptions = subscriptions;
                this.loadingService.hide();
            }
        );
    }

    walletIsPresent(): boolean {
        if (localStorage.getItem('wallet') != 'unregistered') {
            return true;
        }
        return false;
    }

    openAddSubscriptionModal(template: TemplateRef<any>): void {
        this.editableSubscription = new SubscriptionModel();
        this.modalRef = this.modalService.show(template, {class: 'modal-lg'});
    }

    openEditSubscriptionModal(template: TemplateRef<any>, subscription: SubscriptionModel): void {
        this.editableSubscription = SubscriptionModel.cloneSubscription(subscription);
        this.modalRef = this.modalService.show(template, {class: 'modal-lg'});
    }

    closeEditSubscriptionModal(): void {
        this.loadSubscriptions();
        this.modalRef.hide();
    }

    deleteSubscription(id: string): void {
        this.subscriptionsService.deleteSubscription(id).subscribe(() => {
            this.loadSubscriptions();
        })
    }

    fillUp(): void {
        const billingAccount = this.owner.billingAccount;
        billingAccount.balance += this.amount;
        this.baService.addMoneyOnBillingAccount(billingAccount.balance).subscribe(() => {
            this.loadOwner();
            this.amount = 0;
            this.modalRef.hide();
        });
    }

    amountIsNegative(): boolean {
        return this.amount < 0;
    }
}
