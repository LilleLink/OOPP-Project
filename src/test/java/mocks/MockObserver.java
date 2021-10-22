package mocks;

import model.IObserver;

public class MockObserver implements IObserver {
    private int eventCount;

    public MockObserver() {
        eventCount = 0;
    }

    public void resetEventCount() {
        eventCount = 0;
    }

    public int getEventCount() {
        return eventCount;
    }

    @Override
    public void onEvent() {
        eventCount++;
    }
}
