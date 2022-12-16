package test_cases.user;

import base.BaseAuthData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.jmeter.protocol.http.util.HTTPConstants;
import org.apache.jmeter.threads.JMeterVariables;
import org.junit.jupiter.api.AfterAll;
import test_cases.orgunit.OrgUnitLoadTestData;
import test_cases.virtualdbgroup.VirtualDbGroupLoadTestData;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;
import us.abstracta.jmeter.javadsl.http.DslHttpSampler;
import util.CommonUtil;
import util.JmeterUtil;
import util.user.UserLoadTestClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static test_cases.orgunit.OrgUnitLoadTestData.orgUnitIdsForDelete;
import static test_cases.orgunit.OrgUnitLoadTestData.removeOrgUnitIds;
import static test_cases.virtualdbgroup.VirtualDbGroupLoadTestData.removeVirtualDbGroupIds;
import static test_cases.virtualdbgroup.VirtualDbGroupLoadTestData.virtualDbGroupIdsForDelete;
import static us.abstracta.jmeter.javadsl.JmeterDsl.*;
import static util.CommonUtil.*;

public class UserLoadTestData extends BaseAuthData {

    private final ObjectMapper mapper = new ObjectMapper();
    UserLoadTestClient userLoadTestClient = new UserLoadTestClient();
    VirtualDbGroupLoadTestData virtualDbGroupLoadTestData = new VirtualDbGroupLoadTestData();
    OrgUnitLoadTestData orgUnitLoadTestData = new OrgUnitLoadTestData();
    static JmeterUtil jmeterUtil = new JmeterUtil();

    private static final String xTenantId = "user_platform";
    private String jsonExtractorUserId = "userId";
    private String jsonExtractorUserIdPath = "id";
    private String jsonExtractorUserIdsPath = "data[:" + itemQuantity + "].id";
    private String jsonExtractorUserIds = "ids";

    public String getJsonExtractorUserIds() {
        return jsonExtractorUserIds;
    }

    public String getJsonExtractorUserId() {
        return jsonExtractorUserId;
    }

    public static List<String> userIdsForDelete = new ArrayList<>();

    private String successStatusCodeAfterCreate = "200";
    private static String successStatusCodeAfterDelete = "204";
    private static String successStatusCodeAfterDeleteBatch = "200";

