import {Component, OnInit, TemplateRef} from '@angular/core';
import {SubscriptionService} from "../../../services/subscription.service";
import {SubscriptionModel} from "../../models/subscription.model";
import {BasketItemService} from "../../../services/basket-item.service";
import {BasketItemModel} from "../../models/basket-item.model";
import {SubscriptionCategoryModel} from "../../models/subscription-category.model";
import {CategoryService} from "../../../services/category.service";
import {CustomerService} from "../../../services/customer.service";
import {ListWrapper} from "../../models/list-wrapper";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {NgxSpinnerService} from "ngx-spinner";
import {PageChangedEvent} from "ngx-bootstrap/pagination";
import {finalize} from "rxjs";
import {Status} from "../../models/status";

@Component({
    selector: 'app-shop',
    templateUrl: './shop.component.html',
    styleUrls: ['./shop.component.css']
})
export class ShopComponent implements OnInit {

    shoppingList: BasketItemModel[] = [];
    subs: SubscriptionModel[];
    bsModalRef: BsModalRef;
    selectedMonthsCountPerSubscription: number[] = [];
    size: number = 12;
    totalElements: number;
    itemsCounter: number;
    categories: SubscriptionCategoryModel[] = [];
    searchValue: string;
    currentCategoryId: string;
    loadOption: number = 1;

    constructor(private loadingService: NgxSpinnerService, private subscriptionsService: SubscriptionService,
                private sbService: BasketItemService, private modalService: BsModalService,
                private categoryService: CategoryService, private customerService: CustomerService) {
    }

    ngOnInit() {
        this.loadSubscriptions(0);
        this.loadCategories();
        if (localStorage.getItem('currentUserRole') == 'customer') {
            this.updateCustomerStatus();
            this.updateItemsCounter();
        }

    }

    private updateCustomerStatus(): void {
        this.customerService.getCustomerByUserId().subscribe(customer => {
            localStorage.setItem('status', customer.status);
        })
    }

    private updateItemsCounter(): void {
        this.sbService.getCount().subscribe(count => {
            this.itemsCounter = count;
        });
    }

    private loadSubscriptions(page: number): void {
        this.loadingService.show();
        this.subscriptionsService.getSubscriptionsPaged(page, this.size).subscribe(source => {
            this.subs = source.content;
            this.totalElements = source.totalElements;
            this.loadingService.hide();
        });
    }

    pageChanged(event: PageChangedEvent): void {
        switch (this.loadOption) {
            case 1: {
                this.loadSubscriptions(event.page - 1);
                break;
            }
            case 2: {
                this.loadSubscriptionsByNameLike(event.page - 1);
                break;
            }
            case 3: {
                this.loadSubscriptionsByCategoryId(event.page - 1);
                break;
            }
        }
        this.selectedMonthsCountPerSubscription = [];
    }

    addToBasket(): void {
        this.loadingService.show();
        let i: number = 0;
        for (let sub of this.subs) {
            if (this.selectedMonthsCountPerSubscription[i] > 0) {
                this.shoppingList.push(new BasketItemModel(localStorage.getItem('customerId'), sub, this.selectedMonthsCountPerSubscription[i]));
            }
            i++;
        }

        this.sbService.saveBasketItem(new ListWrapper<BasketItemModel>(this.shoppingList)).pipe(finalize(() => {
            this.updateItemsCounter();
            this.shoppingList = [];
            this.selectedMonthsCountPerSubscription = [];
            this.loadingService.hide();
        })).subscribe(() => {
        });
    }

    adminOrOwner(): boolean {
        return !(localStorage.getItem('currentUserRole') == 'customer');
    }

    isAddToCardButtonDisabled(): boolean {
        if (localStorage.getItem('wallet') == null || localStorage.getItem('status') == Status.BLOCKED) {
            return true;
        }
        for (let value of this.selectedMonthsCountPerSubscription) {
            if (value > 0 && value < 1000) {
                return false;
            }
        }
        return true;
    }

    confirm() {
        this.addToBasket();
        this.bsModalRef.hide();
    }

    decline() {
        this.bsModalRef.hide();
    }

    openConfirmModal(template: TemplateRef<any>) {
        this.bsModalRef = this.modalService.show(template, {class: 'modal-sm'});
    }

    search(searchValue: string) {
        this.searchValue = searchValue;
        this.loadOption = 2;
        this.loadSubscriptionsByNameLike(0);
    }

    loadSubscriptionsByNameLike(page: number): void {
        this.loadingService.show();
        this.subscriptionsService.getSubscriptionsByNameLike(this.searchValue, page, this.size).subscribe(source => {
            this.subs = source.content;
            this.totalElements = source.totalElements;
            this.loadingService.hide();
        })
    }

    loadCategories(): void {
        this.loadingService.show();
        this.categoryService.getCategories().subscribe(categories => {
            this.categories = categories;
            this.loadingService.hide();
        })
    }

    filter(id: string): void {
        this.currentCategoryId = id;
        this.loadOption = 3;
        this.loadSubscriptionsByCategoryId(0);
    }

    loadSubscriptionsByCategoryId(page: number): void {
        this.loadingService.show();
        this.subscriptionsService.getSubscriptionByCategoryId(this.currentCategoryId, page, this.size).subscribe(source => {
            this.subs = source.content;
            this.totalElements = source.totalElements;
            this.loadingService.hide();
        })
    }

    loadAll(): void {
        this.loadOption = 1;
        this.loadSubscriptions(0);
    }

    walletIsPresent(): boolean {
        const wallet = localStorage.getItem('wallet');
        if (this.adminOrOwner() || wallet == null) {
            return true;
        }
        return wallet != 'unregistered';
    }

    customerIsNotBlocked(): boolean {
        return localStorage.getItem('status') != Status.BLOCKED;
    }
}
