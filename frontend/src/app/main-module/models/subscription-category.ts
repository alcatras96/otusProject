export class SubscriptionCategory {

    id: string;
    name: string;

    static cloneCategory(category: SubscriptionCategory): SubscriptionCategory {
        let cloneCategory: SubscriptionCategory = new SubscriptionCategory();
        cloneCategory.id = category.id;
        cloneCategory.name = category.name;
        return cloneCategory;
    }
}
