import {Subscription} from "./subscription";

export class BasketItem {

    id: string;
    customerId: string;
    subscription: Subscription = new Subscription();
    quantity: number;

    constructor(customerId: string, subscription: Subscription, quantity: number) {
        this.customerId = customerId;
        this.subscription = new Subscription();
        this.subscription.id = subscription.id;
        this.quantity = quantity;
    }
}


