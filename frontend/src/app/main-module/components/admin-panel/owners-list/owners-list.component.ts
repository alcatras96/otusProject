import {Component, OnInit, TemplateRef} from '@angular/core';
import {Owner} from "../../../models/owner";
import {OwnerService} from "../../../../services/owner.service";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {NgxSpinnerService} from "ngx-spinner";
import {PageChangedEvent} from "ngx-bootstrap/pagination";

@Component({
    selector: 'app-ownersList',
    templateUrl: './owners-list.component.html',
    styleUrls: ['./owners-list.component.css']
})
export class OwnersListComponent implements OnInit {

    bsModalRef: BsModalRef;
    isCollapsed: boolean = false;

    owners: Owner[] = [];
    editableOwner: Owner;
    size: number = 10;
    totalElements: number = 0;
    page: number = 0;

    // private subUsers: Subscription[] = [];

    constructor(private loadingService: NgxSpinnerService , private ownersService: OwnerService, private modalService: BsModalService) {
    }

    // Calls on component init
    ngOnInit() {
        this.loadOwners(this.page, this.size);
    }

    private loadOwners(page: number, size: number): void {
        this.loadingService.show();
        this.ownersService.getOwners(page, size).subscribe(source => {
            this.owners = source.content;
            this.totalElements = source.totalElements;
            this.loadingService.hide();
        });
    }

    public deleteOwner(id: string): void {
        this.ownersService.deleteOwner(id).subscribe(() => {
            this.loadOwners(this.page, this.size);
            // this.loadUsers(0, this.size);
        });
    }

    openOwnerEditModal(template: TemplateRef<any>, owner: Owner) {
        this.editableOwner = Owner.cloneOwner(owner);
        this.bsModalRef = this.modalService.show(template, {class: 'modal-lg'});
    }

    closeEditOwnerModal(): void {
        this.loadOwners(this.page, this.size);
        this.bsModalRef.hide();
    }

    confirmDeleteOwner(id: string) {
        this.deleteOwner(id);
        this.bsModalRef.hide();
    }

    decline() {
        this.bsModalRef.hide();
    }

    openConfirmModal(template: TemplateRef<any>) {
        this.bsModalRef = this.modalService.show(template, {class: 'modal-sm'});
    }

    pageChanged(event: PageChangedEvent): void {
        this.page = event.page - 1;
        this.loadOwners(this.page, this.size);
    }

    collapse(isCollapsed: boolean): void {
        this.isCollapsed = isCollapsed;
    }
}