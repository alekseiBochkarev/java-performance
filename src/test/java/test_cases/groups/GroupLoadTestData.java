package test_cases.groups;

import base.BaseAuthData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.jmeter.protocol.http.util.HTTPConstants;
import org.apache.jmeter.threads.JMeterVariables;
import org.junit.jupiter.api.AfterAll;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;
import us.abstracta.jmeter.javadsl.http.DslHttpSampler;
import util.CommonUtil;
import util.JmeterUtil;
import util.group.GroupLoadTestClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.abstracta.jmeter.javadsl.JmeterDsl.*;
import static util.CommonUtil.*;
import static util.RandomUtil.getRandomIndex;

public class GroupLoadTestData extends BaseAuthData {

    private final ObjectMapper mapper = new ObjectMapper();
    GroupLoadTestClient groupLoadTestClient = new GroupLoadTestClient();
    static JmeterUtil jmeterUtil = new JmeterUtil();

    private static final String xTenantId = "user_platform";
    private String jsonExtractorGroupId = "groupId";
    private String jsonExtractorGroupIdPath = "id";
    private String jsonExtractorGroupIds = "ids";
    private String jsonExtractorGroupIdsPath = "data[:" + itemQuantity + "].id";

    public String getJsonExtractorGroupIds() {
        return jsonExtractorGroupIds;
    }

    public String getJsonExtractorGroupId() {
        return jsonExtractorGroupId;
    }

    public static List<String> groupIdsForDelete = new ArrayList<>();

    private String successStatusCodeAfterCreate = "200";
    private static String successStatusCodeAfterDelete = "204";
    private static String successStatusCodeAfterDeleteBatch = "200";

