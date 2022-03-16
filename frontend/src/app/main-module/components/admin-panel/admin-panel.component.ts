import {Component, OnInit, TemplateRef} from '@angular/core';
import {CustomerModel} from "../../models/customer.model";
import {CustomerService} from "../../../services/customer.service";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {NgxSpinnerService} from "ngx-spinner";
import {PageChangedEvent} from "ngx-bootstrap/pagination";

@Component({
    selector: 'app-admin-panel',
    templateUrl: './admin-panel.component.html',
    styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {

    bsModalRef: BsModalRef;
    isCollapsed: boolean = false;

    customers: CustomerModel[] = [];
    editableCustomer: CustomerModel;
    size: number = 10;
    totalElements: number = 0;
    page: number = 0;

    constructor(private loadingService: NgxSpinnerService, private customersService: CustomerService,
                private modalService: BsModalService) {
    }

    ngOnInit() {
        this.loadCustomers(this.page, this.size);
    }

    private loadCustomers(page: number, size: number): void {
        this.loadingService.show();
        this.customersService.getCustomers(page, size).subscribe(source => {
            this.customers = source.content;
            this.totalElements = source.totalElements;
            this.loadingService.hide();
        });
    }

    public deleteCustomer(id: string): void {
        this.customersService.deleteCustomer(id).subscribe(() => {
            this.loadCustomers(this.page, this.size);
        });
    }

    openCustomerEditModal(template: TemplateRef<any>, customer: CustomerModel) {
        this.editableCustomer = CustomerModel.cloneCustomer(customer);
        this.bsModalRef = this.modalService.show(template, {class: 'modal-lg'});
    }

    closeEditCustomerModal(): void {
        this.loadCustomers(this.page, this.size);
        this.bsModalRef.hide();
    }

    confirmDeleteCustomer(id: string) {
        this.deleteCustomer(id);
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
        this.loadCustomers(this.page, this.size);
    }

    collapse(isCollapsed: boolean): void {
        this.isCollapsed = isCollapsed;
    }
}
