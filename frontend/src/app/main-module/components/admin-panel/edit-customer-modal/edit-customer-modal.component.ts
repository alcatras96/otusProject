import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Customer} from "../../../models/customer";
import {CustomerService} from "../../../../services/customer.service";

@Component({
    selector: 'modal-editCustomerMenu',
    templateUrl: './edit-customer-modal.component.html'
})
export class EditCustomerModalComponent {


    @Input() editableCustomer: Customer = new Customer();
    @Output() onChanged = new EventEmitter();

    constructor(private customersService: CustomerService) {
    }

    saveCustomer() {
        // console.log(this.editableOwner.name);
        this.customersService.saveCustomer(this.editableCustomer).subscribe(() => {
            this.onChanged.emit();
        });
    }
}
