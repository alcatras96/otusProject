import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Owner} from "../../../models/owner";
import {OwnerService} from "../../../../services/owner.service";

@Component({
    selector: 'modal-editOwnerMenu',
    templateUrl: './edit-owner-modal.component.html'
})
export class EditOwnerModalComponent {

    @Input() editableOwner: Owner = new Owner();
    @Output() onChanged = new EventEmitter();

    constructor(private ownersService: OwnerService) {
    }

    saveOwner() {
        this.ownersService.saveEditedOwner(this.editableOwner).subscribe(() => {
            this.onChanged.emit();
        });
    }

}
