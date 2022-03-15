import {Component, EventEmitter, Input, Output} from '@angular/core';
import {OwnerModel} from "../../../models/owner.model";
import {OwnerService} from "../../../../services/owner.service";

@Component({
    selector: 'modal-edit-owner',
    templateUrl: './edit-owner-modal.component.html'
})
export class EditOwnerModalComponent {

    @Input() editableOwner: OwnerModel = new OwnerModel();
    @Output() onChanged = new EventEmitter();

    constructor(private ownersService: OwnerService) {
    }

    saveOwner() {
        this.ownersService.updateOwnerDetails(this.editableOwner).subscribe(() => {
            this.onChanged.emit();
        });
    }
}
