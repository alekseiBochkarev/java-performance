package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:loadtests.properties"
})
public interface LoadTestsConfig extends Config {

    Integer max_delay_time_for_get_all();

    @Key("max-delay-time-for-find")
    Integer max_delay_time_for_find();

    @Key("max-delay-time-for-create")
    Integer max_delay_time_for_create();

    @Key("max-delay-time-for-create-batch")
    int max_delay_time_for_create_batch();

    @Key("max-delay-time-for-update")
    Integer max_delay_time_for_update();
    
    @Key("threadsCount")
    Integer threadsCount();
    @Key("iterations")
    Integer iterations();
    @Key("login")
    String login();
    @Key("password")
    String password();
    @Key("get-token-every-time")
    boolean get_token_every_time();
    @Key("influx-db-path")
    String influx_db_path();
    @Key("use-blazemeter-engine")
    boolean use_blazemeter_engine();
    @Key("use-influx-db-listener")
    boolean use_influx_db_listener();
    @Key("keycloak-url")
    String keycloak_url();
    @Key("auth-platform-url")
    String auth_platform_url();
    @Key("user-platform-url")
    String user_platform_url();
    @Key("itemQuantity")
    int itemQuantity();
    @Key("delete-data-after-tests")
    boolean delete_data_after_tests();
}
