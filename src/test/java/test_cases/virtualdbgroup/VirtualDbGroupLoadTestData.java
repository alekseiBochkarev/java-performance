package test_cases.virtualdbgroup;

import base.BaseAuthData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.jmeter.protocol.http.util.HTTPConstants;
import org.apache.jmeter.threads.JMeterVariables;
import org.junit.jupiter.api.AfterAll;
import test_cases.virtualdb.VirtualDbLoadTestData;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;
import us.abstracta.jmeter.javadsl.http.DslHttpSampler;
import util.CommonUtil;
import util.JmeterUtil;
import util.virtualdbgroup.VirtualDbGroupLoadTestClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.abstracta.jmeter.javadsl.JmeterDsl.*;
import static util.CommonUtil.*;

public class VirtualDbGroupLoadTestData extends BaseAuthData {

    private final ObjectMapper mapper = new ObjectMapper();
    VirtualDbGroupLoadTestClient virtualDbGroupLoadTestClient = new VirtualDbGroupLoadTestClient();
    VirtualDbLoadTestData virtualDbLoadTestData = new VirtualDbLoadTestData();
    static JmeterUtil jmeterUtil = new JmeterUtil();

    private static final String xTenantId = "user_platform";
    private String jsonExtractorVirtualDbGroupId = "virtualDbGroupId";
    private String jsonExtractorVirtualDbGroupIdPath = "id";
    private String jsonExtractorVirtualDbGroupIds = "ids";
    private String jsonExtractorVirtualDbGroupIdsPath = "data[:" + itemQuantity + "].id";

    public String getJsonExtractorVirtualDbGroupId() {
        return jsonExtractorVirtualDbGroupId;
    }

    public static List<String> virtualDbGroupIdsForDelete = new ArrayList<>();

    private String successStatusCodeAfterCreate = "200";
    private static String successStatusCodeAfterDelete = "204";
    private static String successStatusCodeAfterDeleteBatch = "200";