    public static Consumer<List<String>> removeGroupIds = list -> {
        try {
            deleteGroupIdsAfterTests(list);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

    public static Consumer<List<String>> removeGroupIdsEachOver = list -> {
        Iterator<String> groupIdIterator = list.listIterator();
        while (groupIdIterator.hasNext()) {
            try {
                deleteGroupIdAfterTests(groupIdIterator.next());
            }
            catch (IOException e) {
                System.err.println(e);
                throw new RuntimeException(e);
            }
        }
    };

    @AfterAll
    static void cleanUp () {
        if (DELETE_DATA_AFTER_TESTS) {
            System.out.println("after all, GROUP ids before removing: ");
            printIds.accept(groupIdsForDelete);
            List<String> groupIdsForDeleteAdd = groupIdsForDelete.stream().collect(Collectors.toList());
            removeGroupIds.accept(groupIdsForDeleteAdd);
            System.out.println("after all, group ids after removing");
            System.out.println(groupIdsForDelete);
        }
    }

    static void deleteGroupIdAfterTests(String groupId) throws IOException {
        TestPlanStats stats = testPlan(threadGroup(1,1, getHttpSamplerForDeleteGroup(groupId))).run();
        assertEquals(0, stats.overall().errorsCount());
    }

    static void deleteGroupIdsAfterTests(List<String> groupIds) throws IOException {
        System.out.println(groupIds.size());
        TestPlanStats stats = testPlan(threadGroup(1,1, getHttpSamplerForDeleteGroupBatch(groupIds)
              //  , resultsTreeVisualizer()
        )).run();
        assertEquals(0, stats.overall().errorsCount());
    }

    /***Create samplers ***/
    public DslHttpSampler getHttpSamplerForCreateGroup() {
        DslHttpSampler httpSampler =
                jmeterUtil.getBaseHttpSampler("Create group",
                        getGroupUrl(),
                        HTTPConstants.POST,
                        xTenantId);
        httpSampler
                .body("${REQUEST_BODY}")
                .children(
                        jsr223PreProcessor(s -> s.vars.put("REQUEST_BODY",
                                buildRequestBodyForCreate(s.vars))),
                        jsonExtractor(jsonExtractorGroupId, jsonExtractorGroupIdPath),
                        jsr223PostProcessor(s -> {
                            if (successStatusCodeAfterCreate.equals(s.prev.getResponseCode())) {
                                s.prev.setSuccessful(true);
                                CommonUtil.addIdForDeleteList(jsonExtractorGroupId, groupIdsForDelete);
                            }
                        })
                );
        return httpSampler;
    }

    public DslHttpSampler getHttpSamplerForCreateGroupBatch() {
        String url = getGroupUrl() + "/batch";
        DslHttpSampler httpSampler = jmeterUtil.getBaseHttpSampler("Create batch group",
                url,
                HTTPConstants.POST,
                xTenantId);
        httpSampler
                .body("${REQUEST_BODY_CREATE_BATCH}")
                .children(
                        jsr223PreProcessor(s -> s.vars.put("REQUEST_BODY_CREATE_BATCH",
                                buildRequestBodyForCreateBatch())),
                        jsonExtractor(jsonExtractorGroupIds, jsonExtractorGroupIdsPath)
                                .matchNumber(-1),
                        jsr223PostProcessor(s -> {
                            if (successStatusCodeAfterCreate.equals(s.prev.getResponseCode())) {
                                s.prev.setSuccessful(true);
                                CommonUtil.addIdsForDeleteListForBatch(jsonExtractorGroupIds, groupIdsForDelete, itemQuantity);
                            }
                        })
                );
        return httpSampler;
    }

    /***Update samplers ***/
    protected DslHttpSampler getHttpSamplerForUpdateGroup() {
        String url = getGroupUrl()
                + "/${"
                + jsonExtractorGroupId
                + "}";
        DslHttpSampler httpSampler =
                jmeterUtil.getBaseHttpSampler("Update group", url, HTTPConstants.PUT, xTenantId);
        httpSampler
                .body("${REQUEST_BODY_UPDATE}")
                .children(
                        jsr223PreProcessor(s -> s.vars.put("REQUEST_BODY_UPDATE",
                                buildRequestBodyForUpdate(s.vars)))
                );
        return httpSampler;
    }

    protected DslHttpSampler getHttpSamplerForJustUpdateGroup() {
        String url = getGroupUrl()
                + "/"
                + "${GROUP_ID_REQUEST_PATH}"
                + "";
        DslHttpSampler httpSampler =
                jmeterUtil.getBaseHttpSampler("Update group", url, HTTPConstants.PUT, xTenantId);
        httpSampler
                .body("${REQUEST_BODY_UPDATE}")
                .children(
                        jsr223PreProcessor(s -> s.vars.put("GROUP_ID_REQUEST_PATH", buildGroupIdRequestPath())),
                        jsr223PreProcessor(s -> s.vars.put("REQUEST_BODY_UPDATE",
                                buildRequestBodyForUpdate(s.vars)))
                );
        return httpSampler;
    }

    protected DslHttpSampler getHttpSamplerForUpdateGroupBatch() {
        String url = getGroupUrl() + "/batch";
        DslHttpSampler httpSampler = jmeterUtil.getBaseHttpSampler("Update group batch",
                url,
                HTTPConstants.PUT,
                xTenantId);
        httpSampler
                .body("${REQUEST_BODY_UPDATE_BATCH}")
                .children(
                        jsr223PreProcessor(S -> S.vars.put("REQUEST_BODY_UPDATE_BATCH",
                                buildRequestBodyForUpdateBatch()))
                );
        return httpSampler;
    }

    /***Delete samplers ***/
    protected DslHttpSampler getHttpSamplerForDeleteGroup() {
        String url = getGroupUrl()
                + "/${"
                + jsonExtractorGroupId
                + "}";
        return jmeterUtil.getBaseHttpSampler("Delete group", url, HTTPConstants.DELETE, xTenantId)
                .children(jsr223PostProcessor(s -> {
            if (successStatusCodeAfterDelete.equals(s.prev.getResponseCode())) {
                s.prev.setSuccessful(true);
                extractAndRemoveIdFromDeleteList(jsonExtractorGroupId, groupIdsForDelete);
            }
        }));
    }

    protected DslHttpSampler getHttpSamplerForJustDeleteGroup() {
        String url = getGroupUrl()
                + "/"
                + "${GROUP_ID_REQUEST_PATH}"
                + "";
        return jmeterUtil.getBaseHttpSampler("Delete group", url, HTTPConstants.DELETE, xTenantId)
                .children(
                        jsr223PreProcessor(s -> s.vars.put("GROUP_ID_REQUEST_PATH", buildGroupIdRequestPathForDelete())),
                        jsr223PostProcessor(s -> {
                    if (successStatusCodeAfterDelete.equals(s.prev.getResponseCode())) {
                        s.prev.setSuccessful(true);
                        extractAndRemoveIdFromDeleteList(jsonExtractorGroupId, groupIdsForDelete);
                    }
                }));
    }

    protected static DslHttpSampler getHttpSamplerForDeleteGroup(String groupId) {
        String url = getGroupUrl()
                + "/"  + groupId;
        return jmeterUtil.getBaseHttpSampler("Delete group After Tests",
                        url,
                        HTTPConstants.DELETE,
                        xTenantId)
                .children(jsr223PostProcessor(s -> {
                    if (successStatusCodeAfterDelete.equals(s.prev.getResponseCode())) {
                        s.prev.setSuccessful(true);
                        removeIdFromDeleteList(groupId, groupIdsForDelete);
                    }
                }));
    }

    protected DslHttpSampler getHttpSamplerForDeleteGroupBatch() {
        String url = getGroupUrl() + buildParamsForDeleteBatch(jsonExtractorGroupIds);
        return jmeterUtil.getBaseHttpSampler("Delete batch group",
                url,
                HTTPConstants.DELETE,
                xTenantId)
                .children(jsr223PostProcessor(s -> {
            if (successStatusCodeAfterDeleteBatch.equals(s.prev.getResponseCode())) {
                s.prev.setSuccessful(true);
                extractAndRemoveIdsFromDeleteListForBatch(jsonExtractorGroupIds, groupIdsForDelete, itemQuantity);
            }
        }));
    }

    protected static DslHttpSampler getHttpSamplerForDeleteGroupBatch(List<String> idsForDelete) {
        String url = getGroupUrl() + CommonUtil.buildParamsForDeleteBatch(idsForDelete);
        return jmeterUtil.getBaseHttpSampler("Delete batch group after tests",
                        url,
                        HTTPConstants.DELETE,
                        xTenantId)
                .children(jsr223PostProcessor(s -> {
                    if (successStatusCodeAfterDeleteBatch.equals(s.prev.getResponseCode())) {
                        s.prev.setSuccessful(true);
                        extractAndRemoveIdsFromDeleteListForBatch(idsForDelete, groupIdsForDelete);
                    }
                }));
    }

    /***Patch samplers ***/
    protected DslHttpSampler getHttpSamplerForPatchGroup() {
        String url = getGroupUrl()
                + "/${"
                + jsonExtractorGroupId
                + "}";
        DslHttpSampler httpSampler =
                jmeterUtil.getBaseHttpSampler("Patch group", url, HTTPConstants.PATCH, xTenantId);
        httpSampler
                .body("${REQUEST_BODY_PATCH_GROUP}")
                .children(
                        jsr223PreProcessor(s -> s.vars.put("REQUEST_BODY_PATCH_GROUP",
                                buildRequestBodyForPatch()))
                );
        return httpSampler;
    }

    protected DslHttpSampler getHttpSamplerForJustPatchGroup() {
        String url = getGroupUrl()
                + "/"
                + "${GROUP_ID_REQUEST_PATH}"
                + "";
        DslHttpSampler httpSampler =
                jmeterUtil.getBaseHttpSampler("Patch group", url, HTTPConstants.PATCH, xTenantId);
        httpSampler
                .body("${REQUEST_BODY_PATCH_GROUP}")
                .children(
                        jsr223PreProcessor(s -> s.vars.put("GROUP_ID_REQUEST_PATH", buildGroupIdRequestPath())),
                        jsr223PreProcessor(s -> s.vars.put("REQUEST_BODY_PATCH_GROUP",
                                buildRequestBodyForPatch()))
                );
        return httpSampler;
    }

    protected DslHttpSampler getHttpSamplerForUpdateGroupPatchBatch() {
        String url = getGroupUrl() + "/batch";
        DslHttpSampler httpSampler = jmeterUtil.getBaseHttpSampler("Update group patch batch",
                url,
                HTTPConstants.PATCH,
                xTenantId);
        httpSampler
                .body("${REQUEST_BODY_UPDATE_PATCH_BATCH}")
                .children(
                        jsr223PreProcessor(S -> S.vars.put("REQUEST_BODY_UPDATE_PATCH_BATCH",
                                buildRequestBodyForUpdatePatchBatch()))
                );
        return httpSampler;
    }

    /***GET samplers ***/
    protected DslHttpSampler getHttpSamplerForGetGroups() {
        return jmeterUtil.getBaseHttpSampler("Get groups",
                getGroupUrl(),
                HTTPConstants.GET,
                xTenantId);
    }

    protected DslHttpSampler getHttpSamplerForFindGroup() {
        String url = getGroupUrl()
                + "/${"
                + jsonExtractorGroupId
                + "}";
        return jmeterUtil.getBaseHttpSampler("Find group", url, HTTPConstants.GET, xTenantId);
    }

    protected DslHttpSampler getHttpSamplerForJustFindGroup() {
        String url = getGroupUrl()
                + "/"
                + "${GROUP_ID_REQUEST_PATH}"
                + "";
        return jmeterUtil.getBaseHttpSampler("Find group", url, HTTPConstants.GET, xTenantId)
                .children(
                        jsr223PreProcessor(s -> s.vars.put("GROUP_ID_REQUEST_PATH", buildGroupIdRequestPath())));
    }

    public static String buildGroupIdRequestPath() {
        return groupIdsForDelete.get(getRandomIndex(0, groupIdsForDelete.size()));
    }

    public static String buildGroupIdRequestPathForDelete() {
        return groupIdsForDelete.remove(getRandomIndex(0, groupIdsForDelete.size()));
    }

    @SneakyThrows
    private String buildRequestBodyForPatch() {
        return this.mapper.writeValueAsString(groupLoadTestClient.getGroupPatchDto());
    }

    @SneakyThrows
    private String buildRequestBodyForCreate(JMeterVariables vars) {
        return this.mapper.writeValueAsString(groupLoadTestClient.getGroupCreateDto());
    }

    @SneakyThrows
    private String buildRequestBodyForUpdate(JMeterVariables vars) {
        return this.mapper.writeValueAsString(groupLoadTestClient.getGroupUpdateDto());
    }

    @SneakyThrows
    public String buildRequestBodyForCreateBatch() {
        StringBuilder body = new StringBuilder("[");
        for (int x = 1; x <= itemQuantity; x = x + 1) {
            body.append(this.mapper.writeValueAsString(groupLoadTestClient.getGroupCreateDto()))
                    .append(",");
        }
        return body.substring(0, body.length() - 1) + "]";
    }

    public String buildParamsForDeleteBatch(String jsonExtractorIdsName) {
        StringBuilder params = new StringBuilder("/batch?");
        for (int x = 1; x <= itemQuantity; x = x + 1) {
            params.append("id=${").append(jsonExtractorIdsName).append("_").append(x).append("}&");

        }
        return params.substring(0, params.length() - 1);
    }

    @SneakyThrows
    public String buildRequestBodyForUpdateBatch() {
        StringBuilder body = new StringBuilder("{");
        for (int x = 1; x <= itemQuantity; x = x + 1) {
            body.append("\"")
                    .append(getVariable(jsonExtractorGroupIds + "_" + x))
                    .append("\":")
                    .append(this.mapper.writeValueAsString(groupLoadTestClient.getGroupUpdateDto()))
                    .append(",");
        }
        return body.substring(0, body.length() - 1) + "}";
    }

    @SneakyThrows
    public String buildRequestBodyForUpdatePatchBatch() {
        StringBuilder body = new StringBuilder("{");
        for (int x = 1; x <= itemQuantity; x = x + 1) {
            body.append("\"")
                    .append(getVariable(jsonExtractorGroupIds + "_" + x))
                    .append("\":")
                    .append(this.mapper.writeValueAsString(groupLoadTestClient.getGroupPatchDto()))
                    .append(",");
        }
        return body.substring(0, body.length() - 1) + "}";
    }
}
