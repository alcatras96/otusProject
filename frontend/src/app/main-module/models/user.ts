import {RoleModel} from "./role.model";

export class User {
    id: string;
    login: string;
    email: string;
    password: string;
    role: RoleModel = new RoleModel();

    static cloneUser(user: User): User {
        if (!user) {
            return null;
        }

        let clonedUser: User = new User();
        clonedUser.id = user.id;
        clonedUser.login = user.login;
        clonedUser.email = user.email;
        clonedUser.password = user.password;
        clonedUser.role = RoleModel.cloneRole(user.role);
        return clonedUser;
    }
}
