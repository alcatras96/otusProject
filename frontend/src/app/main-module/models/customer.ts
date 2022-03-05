import {User} from "./user";
import {BillingAccount} from "./billing-account";
import {Status} from "./status";

export class Customer {
    id: string;
    name: string;
    address: string;
    user: User = new User();
    billingAccount: BillingAccount;
    status: Status = new Status();

    constructor() {
        if (localStorage.getItem('wallet')) {
            this.billingAccount = new BillingAccount();
        }
    }

    static cloneCustomer(customer: Customer): Customer {
        let cloneCustomer: Customer = new Customer();
        cloneCustomer.id = customer.id;
        cloneCustomer.name = customer.name;
        cloneCustomer.address = customer.address;
        cloneCustomer.user = customer.user;
        cloneCustomer.billingAccount = customer.billingAccount;
        cloneCustomer.user = User.cloneUser(customer.user);
        cloneCustomer.status = Status.cloneStatus(customer.status);
        return cloneCustomer;
    }
}