    public static Consumer<List<String>> removeUserIds = list -> {
        try {
            deleteUserIdsAfterTests(list);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

    @AfterAll
    static void cleanUp () {
        if (DELETE_DATA_AFTER_TESTS) {
            /** remove users **/
            System.out.println("after all, user ids before removing: ");
            printIds.accept(userIdsForDelete);
            List<String> userIdsForDeleteAdd = userIdsForDelete.stream().collect(Collectors.toList());
            removeUserIds.accept(userIdsForDeleteAdd);
            System.out.println("after all, user ids after removing");
            System.out.println(userIdsForDelete);
            /** remove virtualDbGroup **/
            System.out.println("after all, virtualDbGroup ids before removing: ");
            printIds.accept(virtualDbGroupIdsForDelete);
            List<String> virtualDbGroupIdsForDeleteAdd = virtualDbGroupIdsForDelete.stream().collect(Collectors.toList());
            removeVirtualDbGroupIds.accept(virtualDbGroupIdsForDeleteAdd);
            System.out.println("after all, virtualDbGroup ids after removing");
            System.out.println(virtualDbGroupIdsForDelete);
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

    private static void deleteUserIdsAfterTests(List<String> userIds) throws IOException {
        TestPlanStats stats = testPlan(threadGroup(1,1, getHttpSamplerForDeleteUserBatch(userIds)
                // , resultsTreeVisualizer()
        )).run();
        assertEquals(0, stats.overall().errorsCount());
    }

    /*** get supporting http sampler for create virtual db group***/
    protected DslHttpSampler getHttpSamplerForCreateVirtualDbGroup() {
        return virtualDbGroupLoadTestData.getHttpSamplerForCreateVirtualDbGroup();
    }

    /*** get supporting http sampler for create org unit***/
    protected DslHttpSampler getHttpSamplerForCreateOrgUnit() {
        return orgUnitLoadTestData.getHttpSamplerForCreateOrgUnit();
    }

    /***Create samplers ***/
    public DslHttpSampler getHttpSamplerForCreateUser() {
        DslHttpSampler httpSampler =
                jmeterUtil.getBaseHttpSampler("Create user",
                        getUserUrl(),
                        HTTPConstants.POST,
                        xTenantId);
        httpSampler
                .body("${REQUEST_BODY}")
                .children(
                        jsr223PreProcessor(s -> s.vars.put("REQUEST_BODY",
                                buildRequestBodyForShortCreate(s.vars))),
                        jsonExtractor(jsonExtractorUserId, jsonExtractorUserIdPath),
                        jsr223PostProcessor(s -> {
                            if (successStatusCodeAfterCreate.equals(s.prev.getResponseCode())) {
                                s.prev.setSuccessful(true);
                                CommonUtil.addIdForDeleteList(jsonExtractorUserId, userIdsForDelete);
                            }
                        })
                );
        return httpSampler;
    }

    public DslHttpSampler getHttpSamplerForCreateUserBatch() {
        String url = getUserUrl() + "/batch";
        DslHttpSampler httpSampler = jmeterUtil.getBaseHttpSampler("Create batch user",
                url,
                HTTPConstants.POST,
                xTenantId);
        httpSampler
                .body("${REQUEST_BODY_CREATE_BATCH}")
                .children(
                        jsr223PreProcessor(s -> s.vars.put("REQUEST_BODY_CREATE_BATCH",
                                buildRequestBodyForCreateBatch())),
                        jsonExtractor(jsonExtractorUserIds, jsonExtractorUserIdsPath)
                                .matchNumber(-1),
                        jsr223PostProcessor(s -> {
                            if (successStatusCodeAfterCreate.equals(s.prev.getResponseCode())) {
                                s.prev.setSuccessful(true);
                                CommonUtil.addIdsForDeleteListForBatch(jsonExtractorUserIds, userIdsForDelete, itemQuantity);
                            }
                        })
                );
        return httpSampler;
    }

    /***Update samplers ***/
    protected DslHttpSampler getHttpSamplerForUpdateUser() {
        String url = getUserUrl()
                + "/${"
                + jsonExtractorUserId
                + "}";
        DslHttpSampler httpSampler =
                jmeterUtil.getBaseHttpSampler("Update user", url, HTTPConstants.PUT, xTenantId);
        httpSampler
                .body("${REQUEST_BODY_UPDATE}")
                .children(
                        jsr223PreProcessor(s -> s.vars.put("REQUEST_BODY_UPDATE",
                                buildRequestBodyForUpdate(s.vars)))
                );
        return httpSampler;
    }

    protected DslHttpSampler getHttpSamplerForUpdateUserBatch() {
        String url = getUserUrl() + "/batch";
        DslHttpSampler httpSampler = jmeterUtil.getBaseHttpSampler("Update user batch",
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
    protected DslHttpSampler getHttpSamplerForDeleteUser() {
        String url = getUserUrl()
                + "/${"
                + jsonExtractorUserId
                + "}";
        return jmeterUtil.getBaseHttpSampler("Delete user", url, HTTPConstants.DELETE, xTenantId)
                .children(jsr223PostProcessor(s -> {
            if (successStatusCodeAfterDelete.equals(s.prev.getResponseCode())) {
                s.prev.setSuccessful(true);
                extractAndRemoveIdFromDeleteList(jsonExtractorUserId, userIdsForDelete);
            }
        }));
    }

    protected DslHttpSampler getHttpSamplerForDeleteUserBatch() {
        String url = getUserUrl() + buildParamsForDeleteBatch(jsonExtractorUserIds);
        return jmeterUtil.getBaseHttpSampler("Delete batch user",
                url,
                HTTPConstants.DELETE,
                xTenantId)
                .children(jsr223PostProcessor(s -> {
                    if (successStatusCodeAfterDeleteBatch.equals(s.prev.getResponseCode())) {
                        s.prev.setSuccessful(true);
                        extractAndRemoveIdsFromDeleteListForBatch(jsonExtractorUserIds, userIdsForDelete, itemQuantity);
                    }
                }));
    }

    public static DslHttpSampler getHttpSamplerForDeleteUserBatch(List<String> userIds) {
        String url = getUserUrl() + CommonUtil.buildParamsForDeleteBatch(userIds);
        return jmeterUtil.getBaseHttpSampler("Delete batch user",
                        url,
                        HTTPConstants.DELETE,
                        xTenantId)
                .children(jsr223PostProcessor(s -> {
                    if (successStatusCodeAfterDeleteBatch.equals(s.prev.getResponseCode())) {
                        s.prev.setSuccessful(true);
                        extractAndRemoveIdsFromDeleteListForBatch(userIds, userIdsForDelete);
                    }
                }));
    }

    /***Patch samplers ***/
    protected DslHttpSampler getHttpSamplerForPatchUser() {
        String url = getUserUrl()
                + "/${"
                + jsonExtractorUserId
                + "}";
        DslHttpSampler httpSampler =
                jmeterUtil.getBaseHttpSampler("Patch user", url, HTTPConstants.PATCH, xTenantId);
        httpSampler
                .body("${REQUEST_BODY_PATCH}")
                .children(
                        jsr223PreProcessor(s -> s.vars.put("REQUEST_BODY_PATCH",
                                buildRequestBodyForPatch()))
                );
        return httpSampler;
    }

    protected DslHttpSampler getHttpSamplerForUpdateUserPatchBatch() {
        String url = getUserUrl() + "/batch";
        DslHttpSampler httpSampler = jmeterUtil.getBaseHttpSampler("Update user patch batch",
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
    protected DslHttpSampler getHttpSamplerForGetUsers() {
        return jmeterUtil.getBaseHttpSampler("Get users",
                getUserUrl(),
                HTTPConstants.GET,
                xTenantId);
    }

    protected DslHttpSampler getHttpSamplerForFindUser() {
        String url = getUserUrl()
                + "/${"
                + jsonExtractorUserId
                + "}";
        return jmeterUtil.getBaseHttpSampler("Find user", url, HTTPConstants.GET, xTenantId);
    }

    @SneakyThrows
    private String buildRequestBodyForPatch() {
        return this.mapper.writeValueAsString(userLoadTestClient.getUserPatchDto());
    }

    /** for future -> after Rests would be fixed **/
    @SneakyThrows
    private String buildRequestBodyForCreate(JMeterVariables vars) {
        return this.mapper.findAndRegisterModules().writeValueAsString(userLoadTestClient.getUserCreateDto(getVariable(virtualDbGroupLoadTestData.getJsonExtractorVirtualDbGroupId()), getVariable(orgUnitLoadTestData.getJsonExtractorOrgUnitId())));
    }

    @SneakyThrows
    private String buildRequestBodyForShortCreate(JMeterVariables vars) {
        return this.mapper.findAndRegisterModules().writeValueAsString(userLoadTestClient.getUserCreateShortDto(getVariable(virtualDbGroupLoadTestData.getJsonExtractorVirtualDbGroupId()), getVariable(orgUnitLoadTestData.getJsonExtractorOrgUnitId())));
    }


    @SneakyThrows
    private String buildRequestBodyForUpdate(JMeterVariables vars) {
        return this.mapper.findAndRegisterModules().writeValueAsString(userLoadTestClient.getUserUpdateDto(getVariable(virtualDbGroupLoadTestData.getJsonExtractorVirtualDbGroupId()), getVariable(orgUnitLoadTestData.getJsonExtractorOrgUnitId())));
    }

    @SneakyThrows
    public String buildRequestBodyForCreateBatch() {
        StringBuilder body = new StringBuilder("[");
        for (int x = 1; x <= itemQuantity; x = x + 1) {
            body.append(this.mapper.findAndRegisterModules().writeValueAsString(userLoadTestClient.getUserCreateShortDto(getVariable(virtualDbGroupLoadTestData.getJsonExtractorVirtualDbGroupId()), getVariable(orgUnitLoadTestData.getJsonExtractorOrgUnitId()))))
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
                    .append(getVariable(jsonExtractorUserIds + "_" + x))
                    .append("\":")
                    .append(this.mapper.findAndRegisterModules().writeValueAsString(userLoadTestClient.getUserUpdateDto(getVariable(virtualDbGroupLoadTestData.getJsonExtractorVirtualDbGroupId()), getVariable(orgUnitLoadTestData.getJsonExtractorOrgUnitId()))))
                    .append(",");
        }
        return body.substring(0, body.length() - 1) + "}";
    }

    @SneakyThrows
    public String buildRequestBodyForUpdatePatchBatch() {
        StringBuilder body = new StringBuilder("{");
        for (int x = 1; x <= itemQuantity; x = x + 1) {
            body.append("\"")
                    .append(getVariable(jsonExtractorUserIds + "_" + x))
                    .append("\":")
                    .append(this.mapper.writeValueAsString(userLoadTestClient.getUserPatchDto()))
                    .append(",");
        }
        return body.substring(0, body.length() - 1) + "}";
    }
}
