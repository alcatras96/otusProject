export class StatusModel {

    id: string;
    name: string;

    static cloneStatus(status: StatusModel): StatusModel {
        if (!status) {
            return null;
        }

        let cloneStatus: StatusModel = new StatusModel();
        cloneStatus.id = status.id;
        cloneStatus.name = status.name;
        return cloneStatus;
    }
}
