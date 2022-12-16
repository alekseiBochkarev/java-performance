package base;

import config.LoadTestsConfig;
import org.aeonbits.owner.ConfigFactory;
import org.apache.jmeter.threads.JMeterContextService;
import org.junit.jupiter.api.BeforeAll;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import util.JmeterUtil;

public class BaseAuthData {
    protected static LoadTestsConfig config() {
        return ConfigFactory.newInstance().create(LoadTestsConfig.class, System.getProperties());
    }

    protected static int threads;
    protected static int iterations;
    protected static int itemQuantity;
    protected static int MAX_DELAY_TIME_FOR_GET_ALL;
    protected static int MAX_DELAY_TIME_FOR_FIND;
    protected static int MAX_DELAY_TIME_FOR_CREATE;
    protected static int MAX_DELAY_TIME_FOR_CREATE_BATCH;
    protected static int MAX_DELAY_TIME_FOR_UPDATE;
    protected static boolean GET_TOKEN_EVERY_TIME;
    protected static boolean DELETE_DATA_AFTER_TESTS;

    @BeforeAll
    public static void setUp() {
        try {
            initEnvVars();
            System.out.println("before all");
        }
        catch (final Throwable t) {
            System.out.println("No fun...: " + t.getMessage());
            t.printStackTrace();
        }
    }

    private static void initEnvVars() {
        threads = config().threadsCount();
        iterations = config().iterations();
        itemQuantity = config().itemQuantity();
        MAX_DELAY_TIME_FOR_GET_ALL = config().max_delay_time_for_get_all();
        MAX_DELAY_TIME_FOR_FIND = config().max_delay_time_for_find();
        MAX_DELAY_TIME_FOR_CREATE = config().max_delay_time_for_create();
        MAX_DELAY_TIME_FOR_CREATE_BATCH = config().max_delay_time_for_create_batch();
        MAX_DELAY_TIME_FOR_UPDATE = config().max_delay_time_for_update();
        GET_TOKEN_EVERY_TIME = config().get_token_every_time();
        DELETE_DATA_AFTER_TESTS = config().delete_data_after_tests();
        JmeterUtil.setInfluxDbPath(config().influx_db_path());
        JmeterUtil.setUSE_BLAZEMETER_ENGINE(config().use_blazemeter_engine());
        JmeterUtil.setUSE_INFLUX_DB_LISTENER(config().use_influx_db_listener());
    }

    private volatile Keycloak keycloak;

    public String getAccessToken() {
        if (GET_TOKEN_EVERY_TIME) {
            keycloak = getKeycloakBuilder().build();
        }
        else if (keycloak == null) {
            synchronized (this) {
                if (keycloak == null) {
                    keycloak = getKeycloakBuilder().build();
                }
            }
        }
        return keycloak.tokenManager().getAccessTokenString();
    }

    public KeycloakBuilder getKeycloakBuilder() {
        return KeycloakBuilder.builder()
                .serverUrl(config().keycloak_url())
                .realm("admin")
                .clientId("uptempo-admin")
                .clientSecret("FBt5pzL7")
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS);
    }

    protected static final String AUTH_URL() {
        return config().auth_platform_url();
    }

    protected static final String APPLICATION_URL() {
        return config().user_platform_url();
    }

    protected String getRoleUrl() {
        return APPLICATION_URL() + "/user/v1/roles";
    }

    protected String getPetStoreUserUrl()
    {
        return APPLICATION_URL() + "/user";
    }

    protected static String getGroupUrl() {
        return APPLICATION_URL() + "/user/v1/groups";
    }

    protected static String getVirtualDbGroupUrl() {
        return APPLICATION_URL() + "/user/v1/virtual-database-groups";
    }

    protected static String getVirtualDbUrl() {
        return APPLICATION_URL() + "/user/v1/virtual-databases";
    }

    protected static String getOrgUnitUrl() {
        return APPLICATION_URL() + "/user/v1/organizational-units";
    }

    protected String getVariable(String variable) {
        return JMeterContextService.getContext().getVariables().get(variable);
    }

    protected static String getGroupMembershipUrl() {
        return APPLICATION_URL() + "/user/v1/group-memberships";
    }

    protected static String getUserDataValuesUrl() {
        return APPLICATION_URL() + "/user/v1/users/user-data/values";
    }

    protected static String getUserUrl() {
        return APPLICATION_URL() + "/user/v1/users";
    }
}
