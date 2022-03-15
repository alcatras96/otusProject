import {Component, EventEmitter, Input, Output} from '@angular/core';
import {CustomerModel} from "../../../models/customer.model";
import {CustomerService} from "../../../../services/customer.service";

@Component({
    selector: 'modal-edit-customer',
    templateUrl: './edit-customer-modal.component.html'
})
export class EditCustomerModalComponent {


    @Input() editableCustomer: CustomerModel = new CustomerModel();
    @Output() onChanged = new EventEmitter();

    constructor(private customersService: CustomerService) {
    }

    saveCustomer() {
        this.customersService.updateCustomerDetails(this.editableCustomer).subscribe(() => {
            this.onChanged.emit();
        });
    }
}
