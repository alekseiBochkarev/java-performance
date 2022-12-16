package util;

import org.apache.jmeter.threads.JMeterContextService;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class CommonUtil {

    public static Consumer<List<String>> printIds = list -> {
        for(int i =0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    };

    public static String addIdForDeleteList(String jsonExtractorIdName, List<String> idsForDelete) {
        String id = JMeterContextService.getContext().getVariables()
                .get(jsonExtractorIdName);
        idsForDelete.add(id);
        return id;
    }

    public static void addIdsForDeleteListForBatch(String jsonExtractorIdsName, List<String> idsForDelete, int itemQuantity) {
        for (int x = 1; x <= itemQuantity; x = x + 1) {
            String id = JMeterContextService.getContext().getVariables()
                    .get(jsonExtractorIdsName+ "_" + x);
            idsForDelete.add(id);
        }
        System.out.println(idsForDelete);
    }

    public static void removeIdFromDeleteList(String entityId, List<String> idsForDelete) {
        Iterator<String> entityIdIterator = idsForDelete.listIterator();
        while (entityIdIterator.hasNext()) {
            if (entityIdIterator.next().equals(entityId)) {
                entityIdIterator.remove();
            }
        }
    }

    public static void extractAndRemoveIdFromDeleteList(String jsonExtractorIdName, List<String> idsForDelete) {
        String entityId = JMeterContextService.getContext().getVariables()
                .get(jsonExtractorIdName);
        System.out.println("try remove id " + entityId + " from list");
        removeIdFromDeleteList(entityId, idsForDelete);
    }

    public static void extractAndRemoveIdsFromDeleteListForBatch(String jsonExtractorIdsName, List<String> idsForDelete, int itemQuantity) {
        for (int x = 1; x <= itemQuantity; x = x + 1) {
            String entityId = JMeterContextService.getContext().getVariables()
                    .get(jsonExtractorIdsName +  "_" + x);
            //System.out.println("entityId = " + entityId);
            removeIdFromDeleteList(entityId, idsForDelete);
        }
    }

    public static void extractAndRemoveIdsFromDeleteListForBatch(List<String> concreteIds, List<String> idsForDelete) {
        for (String concreteId:concreteIds) {
            removeIdFromDeleteList(concreteId, idsForDelete);
        }
    }

    public static String buildParamsForDeleteBatch(List<String> ids) {
        StringBuilder params = new StringBuilder("/batch?");
        for (String id:ids) {
            params.append("id=").append(id).append("&");
        }
        return params.substring(0, params.length() - 1);
    }
}
