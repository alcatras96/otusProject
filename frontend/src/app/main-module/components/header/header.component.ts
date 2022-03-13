import {Component, EventEmitter, Input, Output, TemplateRef} from '@angular/core';
import {WalletModalComponent} from "./wallet-modal/wallet.component";
import {AuthService} from "../../../services/auth.service";
import {Router} from "@angular/router";
import {BasketItemService} from "../../../services/basket-item.service";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css']
})
export class HeaderComponent {

    private bsModalRef: BsModalRef;
    @Input() itemsCounter: number;
    @Output() onSearch = new EventEmitter<string>();
    searchValue: string = '';

    constructor(private modalService: BsModalService, private authService: AuthService,
                private router: Router, private sbService: BasketItemService) {
    }

    openModalWithLogin(template: TemplateRef<any>) {
        this.bsModalRef = this.modalService.show(template);
    }

    openModalWithWallet() {
        this.bsModalRef = this.modalService.show(WalletModalComponent);
    }

    logout() {
        this.authService.logout();
        this.router.navigateByUrl('/');
    }

    closeLoginModal(): void {
        if (localStorage.getItem('currentUserRole') == 'customer') {
            this.sbService.getCount().subscribe(count => {
                this.itemsCounter = count;
            });
        }
        this.bsModalRef.hide();
    }

    userIsPresent(): boolean {
        return (localStorage.getItem('currentUser') != null);
    }

    walletIsPresent(): boolean {
        return localStorage.getItem('wallet') != 'unregistered';

    }

    getUserRole(): string {
        return localStorage.getItem('currentUserRole');
    }

    accountInfo(): string {
        switch (this.getUserRole()) {
            case "customer": {
                return "customerAccountInfo/" + localStorage.getItem('customerId');
            }
            case "owner": {
                return "ownerAccountInfo/" + localStorage.getItem('ownerId');
            }
            default: {
                return "/404";
            }
        }
    }

    notMainPage(): boolean {
        return this.router.url != '/';
    }

    search(): void {
        this.onSearch.emit(this.searchValue);
    }

    keyDownFunction(event: { keyCode: number; }) {
        if (event.keyCode == 13) {
            this.search();
        }
    }

}
