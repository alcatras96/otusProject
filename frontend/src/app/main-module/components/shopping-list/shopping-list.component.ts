import {Component, OnInit, TemplateRef} from '@angular/core';
import {BasketItem} from "../../models/basket-item";
import {BasketItemService} from "../../../services/basket-item.service";
import {ActiveSubscription} from "../../models/active-subscription";
import {ActiveSubscriptionService} from "../../../services/active-subscription.service";
import {Router} from "@angular/router";
import {ListWrapper} from "../../models/list-wrapper";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {NgxSpinnerService} from "ngx-spinner";
import {finalize} from "rxjs";

@Component({
    selector: 'app-shopping-list',
    templateUrl: './shopping-list.component.html',
    styleUrls: ['./shopping-list.component.css']
})
export class ShoppingListComponent implements OnInit {

    shoppingBasket: BasketItem[] = [];
    total: number = 0;
    itemsCounter: number;
    bsModalRef: BsModalRef;
    private subscriptions: ActiveSubscription[] = [];

    constructor(private loadingService: NgxSpinnerService, private sbService: BasketItemService,
                private activeSubscriptionService: ActiveSubscriptionService, private router: Router,
                private modalService: BsModalService) {
    }

    ngOnInit(): void {
        this.loadShoppingBasket();
    }

    userIsNotBlockedAndHasWallet(): boolean {
        return localStorage.getItem('status') == 'valid' && localStorage.getItem('wallet') != 'unregistered';
    }

    private updateItemsCounter(): void {
        this.sbService.getCount().subscribe(count => {
            this.itemsCounter = count;
        });
    }

    loadShoppingBasket(): void {
        this.loadingService.show();
        this.sbService.getSbByCustomerId().subscribe(shoppingBasket => {
            this.shoppingBasket = shoppingBasket;
            this.updateItemsCounter();
            this.totalCount();
            this.loadingService.hide();
        });
    }

    totalCount(): void {
        this.total = 0;
        for (let shoppingItem of this.shoppingBasket) {
            this.total += shoppingItem.quantity * shoppingItem.subscription.price;
        }
    }

    deleteSbItem(id: string): void {
        this.sbService.deleteSbById(id).subscribe(() => {
            this.updateItemsCounter();
            this.loadShoppingBasket();
        });
    }

    checkout(): void {
        this.shoppingBasket.forEach(item => {
            this.subscriptions.push(new ActiveSubscription(item.subscription, item.quantity));
        });
        this.activeSubscriptionService.saveActiveSubscriptions(new ListWrapper<ActiveSubscription>(this.subscriptions)).pipe(finalize(() => {
            this.subscriptions = [];
        })).subscribe(() => {
            this.sbService.deleteAllSbByCustomerId().subscribe(() => {
                this.router.navigateByUrl('/');
            });
        });
    }

    confirm() {
        this.checkout();
        this.bsModalRef.hide();
    }

    decline() {
        this.bsModalRef.hide();
    }

    openConfirmModal(template: TemplateRef<any>) {
        this.bsModalRef = this.modalService.show(template, {class: 'modal-sm'});
    }
}
