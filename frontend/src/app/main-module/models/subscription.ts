import {SubscriptionCategory} from "./subscription-category";

export class Subscription {
    id: string;
    name: string;
    imageUrl: string;
    description: string;
    price: number;
    ownerId: string;
    category: SubscriptionCategory;

    static cloneSubscription(subscription: Subscription): Subscription {
        let clonedSubscription: Subscription = new Subscription();
        clonedSubscription.id = subscription.id;
        clonedSubscription.name = subscription.name;
        clonedSubscription.imageUrl = subscription.imageUrl;
        clonedSubscription.description = subscription.description;
        clonedSubscription.price = subscription.price;
        clonedSubscription.ownerId = subscription.ownerId;
        if (subscription.category) {
            clonedSubscription.category = SubscriptionCategory.cloneCategory(subscription.category);
        }
        return clonedSubscription;
    }
}
