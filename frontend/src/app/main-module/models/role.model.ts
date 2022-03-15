export class RoleModel {
    id: string;
    name: string;

    static cloneRole(role: RoleModel): RoleModel {
        if (!role) {
            return null;
        }

        let clonedRole: RoleModel = new RoleModel();
        clonedRole.id = role.id;
        clonedRole.name = role.name;
        return clonedRole;
    }
}
