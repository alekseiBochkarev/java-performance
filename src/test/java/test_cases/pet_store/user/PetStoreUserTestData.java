package test_cases.pet_store.user;

import base.BaseAuthData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.jmeter.protocol.http.util.HTTPConstants;
import org.apache.jmeter.threads.JMeterVariables;
import us.abstracta.jmeter.javadsl.http.DslHttpSampler;
import util.JmeterUtil;
import util.pet_store.user.PetStoreUserTestClient;

import static us.abstracta.jmeter.javadsl.JmeterDsl.jsr223PreProcessor;

public class PetStoreUserTestData extends BaseAuthData {

    private final ObjectMapper mapper = new ObjectMapper();
    PetStoreUserTestClient petStoreUserTestClient = new PetStoreUserTestClient();
    JmeterUtil jmeterUtil = new JmeterUtil();
    private final String xTenantId = "pet_store";

    protected DslHttpSampler getHttpSamplerForCreateUser() {
        DslHttpSampler httpSampler =
                jmeterUtil.getBaseHttpSampler("Create user in Pet Store",
                        getPetStoreUserUrl(),
                        HTTPConstants.POST,
                        xTenantId);
        httpSampler
                .body("${REQUEST_BODY}")
                .children(
                        jsr223PreProcessor(s -> s.vars.put("REQUEST_BODY",
                                buildRequestBodyForCreatePetStoreUser(s.vars)))
                );
        return httpSampler;
    }

    @SneakyThrows
    public String buildRequestBodyForCreatePetStoreUser(JMeterVariables vars) {
        return this.mapper.writeValueAsString(petStoreUserTestClient.getUserCreateDto());
    }
}
