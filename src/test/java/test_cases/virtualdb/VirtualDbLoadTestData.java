package test_cases.virtualdb;

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
import util.virtualdb.VirtualDbLoadTestClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.abstracta.jmeter.javadsl.JmeterDsl.*;
import static util.CommonUtil.*;

public class VirtualDbLoadTestData extends BaseAuthData {

    private final ObjectMapper mapper = new ObjectMapper();
    VirtualDbLoadTestClient virtualDbLoadTestClient = new VirtualDbLoadTestClient();
    static JmeterUtil jmeterUtil = new JmeterUtil();

    private static final String xTenantId = "user_platform";
    private String jsonExtractorVirtualDbId = "virtualDbId";
    private String jsonExtractorVirtualDbIdPath = "id";
    private String jsonExtractorVirtualDbIds = "ids";
    private String jsonExtractorVirtualDbIdsPath = "data[:" + itemQuantity + "].id";

    public String getJsonExtractorVirtualDbId() {
        return jsonExtractorVirtualDbId;
    }

    public static List<String> virtualDbIdsForDelete = new ArrayList<>();

    private String successStatusCodeAfterCreate = "200";
    private static String successStatusCodeAfterDelete = "204";
    private static String successStatusCodeAfterDeleteBatch = "200";

    public static Consumer<List<String>> removeVirtualDbIds = list -> {
        try {
            deleteVirtualDbIdsAfterTests(list);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

    @AfterAll
    static void cleanUp() {
        if (DELETE_DATA_AFTER_TESTS) {
            /** remove virtualDb **/
            System.out.println("after all, virtualDbGroup ids before removing: ");
            printIds.accept(virtualDbIdsForDelete);
            List<String> virtualDbIdsForDeleteAdd = new ArrayList<>(virtualDbIdsForDelete);
            removeVirtualDbIds.accept(virtualDbIdsForDeleteAdd);
            System.out.println("after all, virtualDbGroup ids after removing");
            System.out.println(virtualDbIdsForDelete);
        }
    }

    static void deleteVirtualDbIdsAfterTests(List<String> virtualDbIds) throws IOException {
        System.out.println(virtualDbIds.size());
        TestPlanStats stats =
                testPlan(threadGroup(1, 1, getHttpSamplerForDeleteVirtualDbBatch(virtualDbIds)
                        //  , resultsTreeVisualizer()
                )).run();
        assertEquals(0, stats.overall().errorsCount());
    }

    /***Create samplers ***/
    public DslHttpSampler getHttpSamplerForCreateVirtualDb() {
        DslHttpSampler httpSampler =
                jmeterUtil.getBaseHttpSampler("Create virtualDb",
                        getVirtualDbUrl(),
                        HTTPConstants.POST,
                        xTenantId);
        httpSampler
                .body("${REQUEST_BODY}")
                .children(
                        jsr223PreProcessor(s -> s.vars.put("REQUEST_BODY",
                                buildRequestBodyForCreate(s.vars))),
                        jsonExtractor(jsonExtractorVirtualDbId, jsonExtractorVirtualDbIdPath),
                        jsr223PostProcessor(s -> {
                            if (successStatusCodeAfterCreate.equals(s.prev.getResponseCode())) {
                                s.prev.setSuccessful(true);
                                CommonUtil.addIdForDeleteList(jsonExtractorVirtualDbId,
                                        virtualDbIdsForDelete);
                            }
                        })
                );
        return httpSampler;
    }

    protected DslHttpSampler getHttpSamplerForCreateVirtualDbBatch() {
        String url = getVirtualDbUrl() + "/batch";
        DslHttpSampler httpSampler = jmeterUtil.getBaseHttpSampler("Create batch virtualDb",
                url,
                HTTPConstants.POST,
                xTenantId);
        httpSampler
                .body("${REQUEST_BODY_CREATE_BATCH}")
                .children(
                        jsr223PreProcessor(s -> s.vars.put("REQUEST_BODY_CREATE_BATCH",
                                buildRequestBodyForCreateBatch())),
                        jsonExtractor(jsonExtractorVirtualDbIds, jsonExtractorVirtualDbIdsPath)
                                .matchNumber(-1),
                        jsr223PostProcessor(s -> {
                            if (successStatusCodeAfterCreate.equals(s.prev.getResponseCode())) {
                                s.prev.setSuccessful(true);
                                CommonUtil.addIdsForDeleteListForBatch(jsonExtractorVirtualDbIds,
                                        virtualDbIdsForDelete,
                                        itemQuantity);
                            }
                        })
                );
        return httpSampler;
    }

    /***Update samplers ***/
    protected DslHttpSampler getHttpSamplerForUpdateVirtualDb() {
        String url = getVirtualDbUrl()
                + "/${"
                + jsonExtractorVirtualDbId
                + "}";
        DslHttpSampler httpSampler =
                jmeterUtil.getBaseHttpSampler("Update virtualDb",
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

    protected DslHttpSampler getHttpSamplerForUpdateVirtualDbBatch() {
        String url = getVirtualDbUrl() + "/batch";
        DslHttpSampler httpSampler = jmeterUtil.getBaseHttpSampler("Update virtualDb batch",
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
    protected DslHttpSampler getHttpSamplerForDeleteVirtualDb() {
        String url = getVirtualDbUrl()
                + "/${"
                + jsonExtractorVirtualDbId
                + "}";
        return jmeterUtil.getBaseHttpSampler("Delete virtualDb",
                        url,
                        HTTPConstants.DELETE,
                        xTenantId)
                .children(jsr223PostProcessor(s -> {
                    if (successStatusCodeAfterDelete.equals(s.prev.getResponseCode())) {
                        s.prev.setSuccessful(true);
                        extractAndRemoveIdFromDeleteList(jsonExtractorVirtualDbId,
                                virtualDbIdsForDelete);
                    }
                }));
    }

    protected DslHttpSampler getHttpSamplerForDeleteVirtualDbBatch() {
        String url = getVirtualDbUrl() + buildParamsForDeleteBatch(jsonExtractorVirtualDbIds);
        return jmeterUtil.getBaseHttpSampler("Delete batch virtualDb",
                        url,
                        HTTPConstants.DELETE,
                        xTenantId)
                .children(jsr223PostProcessor(s -> {
                    if (successStatusCodeAfterDeleteBatch.equals(s.prev.getResponseCode())) {
                        s.prev.setSuccessful(true);
                        extractAndRemoveIdsFromDeleteListForBatch(jsonExtractorVirtualDbIds,
                                virtualDbIdsForDelete,
                                itemQuantity);
                    }
                }));
    }

    protected static DslHttpSampler getHttpSamplerForDeleteVirtualDbBatch(List<String> idsForDelete) {
        String url = getVirtualDbUrl() + CommonUtil.buildParamsForDeleteBatch(idsForDelete);
        return jmeterUtil.getBaseHttpSampler("Delete batch virtualDb after tests",
                        url,
                        HTTPConstants.DELETE,
                        xTenantId)
                .children(jsr223PostProcessor(s -> {
                    if (successStatusCodeAfterDeleteBatch.equals(s.prev.getResponseCode())) {
                        s.prev.setSuccessful(true);
                        extractAndRemoveIdsFromDeleteListForBatch(idsForDelete,
                                virtualDbIdsForDelete);
                    }
                }));
    }

    /***Patch samplers ***/
    protected DslHttpSampler getHttpSamplerForPatchVirtualDb() {
        String url = getVirtualDbUrl()
                + "/${"
                + jsonExtractorVirtualDbId
                + "}";
        DslHttpSampler httpSampler =
                jmeterUtil.getBaseHttpSampler("Patch virtualDb",
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

    protected DslHttpSampler getHttpSamplerForUpdateVirtualDbPatchBatch() {
        String url = getVirtualDbUrl() + "/batch";
        DslHttpSampler httpSampler = jmeterUtil.getBaseHttpSampler("Update virtualDb patch batch",
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
    protected DslHttpSampler getHttpSamplerForGetVirtualDbs() {
        return jmeterUtil.getBaseHttpSampler("Get virtualDbs",
                getVirtualDbUrl(),
                HTTPConstants.GET,
                xTenantId);
    }

    protected DslHttpSampler getHttpSamplerForFindVirtualDb() {
        String url = getVirtualDbUrl()
                + "/${"
                + jsonExtractorVirtualDbId
                + "}";
        return jmeterUtil.getBaseHttpSampler("Find virtualDb", url, HTTPConstants.GET, xTenantId);
    }

    @SneakyThrows
    private String buildRequestBodyForPatch() {
        return this.mapper.writeValueAsString(virtualDbLoadTestClient.getVirtualDbPatchDto());
    }

    @SneakyThrows
    private String buildRequestBodyForCreate(JMeterVariables vars) {
        return this.mapper.writeValueAsString(virtualDbLoadTestClient.getVirtualDbCreateDto());
    }

    @SneakyThrows
    private String buildRequestBodyForUpdate(JMeterVariables vars) {
        return this.mapper.writeValueAsString(virtualDbLoadTestClient.getVirtualDbUpdateDto());
    }

    @SneakyThrows
    public String buildRequestBodyForCreateBatch() {
        StringBuilder body = new StringBuilder("[");
        for (int x = 1; x <= itemQuantity; x = x + 1) {
            body.append(this.mapper.writeValueAsString(virtualDbLoadTestClient.getVirtualDbCreateDto()))
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
                    .append(getVariable(jsonExtractorVirtualDbIds + "_" + x))
                    .append("\":")
                    .append(this.mapper.writeValueAsString(virtualDbLoadTestClient.getVirtualDbUpdateDto()))
                    .append(",");
        }
        return body.substring(0, body.length() - 1) + "}";
    }

    @SneakyThrows
    public String buildRequestBodyForUpdatePatchBatch() {
        StringBuilder body = new StringBuilder("{");
        for (int x = 1; x <= itemQuantity; x = x + 1) {
            body.append("\"")
                    .append(getVariable(jsonExtractorVirtualDbIds + "_" + x))
                    .append("\":")
                    .append(this.mapper.writeValueAsString(virtualDbLoadTestClient.getVirtualDbPatchDto()))
                    .append(",");
        }
        return body.substring(0, body.length() - 1) + "}";
    }
}
