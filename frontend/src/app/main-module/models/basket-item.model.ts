import {SubscriptionModel} from "./subscription.model";

export class BasketItemModel {

    id: string;
    customerId: string;
    subscription: SubscriptionModel = new SubscriptionModel();
    quantity: number;

    constructor(customerId: string, subscription: SubscriptionModel, quantity: number) {
        this.customerId = customerId;
        this.subscription = new SubscriptionModel();
        this.subscription.id = subscription.id;
        this.quantity = quantity;
    }
}


