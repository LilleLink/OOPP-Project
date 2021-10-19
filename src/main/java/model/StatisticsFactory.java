package model;

import java.util.HashMap;

public final class StatisticsFactory {

    public static HashMap<String, Integer> getEventDelegation(EventList eventList, TagHandler tagHandler) {
        HashMap<String, Integer> res = new HashMap<>();
        for (ITag tag : tagHandler.getAllTags()) {
            res.put(tag.getName(), 0);
        }

        for (Event event : eventList.getList()) {
            res.replace(event.getTag().getName(), res.get(event.getTag().getName()) + 1);
        }

        return res;
    }

}
