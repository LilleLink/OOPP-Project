package model;

import java.util.HashMap;
import java.util.Map;

/***
 * A factory that creates specific data packages depending on what method is called.
 */
public final class StatisticsUtils {

    private StatisticsUtils() {
    }

    /***
     * Creates a hashmap containing the ITags and the number of events tagged on it. Serves as a base for creating
     * statistics based on the data.
     * @param eventList the list containing the events that the statistics should be based on.
     * @param tagHandler the taghandler containing the tags the statistics should be based on
     * @return the hashmap containing the data.
     */
    public static Map<ITag, Integer> getEventDelegation(EventList eventList, TagHandler tagHandler) {
        HashMap<ITag, Integer> res = new HashMap<>();
        for (ITag tag : tagHandler.getAllTags()) {
            res.put(tag, eventList.getEventsOfTag(tag).size());
        }
        return res;
    }


}
