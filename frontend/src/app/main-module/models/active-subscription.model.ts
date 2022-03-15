import {SubscriptionModel} from "./subscription.model";

export class ActiveSubscriptionModel {

    id: string;
    customerId: number;
    subscription: SubscriptionModel;
    quantity: number;

    constructor(subscription: SubscriptionModel, quantity: number) {
        this.subscription = subscription;
        this.quantity = quantity;
    }
}
