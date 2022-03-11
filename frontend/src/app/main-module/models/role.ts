export class Role {
    id: string;
    name: string;

    static cloneRole(role: Role): Role {
        if (!role) {
            return null;
        }

        let clonedRole: Role = new Role();
        clonedRole.id = role.id;
        clonedRole.name = role.name;
        return clonedRole;
    }
}
