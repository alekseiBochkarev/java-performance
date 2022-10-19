package test_cases.roles;

import base.BaseAuthData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.jmeter.protocol.http.util.HTTPConstants;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import us.abstracta.jmeter.javadsl.http.DslHttpSampler;
import util.JmeterUtil;
import util.role.RoleLoadTestClient;

import static us.abstracta.jmeter.javadsl.JmeterDsl.jsonExtractor;
import static us.abstracta.jmeter.javadsl.JmeterDsl.jsr223PreProcessor;

public class RoleLoadTestData extends BaseAuthData {
    private final ObjectMapper mapper = new ObjectMapper();
    RoleLoadTestClient roleLoadTestClient = new RoleLoadTestClient();
    JmeterUtil jmeterUtil = new JmeterUtil();
    private final String xTenantId = "user_platform";
    private String jsonExtractorIdName = "roleId";
    private String jsonExtractorIdPath = "id";
    private String jsonExtractorIdsPath = "data[:" + itemQuantity + "].id";
    private String jsonExtractorIdsName = "ids";

    protected DslHttpSampler getHttpSamplerForCreateRole() {
        DslHttpSampler httpSampler =
                jmeterUtil.getBaseHttpSampler("Create role",
                        getRoleUrl(),
                        HTTPConstants.POST,
                        xTenantId);
        httpSampler
                .body("${REQUEST_BODY}")
                .children(
                        jsr223PreProcessor(s -> s.vars.put("REQUEST_BODY",
                                buildRequestBodyForCreate(s.vars))),
                        jsonExtractor(jsonExtractorIdName, jsonExtractorIdPath)
                );
        return httpSampler;
    }

    protected DslHttpSampler getHttpSamplerForUpdateRole() {
        String url = getRoleUrl()
                + "/${"
                + jsonExtractorIdName
                + "}";
        DslHttpSampler httpSampler =
                jmeterUtil.getBaseHttpSampler("Update role", url, HTTPConstants.PUT, xTenantId);
        httpSampler
                .body("${REQUEST_BODY_UPDATE}")
                .children(
                        jsr223PreProcessor(s -> s.vars.put("REQUEST_BODY_UPDATE",
                                buildRequestBodyForUpdate(s.vars)))
                );
        return httpSampler;
    }

    protected DslHttpSampler getHttpSamplerForPatchRole() {
        String url = getRoleUrl()
                + "/${"
                + jsonExtractorIdName
                + "}";
        DslHttpSampler httpSampler =
                jmeterUtil.getBaseHttpSampler("Patch role", url, HTTPConstants.PATCH, xTenantId);
        httpSampler
                .body("${REQUEST_BODY_PATCH}")
                .children(
                        jsr223PreProcessor(s -> s.vars.put("REQUEST_BODY_PATCH",
                                buildRequestBodyForPatch()))
                );
        return httpSampler;
    }

    protected DslHttpSampler getHttpSamplerForGetRoles() {
        return jmeterUtil.getBaseHttpSampler("Get roles",
                getRoleUrl(),
                HTTPConstants.GET,
                xTenantId);
    }

    protected DslHttpSampler getHttpSamplerForFindRole() {
        String url = getRoleUrl()
                + "/${"
                + jsonExtractorIdName
                + "}";
        return jmeterUtil.getBaseHttpSampler("Find role", url, HTTPConstants.GET, xTenantId);
    }

    protected DslHttpSampler getHttpSamplerForDeleteRole() {
        String url = getRoleUrl()
                + "/${"
                + jsonExtractorIdName
                + "}";
        return jmeterUtil.getBaseHttpSampler("Delete role", url, HTTPConstants.DELETE, xTenantId);
    }

    protected DslHttpSampler getHttpSamplerForDeleteRoleBatch() {
        String url = getRoleUrl() + buildParamsForDeleteBatch(jsonExtractorIdsName);
        return jmeterUtil.getBaseHttpSampler("Delete batch roles",
                url,
                HTTPConstants.DELETE,
                xTenantId);
    }

    protected DslHttpSampler getHttpSamplerForCreateRoleBatch() {
        String url = getRoleUrl() + "/batch";
        DslHttpSampler httpSampler = jmeterUtil.getBaseHttpSampler("Create role batch",
                url,
                HTTPConstants.POST,
                xTenantId);
        httpSampler
                .body("${REQUEST_BODY_CREATE_BATCH}")
                .children(
                        jsr223PreProcessor(s -> s.vars.put("REQUEST_BODY_CREATE_BATCH",
                                buildRequestBodyForCreateBatch())),
                        jsonExtractor(jsonExtractorIdsName, jsonExtractorIdsPath)
                                .matchNumber(-1)
                );
        return httpSampler;
    }

    protected DslHttpSampler getHttpSamplerForUpdateRoleBatch() {
        String url = getRoleUrl() + "/batch";
        DslHttpSampler httpSampler = jmeterUtil.getBaseHttpSampler("Update role batch",
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

    protected DslHttpSampler getHttpSamplerForUpdateRolePatchBatch() {
        String url = getRoleUrl() + "/batch";
        DslHttpSampler httpSampler = jmeterUtil.getBaseHttpSampler("Update role patch batch",
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

    @SneakyThrows
    public String buildRequestBodyForCreate(JMeterVariables vars) {
        return this.mapper.writeValueAsString(roleLoadTestClient.getRoleCreateDto());
    }

    @SneakyThrows
    public String buildRequestBodyForPatch() {
        return this.mapper.writeValueAsString(roleLoadTestClient.getRolePatchDto());
    }

    @SneakyThrows
    public String buildRequestBodyForUpdate(JMeterVariables vars) {
        return this.mapper.writeValueAsString(roleLoadTestClient.getRoleUpdateDto());
    }

    public String buildParamsForDeleteBatch(String jsonExtractorIdsName) {
        StringBuilder params = new StringBuilder("/batch?");
        for (int x = 1; x <= itemQuantity; x = x + 1) {
            params.append("id=${").append(jsonExtractorIdsName).append("_").append(x).append("}&");

        }
        return params.substring(0, params.length() - 1);
    }

    public String buildRequestBodyForCreateBatch() throws JsonProcessingException {
        StringBuilder body = new StringBuilder("["
                + this.mapper.writeValueAsString(roleLoadTestClient.getRoleCreateDto()));
        for (int x = 1; x < itemQuantity; x = x + 1) {
            body.append(",")
                    .append(this.mapper.writeValueAsString(roleLoadTestClient.getRoleCreateDto()));
        }
        return body + "]";
    }

    public String buildRequestBodyForUpdateBatch() throws JsonProcessingException {
        StringBuilder body = new StringBuilder("{");
        for (int x = 1; x <= itemQuantity; x = x + 1) {
            body.append("\"")
                    .append(JMeterContextService.getContext().getVariables()
                            .get(jsonExtractorIdsName + "_" + x))
                    .append("\":")
                    .append(this.mapper.writeValueAsString(roleLoadTestClient.getRoleUpdateDto()))
                    .append(",");
        }
        return body.substring(0, body.length() - 1) + "}";
    }

    public String buildRequestBodyForUpdatePatchBatch() throws JsonProcessingException {
        StringBuilder body = new StringBuilder("{");
        for (int x = 1; x <= itemQuantity; x = x + 1) {
            body.append("\"")
                    .append(JMeterContextService.getContext().getVariables()
                            .get(jsonExtractorIdsName + "_" + x))
                    .append("\":")
                    .append(this.mapper.writeValueAsString(roleLoadTestClient.getRolePatchDto()))
                    .append(",");
        }
        return body.substring(0, body.length() - 1) + "}";
    }

}
