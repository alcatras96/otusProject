import {User} from "./user";
import {BillingAccountModel} from "./billing-account.model";

export class OwnerModel {
    id: string;
    name: string;
    user: User = new User();
    billingAccount: BillingAccountModel;

    constructor() {
        if (localStorage.getItem('wallet')) {
            this.billingAccount = new BillingAccountModel();
        }
    }

    static cloneOwner(owner: OwnerModel): OwnerModel {
        let cloneOwner: OwnerModel = new OwnerModel();
        cloneOwner.id = owner.id;
        cloneOwner.name = owner.name;
        cloneOwner.user = owner.user;
        cloneOwner.billingAccount = owner.billingAccount;
        cloneOwner.user = User.cloneUser(owner.user);
        return cloneOwner;
    }
}
