import {User} from "./user";
import {BillingAccountModel} from "./billing-account.model";
import {Status} from "./status";

export class CustomerModel {
    id: string;
    name: string;
    address: string;
    user: User = new User();
    billingAccount: BillingAccountModel;
    status: Status;

    constructor() {
        if (localStorage.getItem('wallet')) {
            this.billingAccount = new BillingAccountModel();
        }
    }

    static cloneCustomer(customer: CustomerModel): CustomerModel {
        let cloneCustomer: CustomerModel = new CustomerModel();
        cloneCustomer.id = customer.id;
        cloneCustomer.name = customer.name;
        cloneCustomer.address = customer.address;
        cloneCustomer.user = customer.user;
        cloneCustomer.billingAccount = customer.billingAccount;
        cloneCustomer.user = User.cloneUser(customer.user);
        cloneCustomer.status = customer.status;
        return cloneCustomer;
    }
}
