import {Subscription} from "./subscription";

export class ActiveSubscription {

    id: string;
    customerId: number;
    subscription: Subscription;
    quantity: number;

    constructor(subscription: Subscription, quantity: number) {
        this.subscription = subscription;
        this.quantity = quantity;
    }
}
