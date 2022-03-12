import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Customer} from "../../../models/customer";
import {CustomerService} from "../../../../services/customer.service";

@Component({
    selector: 'modal-edit-customer',
    templateUrl: './edit-customer-modal.component.html'
})
export class EditCustomerModalComponent {


    @Input() editableCustomer: Customer = new Customer();
    @Output() onChanged = new EventEmitter();

    constructor(private customersService: CustomerService) {
    }

    saveCustomer() {
        this.customersService.updateCustomerDetails(this.editableCustomer).subscribe(() => {
            this.onChanged.emit();
        });
    }
}
