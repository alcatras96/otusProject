export class SubscriptionCategoryModel {

    id: string;
    name: string;

    static cloneCategory(category: SubscriptionCategoryModel): SubscriptionCategoryModel {
        let cloneCategory: SubscriptionCategoryModel = new SubscriptionCategoryModel();
        cloneCategory.id = category.id;
        cloneCategory.name = category.name;
        return cloneCategory;
    }
}
