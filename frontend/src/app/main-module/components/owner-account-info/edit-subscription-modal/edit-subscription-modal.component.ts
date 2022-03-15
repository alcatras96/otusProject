import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {SubscriptionModel} from "../../../models/subscription.model";
import {SubscriptionService} from "../../../../services/subscription.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SubscriptionCategoryModel} from "../../../models/subscription-category.model";
import {CategoryService} from "../../../../services/category.service";

@Component({
    selector: 'modal-edit-subscription',
    templateUrl: './edit-subscription-modal.component.html',
    styleUrls: ['./edit-subscription-modal.component.css']
})
export class EditSubscriptionModalComponent implements OnInit {

    subscriptionForm: FormGroup;

    @Input() editableSubscription: SubscriptionModel = new SubscriptionModel();
    @Output() onChanged = new EventEmitter();
    categories: SubscriptionCategoryModel[] = [];

    constructor(private subscriptionsService: SubscriptionService, private formBuilder: FormBuilder, private categoryService: CategoryService) {
    }

    submit(): void {
        if (this.editableSubscription.ownerId == null) {
            this.editableSubscription.ownerId = localStorage.getItem('ownerId');
        }

        if(this.editableSubscription.category == null){
            this.editableSubscription.category = new SubscriptionCategoryModel();
        }


        this.categories.forEach(category => {
            if (category.name == this.subscriptionForm.controls['category'].value) {
                this.editableSubscription.category.id = category.id;
            }
        });

        this.subscriptionsService.saveSubscription(this.editableSubscription).subscribe(() => {
            this.onChanged.emit();
        });
    }

    close() {
        this.onChanged.emit();
    }

    ngOnInit(): void {
        this.loadCategories();

        this.subscriptionForm = this.formBuilder.group({
            name: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(20), Validators.pattern("^[a-zA-Z0-9_]+( [a-zA-Z0-9_]+)*$")]],
            price: ['', [Validators.required, Validators.min(1), Validators.max(999)]],
            imageUrl: ['', Validators.required],
            description: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(100)]],
            category: []
        });

        if (this.editableSubscription.category != null) {
            this.subscriptionForm.controls['category'].setValue(this.editableSubscription.category.name);
        } else {
            this.subscriptionForm.controls['category'].setValue('Other');
        }
    }

    loadCategories(): void {
        this.categoryService.getCategories().subscribe(source => {
            this.categories = source;
        })
    }

    equalSubscriptionCategory(category: SubscriptionCategoryModel): boolean {
        if (this.editableSubscription.category == null && category.name == 'Other') {
            return true;
        } else if (this.editableSubscription.category != null && this.editableSubscription.category.id === category.id) {
            return true;
        }
        return false;
    }


}
