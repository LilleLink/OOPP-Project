package model;

import mocks.MockChronological;
import model.notifications.Notifier;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotifierTest {

    List<MockChronological> objects;
    MockChronological object;
    private Notifier<MockChronological> notifier;

    @Before
    public void populateList() {
        objects = new ArrayList<>();
        object = new MockChronological(LocalDateTime.now().plusSeconds(1));
        objects.add(object);
        notifier = new Notifier<>(objects);
    }

    @Test
    public void notificationReceivedTest() throws InterruptedException {
        Thread thread = new Thread(notifier);
        thread.start();
        Thread.sleep(10);
        thread.interrupt();
        List<MockChronological> notifications = notifier.getActive();
        assert(notifications.size() == 1 && notifier.size() == 1);
    }

    @Test
    public void objectNotAddedWhenMutedTest() throws InterruptedException {
        Thread thread = new Thread(notifier);
        thread.start();
        notifier.mute(1000);
        Thread.sleep(100);
        thread.interrupt();
        List<MockChronological> notifications = notifier.getActive();
        assert(notifications.isEmpty());
    }

    @Test
    public void objectAddedAfterUnmuteTest() throws InterruptedException {
        Thread thread = new Thread(notifier);
        thread.start();
        notifier.mute(1000);
        Thread.sleep(100);
        notifier.unmute();
        Thread.sleep(100);
        thread.interrupt();
        List<MockChronological> notifications = notifier.getActive();
        assert(notifications.size() == 1);
    }

    @Test
    public void notificationsAreClearedTest() throws InterruptedException {
        Thread thread = new Thread(notifier);
        thread.start();
        Thread.sleep(10);
        thread.interrupt();
        notifier.clear();
        List<MockChronological> notifications = notifier.getActive();
        assert(notifications.isEmpty());
    }

    @Test
    public void highIntervalAddedTest() throws InterruptedException {
        MockChronological object = new MockChronological(LocalDateTime.now().plusDays(1));
        List<MockChronological> objects = new ArrayList<>();
        objects.add(object);
        notifier.setContent(objects);
        Thread thread = new Thread(notifier);
        notifier.setInterval(1440);
        thread.start();
        Thread.sleep(10);
        List<MockChronological> notifications = notifier.getActive();
        assert(notifications.contains(object));
    }
}