    public static Consumer<List<String>> removeVirtualDbGroupIds = list -> {
        try {
            deleteVirtualDbGroupIdsAfterTests(list);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

    @AfterAll
    static void cleanUp () {
        if (DELETE_DATA_AFTER_TESTS) {
            /** remove virtualDbGroup **/
            System.out.println("after all, virtualDbGroup ids before removing: ");
            printIds.accept(virtualDbGroupIdsForDelete);
            List<String> virtualDbGroupIdsForDeleteAdd = virtualDbGroupIdsForDelete.stream().collect(Collectors.toList());
            removeVirtualDbGroupIds.accept(virtualDbGroupIdsForDeleteAdd);
            System.out.println("after all, virtualDbGroup ids after removing");
            System.out.println(virtualDbGroupIdsForDelete);
        }
    }

    static void deleteVirtualDbGroupIdsAfterTests(List<String> virtualDbGroupIds) throws IOException {
        System.out.println(virtualDbGroupIds.size());
        TestPlanStats stats = testPlan(threadGroup(1,1, getHttpSamplerForDeleteVirtualDbGroupBatch(virtualDbGroupIds)
                //  , resultsTreeVisualizer()
        )).run();
        assertEquals(0, stats.overall().errorsCount());
    }

    /*** get supporting http sampler for create virtual db ***/
    protected DslHttpSampler getHttpSamplerForCreateVirtualDb() {
        return virtualDbLoadTestData.getHttpSamplerForCreateVirtualDb();
    }

    /***Create samplers ***/
    public DslHttpSampler getHttpSamplerForCreateVirtualDbGroup() {
        DslHttpSampler httpSampler =
                jmeterUtil.getBaseHttpSampler("Create virtualDbGroup",
                        getVirtualDbGroupUrl(),
                        HTTPConstants.POST,
                        xTenantId);
        httpSampler
                .body("${REQUEST_BODY}")
                .children(
                        jsr223PreProcessor(s -> s.vars.put("REQUEST_BODY",
                                buildRequestBodyForCreate(s.vars))),
                        jsonExtractor(jsonExtractorVirtualDbGroupId,
                                jsonExtractorVirtualDbGroupIdPath),
                        jsr223PostProcessor(s -> {
                            if (successStatusCodeAfterCreate.equals(s.prev.getResponseCode())) {
                                s.prev.setSuccessful(true);
                                CommonUtil.addIdForDeleteList(jsonExtractorVirtualDbGroupId, virtualDbGroupIdsForDelete);
                            }
                        })
                );
        return httpSampler;
    }

    protected DslHttpSampler getHttpSamplerForCreateVirtualDbGroupBatch() {
        String url = getVirtualDbGroupUrl() + "/batch";
        DslHttpSampler httpSampler = jmeterUtil.getBaseHttpSampler("Create batch virtualDbGroup",
                url,
                HTTPConstants.POST,
                xTenantId);
        httpSampler
                .body("${REQUEST_BODY_CREATE_BATCH}")
                .children(
                        jsr223PreProcessor(s -> s.vars.put("REQUEST_BODY_CREATE_BATCH",
                                buildRequestBodyForCreateBatch())),
                        jsonExtractor(jsonExtractorVirtualDbGroupIds,
                                jsonExtractorVirtualDbGroupIdsPath)
                                .matchNumber(-1),
                        jsr223PostProcessor(s -> {
                            if (successStatusCodeAfterCreate.equals(s.prev.getResponseCode())) {
                                s.prev.setSuccessful(true);
                                CommonUtil.addIdsForDeleteListForBatch(jsonExtractorVirtualDbGroupIds, virtualDbGroupIdsForDelete, itemQuantity);
                            }
                        })
                );
        return httpSampler;
    }

    /***Update samplers ***/
    protected DslHttpSampler getHttpSamplerForUpdateVirtualDbGroup() {
        String url = getVirtualDbGroupUrl()
                + "/${"
                + jsonExtractorVirtualDbGroupId
                + "}";
        DslHttpSampler httpSampler =
                jmeterUtil.getBaseHttpSampler("Update virtualDbGroup",
                        url,
                        HTTPConstants.PUT,
                        xTenantId);
        httpSampler
                .body("${REQUEST_BODY_UPDATE}")
                .children(
                        jsr223PreProcessor(s -> s.vars.put("REQUEST_BODY_UPDATE",
                                buildRequestBodyForUpdate(s.vars)))
                );
        return httpSampler;
    }

    protected DslHttpSampler getHttpSamplerForUpdateVirtualDbGroupBatch() {
        String url = getVirtualDbGroupUrl() + "/batch";
        DslHttpSampler httpSampler = jmeterUtil.getBaseHttpSampler("Update virtualDbGroup batch",
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
    protected DslHttpSampler getHttpSamplerForDeleteVirtualDbGroup() {
        String url = getVirtualDbGroupUrl()
                + "/${"
                + jsonExtractorVirtualDbGroupId
                + "}";
        return jmeterUtil.getBaseHttpSampler("Delete virtualDbGroup",
                url,
                HTTPConstants.DELETE,
                xTenantId)
                .children(jsr223PostProcessor(s -> {
                    if (successStatusCodeAfterDelete.equals(s.prev.getResponseCode())) {
                        s.prev.setSuccessful(true);
                        extractAndRemoveIdFromDeleteList(jsonExtractorVirtualDbGroupId, virtualDbGroupIdsForDelete);
                    }
                }));
    }

    protected DslHttpSampler getHttpSamplerForDeleteVirtualDbGroupBatch() {
        String url = getVirtualDbGroupUrl() + buildParamsForDeleteBatch(
                jsonExtractorVirtualDbGroupIds);
        return jmeterUtil.getBaseHttpSampler("Delete batch virtualDbGroup",
                url,
                HTTPConstants.DELETE,
                xTenantId)
                .children(jsr223PostProcessor(s -> {
                    if (successStatusCodeAfterDeleteBatch.equals(s.prev.getResponseCode())) {
                        s.prev.setSuccessful(true);
                        extractAndRemoveIdsFromDeleteListForBatch(jsonExtractorVirtualDbGroupIds, virtualDbGroupIdsForDelete, itemQuantity);
                    }
                }));
    }

    protected static DslHttpSampler getHttpSamplerForDeleteVirtualDbGroupBatch(List<String> idsForDelete) {
        String url = getVirtualDbGroupUrl() + CommonUtil.buildParamsForDeleteBatch(idsForDelete);
        return jmeterUtil.getBaseHttpSampler("Delete batch virtualDbGroup after tests",
                        url,
                        HTTPConstants.DELETE,
                        xTenantId)
                .children(jsr223PostProcessor(s -> {
                    if (successStatusCodeAfterDeleteBatch.equals(s.prev.getResponseCode())) {
                        s.prev.setSuccessful(true);
                        extractAndRemoveIdsFromDeleteListForBatch(idsForDelete, virtualDbGroupIdsForDelete);
                    }
                }));
    }

    /***Patch samplers ***/
    protected DslHttpSampler getHttpSamplerForPatchVirtualDbGroup() {
        String url = getVirtualDbGroupUrl()
                + "/${"
                + jsonExtractorVirtualDbGroupId
                + "}";
        DslHttpSampler httpSampler =
                jmeterUtil.getBaseHttpSampler("Patch virtualDbGroup",
                        url,
                        HTTPConstants.PATCH,
                        xTenantId);
        httpSampler
                .body("${REQUEST_BODY_PATCH}")
                .children(
                        jsr223PreProcessor(s -> s.vars.put("REQUEST_BODY_PATCH",
                                buildRequestBodyForPatch()))
                );
        return httpSampler;
    }

    protected DslHttpSampler getHttpSamplerForUpdateVirtualDbGroupPatchBatch() {
        String url = getVirtualDbGroupUrl() + "/batch";
        DslHttpSampler httpSampler =
                jmeterUtil.getBaseHttpSampler("Update virtualDbGroup patch batch",
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
    protected DslHttpSampler getHttpSamplerForGetVirtualDbGroups() {
        return jmeterUtil.getBaseHttpSampler("Get virtualDbGroups",
                getVirtualDbGroupUrl(),
                HTTPConstants.GET,
                xTenantId);
    }

    protected DslHttpSampler getHttpSamplerForFindVirtualDbGroup() {
        String url = getVirtualDbGroupUrl()
                + "/${"
                + jsonExtractorVirtualDbGroupId
                + "}";
        return jmeterUtil.getBaseHttpSampler("Find virtualDbGroup",
                url,
                HTTPConstants.GET,
                xTenantId);
    }

    @SneakyThrows
    private String buildRequestBodyForPatch() {
        return this.mapper.writeValueAsString(virtualDbGroupLoadTestClient.getVirtualDbGroupPatchDto());
    }

    @SneakyThrows
    private String buildRequestBodyForCreate(JMeterVariables vars) {
        return this.mapper.writeValueAsString(virtualDbGroupLoadTestClient.getVirtualDbGroupCreateDto());
    }

    @SneakyThrows
    private String buildRequestBodyForUpdate(JMeterVariables vars) {
        return this.mapper.writeValueAsString(virtualDbGroupLoadTestClient.getVirtualDbGroupUpdateDto(buildRequestBodyForUpdate()));
    }

    @SneakyThrows
    public String buildRequestBodyForCreateBatch() {
        StringBuilder body = new StringBuilder("[");
        for (int x = 1; x <= itemQuantity; x = x + 1) {
            body.append(this.mapper.writeValueAsString(virtualDbGroupLoadTestClient.getVirtualDbGroupCreateDto()))
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
                    .append(getVariable(jsonExtractorVirtualDbGroupIds + "_" + x))
                    .append("\":")
                    .append(this.mapper.writeValueAsString(virtualDbGroupLoadTestClient.getVirtualDbGroupUpdateDto(buildRequestBodyForUpdate())))
                    .append(",");
        }
        return body.substring(0, body.length() - 1) + "}";
    }

    @SneakyThrows
    public String buildRequestBodyForUpdatePatchBatch() {
        StringBuilder body = new StringBuilder("{");
        for (int x = 1; x <= itemQuantity; x = x + 1) {
            body.append("\"")
                    .append(getVariable(jsonExtractorVirtualDbGroupIds + "_" + x))
                    .append("\":")
                    .append(this.mapper.writeValueAsString(virtualDbGroupLoadTestClient.getVirtualDbGroupPatchDto()))
                    .append(",");
        }
        return body.substring(0, body.length() - 1) + "}";
    }

    @SneakyThrows
    public List<UUID> buildRequestBodyForUpdate() {
        List<UUID> uuids = new ArrayList<>();
        String request = getVariable(virtualDbLoadTestData.getJsonExtractorVirtualDbId());
        uuids.add(UUID.fromString(request));
        return uuids;
    }
}
