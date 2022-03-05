import {User} from "./user";
import {BillingAccount} from "./billing-account";

export class Owner {
    id: string;
    name: string;
    user: User = new User();
    billingAccount: BillingAccount;

    constructor() {
        if (localStorage.getItem('wallet')) {
            this.billingAccount = new BillingAccount();
        }
    }

    static cloneOwner(owner: Owner): Owner {
        let cloneOwner: Owner = new Owner();
        cloneOwner.id = owner.id;
        cloneOwner.name = owner.name;
        cloneOwner.user = owner.user;
        cloneOwner.billingAccount = owner.billingAccount;
        cloneOwner.user = User.cloneUser(owner.user);
        return cloneOwner;
    }
}
