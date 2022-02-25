package front.users.edit;

import front.users.add.AddUserEvent;

public interface EditUserListener {
    public void userEventOccurred(AddUserEvent editUserEvent);
}
