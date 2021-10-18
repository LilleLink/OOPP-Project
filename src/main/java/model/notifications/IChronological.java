package model.notifications;

import java.time.LocalDateTime;

public interface IChronological {

    int compareTime(LocalDateTime dateTime);

}
