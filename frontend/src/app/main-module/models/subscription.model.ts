import {SubscriptionCategoryModel} from "./subscription-category.model";

export class SubscriptionModel {
    id: string;
    name: string;
    imageUrl: string;
    description: string;
    price: number;
    ownerId: string;
    category: SubscriptionCategoryModel;

    static cloneSubscription(subscription: SubscriptionModel): SubscriptionModel {
        let clonedSubscription: SubscriptionModel = new SubscriptionModel();
        clonedSubscription.id = subscription.id;
        clonedSubscription.name = subscription.name;
        clonedSubscription.imageUrl = subscription.imageUrl;
        clonedSubscription.description = subscription.description;
        clonedSubscription.price = subscription.price;
        clonedSubscription.ownerId = subscription.ownerId;
        if (subscription.category) {
            clonedSubscription.category = SubscriptionCategoryModel.cloneCategory(subscription.category);
        }
        return clonedSubscription;
    }
}
