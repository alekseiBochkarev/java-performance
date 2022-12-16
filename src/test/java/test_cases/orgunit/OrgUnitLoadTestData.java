package test_cases.orgunit;

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
import util.orgunit.OrgUnitLoadTestClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.abstracta.jmeter.javadsl.JmeterDsl.*;
import static util.CommonUtil.*;

public class OrgUnitLoadTestData extends BaseAuthData {

    private final ObjectMapper mapper = new ObjectMapper();
    OrgUnitLoadTestClient orgUnitLoadTestClient = new OrgUnitLoadTestClient();
    static JmeterUtil jmeterUtil = new JmeterUtil();
    private static final String xTenantId = "user_platform";
    private String jsonExtractorOrgUnitId = "orgUnitId";
    private String jsonExtractorOrgUnitIdPath = "id";
    private String jsonExtractorOrgUnitIds = "ids";
    private String jsonExtractorOrgUnitIdsPath = "data[:" + itemQuantity + "].id";

    public String getJsonExtractorOrgUnitId() {
        return jsonExtractorOrgUnitId;
    }

    public static List<String> orgUnitIdsForDelete = new ArrayList<>();

    private String successStatusCodeAfterCreate = "200";
    private static String successStatusCodeAfterDelete = "204";
    private static String successStatusCodeAfterDeleteBatch = "200";

    public static Consumer<List<String>> removeOrgUnitIds = list -> {
        try {
            deleteOrgUnitIdsAfterTests(list);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

    @AfterAll
    static void cleanUp () {
        if (DELETE_DATA_AFTER_TESTS) {
            /** remove orgUnit **/
            System.out.println("after all, orgUnit ids before removing: ");
            printIds.accept(orgUnitIdsForDelete);
            List<String> orgUnitIdsForDeleteAdd = orgUnitIdsForDelete.stream().collect(
                    Collectors.toList());
            removeOrgUnitIds.accept(orgUnitIdsForDeleteAdd);
            System.out.println("after all, orgUnit ids after removing");
            System.out.println(orgUnitIdsForDelete);
        }
    }

    static void deleteOrgUnitIdsAfterTests(List<String> orgUnitIds) throws IOException {
        System.out.println(orgUnitIds.size());
        TestPlanStats stats = testPlan(threadGroup(1,1, getHttpSamplerForDeleteOrgUnitBatch(orgUnitIds)
                //  , resultsTreeVisualizer()
        )).run();
        assertEquals(0, stats.overall().errorsCount());
    }

    /***Create samplers ***/
    public DslHttpSampler getHttpSamplerForCreateOrgUnit() {
        DslHttpSampler httpSampler =
                jmeterUtil.getBaseHttpSampler("Create orgUnit",
                        getOrgUnitUrl(),
                        HTTPConstants.POST,
                        xTenantId);
        httpSampler
                .body("${REQUEST_BODY_ORG_UNIT}")
                .children(
                        jsr223PreProcessor(s -> s.vars.put("REQUEST_BODY_ORG_UNIT",
                                buildRequestBodyForCreate(s.vars))),
                        jsonExtractor(jsonExtractorOrgUnitId, jsonExtractorOrgUnitIdPath),
                        jsr223PostProcessor(s -> {
                            if (successStatusCodeAfterCreate.equals(s.prev.getResponseCode())) {
                                s.prev.setSuccessful(true);
                                CommonUtil.addIdForDeleteList(jsonExtractorOrgUnitId, orgUnitIdsForDelete);
                            }
                        })
                );
        return httpSampler;
    }

    protected DslHttpSampler getHttpSamplerForCreateOrgUnitBatch() {
        String url = getOrgUnitUrl() + "/batch";
        DslHttpSampler httpSampler = jmeterUtil.getBaseHttpSampler("Create batch orgUnit",
                url,
                HTTPConstants.POST,
                xTenantId);
        httpSampler
                .body("${REQUEST_BODY_CREATE_ORG_UNIT_BATCH}")
                .children(
                        jsr223PreProcessor(s -> s.vars.put("REQUEST_BODY_CREATE_ORG_UNIT_BATCH",
                                buildRequestBodyForCreateBatch())),
                        jsonExtractor(jsonExtractorOrgUnitIds, jsonExtractorOrgUnitIdsPath)
                                .matchNumber(-1),
                        jsr223PostProcessor(s -> {
                            if (successStatusCodeAfterCreate.equals(s.prev.getResponseCode())) {
                                s.prev.setSuccessful(true);
                                CommonUtil.addIdsForDeleteListForBatch(jsonExtractorOrgUnitIds, orgUnitIdsForDelete, itemQuantity);
                            }
                        })
                );
        return httpSampler;
    }

    /***Update samplers ***/
    protected DslHttpSampler getHttpSamplerForUpdateOrgUnit() {
        String url = getOrgUnitUrl()
                + "/${"
                + jsonExtractorOrgUnitId
                + "}";
        DslHttpSampler httpSampler =
                jmeterUtil.getBaseHttpSampler("Update orgUnit", url, HTTPConstants.PUT, xTenantId);
        httpSampler
                .body("${REQUEST_BODY_UPDATE}")
                .children(
                        jsr223PreProcessor(s -> s.vars.put("REQUEST_BODY_UPDATE",
                                buildRequestBodyForUpdate(s.vars)))
                );
        return httpSampler;
    }

    protected DslHttpSampler getHttpSamplerForUpdateOrgUnitBatch() {
        String url = getOrgUnitUrl() + "/batch";
        DslHttpSampler httpSampler = jmeterUtil.getBaseHttpSampler("Update orgUnit batch",
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
    protected DslHttpSampler getHttpSamplerForDeleteOrgUnit() {
        String url = getOrgUnitUrl()
                + "/${"
                + jsonExtractorOrgUnitId
                + "}";
        return jmeterUtil.getBaseHttpSampler("Delete orgUnit",
                url,
                HTTPConstants.DELETE,
                xTenantId)
                .children(jsr223PostProcessor(s -> {
                    if (successStatusCodeAfterDelete.equals(s.prev.getResponseCode())) {
                        s.prev.setSuccessful(true);
                        extractAndRemoveIdFromDeleteList(jsonExtractorOrgUnitId, orgUnitIdsForDelete);
                    }
                }));
    }

    protected DslHttpSampler getHttpSamplerForDeleteOrgUnitBatch() {
        String url = getOrgUnitUrl() + buildParamsForDeleteBatch(jsonExtractorOrgUnitIds);
        return jmeterUtil.getBaseHttpSampler("Delete batch orgUnit",
                url,
                HTTPConstants.DELETE,
                xTenantId)
                .children(jsr223PostProcessor(s -> {
                    if (successStatusCodeAfterDeleteBatch.equals(s.prev.getResponseCode())) {
                        s.prev.setSuccessful(true);
                        extractAndRemoveIdsFromDeleteListForBatch(jsonExtractorOrgUnitIds, orgUnitIdsForDelete, itemQuantity);
                    }
                }));
    }

    protected static DslHttpSampler getHttpSamplerForDeleteOrgUnitBatch(List<String> idsForDelete) {
        String url = getOrgUnitUrl() + CommonUtil.buildParamsForDeleteBatch(idsForDelete);
        return jmeterUtil.getBaseHttpSampler("Delete batch orgUnit after tests",
                        url,
                        HTTPConstants.DELETE,
                        xTenantId)
                .children(jsr223PostProcessor(s -> {
                    if (successStatusCodeAfterDeleteBatch.equals(s.prev.getResponseCode())) {
                        s.prev.setSuccessful(true);
                        extractAndRemoveIdsFromDeleteListForBatch(idsForDelete, orgUnitIdsForDelete);
                    }
                }));
    }

    /***Patch samplers ***/
    protected DslHttpSampler getHttpSamplerForPatchOrgUnit() {
        String url = getOrgUnitUrl()
                + "/${"
                + jsonExtractorOrgUnitId
                + "}";
        DslHttpSampler httpSampler =
                jmeterUtil.getBaseHttpSampler("Patch orgUnit", url, HTTPConstants.PATCH, xTenantId);
        httpSampler
                .body("${REQUEST_BODY_PATCH}")
                .children(
                        jsr223PreProcessor(s -> s.vars.put("REQUEST_BODY_PATCH",
                                buildRequestBodyForPatch()))
                );
        return httpSampler;
    }

    protected DslHttpSampler getHttpSamplerForUpdateOrgUnitPatchBatch() {
        String url = getOrgUnitUrl() + "/batch";
        DslHttpSampler httpSampler = jmeterUtil.getBaseHttpSampler("Update orgUnit patch batch",
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
    protected DslHttpSampler getHttpSamplerForGetOrgUnits() {
        return jmeterUtil.getBaseHttpSampler("Get orgUnits",
                getOrgUnitUrl(),
                HTTPConstants.GET,
                xTenantId);
    }

    protected DslHttpSampler getHttpSamplerForFindOrgUnit() {
        String url = getOrgUnitUrl()
                + "/${"
                + jsonExtractorOrgUnitId
                + "}";
        return jmeterUtil.getBaseHttpSampler("Find orgUnit", url, HTTPConstants.GET, xTenantId);
    }

    @SneakyThrows
    private String buildRequestBodyForPatch() {
        return this.mapper.writeValueAsString(orgUnitLoadTestClient.getOrgUnitPatchDto());
    }

    @SneakyThrows
    private String buildRequestBodyForCreate(JMeterVariables vars) {
        return this.mapper.writeValueAsString(orgUnitLoadTestClient.getOrgUnitCreateDto());
    }

    @SneakyThrows
    private String buildRequestBodyForUpdate(JMeterVariables vars) {
        return this.mapper.writeValueAsString(orgUnitLoadTestClient.getOrgUnitUpdateDto());
    }

    @SneakyThrows
    public String buildRequestBodyForCreateBatch() {
        StringBuilder body = new StringBuilder("[");
        for (int x = 1; x <= itemQuantity; x = x + 1) {
            body.append(this.mapper.writeValueAsString(orgUnitLoadTestClient.getOrgUnitCreateDto()))
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
                    .append(getVariable(jsonExtractorOrgUnitIds + "_" + x))
                    .append("\":")
                    .append(this.mapper.writeValueAsString(orgUnitLoadTestClient.getOrgUnitUpdateDto()))
                    .append(",");
        }
        return body.substring(0, body.length() - 1) + "}";
    }

    @SneakyThrows
    public String buildRequestBodyForUpdatePatchBatch() {
        StringBuilder body = new StringBuilder("{");
        for (int x = 1; x <= itemQuantity; x = x + 1) {
            body.append("\"")
                    .append(getVariable(jsonExtractorOrgUnitIds + "_" + x))
                    .append("\":")
                    .append(this.mapper.writeValueAsString(orgUnitLoadTestClient.getOrgUnitPatchDto()))
                    .append(",");
        }
        return body.substring(0, body.length() - 1) + "}";
    }
}
