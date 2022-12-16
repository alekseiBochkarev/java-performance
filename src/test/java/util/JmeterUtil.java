package util;

import base.BaseAuthData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.entity.ContentType;
import org.apache.jmeter.threads.JMeterVariables;
import us.abstracta.jmeter.javadsl.JmeterDsl;
import us.abstracta.jmeter.javadsl.blazemeter.BlazeMeterEngine;
import us.abstracta.jmeter.javadsl.core.DslTestPlan;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;
import us.abstracta.jmeter.javadsl.core.threadgroups.BaseThreadGroup;
import us.abstracta.jmeter.javadsl.http.DslHttpSampler;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import static us.abstracta.jmeter.javadsl.JmeterDsl.*;
import static us.abstracta.jmeter.javadsl.JmeterDsl.influxDbListener;
import static us.abstracta.jmeter.javadsl.dashboard.DashboardVisualizer.dashboardVisualizer;

public class JmeterUtil {
    private static final String CLIENT_ID = "";
    private static final String CLIENT_SECRET = "";
    private final ObjectMapper mapper = new ObjectMapper();
    private static boolean USE_BLAZEMETER_ENGINE;
    private static boolean USE_INFLUX_DB_LISTENER = false;
    private static String INFLUX_DB_PATH = "";

    public static void setUSE_BLAZEMETER_ENGINE(final boolean USE_BLAZEMETER_ENGINE) {
        JmeterUtil.USE_BLAZEMETER_ENGINE = USE_BLAZEMETER_ENGINE;
    }

    public static void setUSE_INFLUX_DB_LISTENER(final boolean USE_INFLUX_DB_LISTENER) {
        JmeterUtil.USE_INFLUX_DB_LISTENER = USE_INFLUX_DB_LISTENER;
    }

    public static void setInfluxDbPath(final String influxDbPath) {
        INFLUX_DB_PATH = influxDbPath;
    }

    public JmeterUtil() {
    }

    public TestPlanStats get(final String url, final int threads, final int iterations) {
        return this.getTestPlanStats(threads, iterations, this.getHttpSampler(url, "GET"));
    }

    public TestPlanStats post(final String url,
            final int threads,
            final int iterations,
            final Object body) {
        return this.getTestPlanStats(threads, iterations, this.getHttpSampler(url, "POST", body));
    }

    public TestPlanStats post(final String url, final String token,
            final int threads,
            final int iterations,
            final Object body) {
        return this.getTestPlanStats(threads, iterations, this.getHttpSampler(url, "POST", body));
    }

    public TestPlanStats post(final String url,
            final int threads,
            final int iterations,
            final HashMap<String, String> params) {
        return this.getTestPlanStats(threads, iterations, this.getHttpSampler(url, "POST", params));
    }

    public UUID getUUID(String uuid) throws IllegalArgumentException {
        System.out.println(uuid);
        return UUID.fromString(uuid);
    }

    public TestPlanStats patch(final String url,
            final int threads,
            final int iterations,
            final Object body) {
        return this.getTestPlanStats(threads, iterations, this.getHttpSampler(url, "PATCH", body));
    }

    public TestPlanStats getTestPlanStats(final int threads,
            final int iterations,
            final DslHttpSampler dslHttpSampler) {
        try {
            return this.USE_BLAZEMETER_ENGINE ?
                    JmeterDsl.testPlan(new DslTestPlan.TestPlanChild[] { JmeterDsl.threadGroup(
                                    threads,
                                    iterations,
                                    new BaseThreadGroup.ThreadGroupChild[] { dslHttpSampler }) })
                            .runIn((new BlazeMeterEngine(this.CLIENT_ID + ":" + this.CLIENT_SECRET))
                                    .testName("Division performance tests").totalUsers(10)
                                    .holdFor(
                                            Duration.ofMinutes(10L)).threadsPerEngine(5)
                                    .testTimeout(Duration.ofMinutes(20L)))
                    : this.USE_INFLUX_DB_LISTENER ?
                    JmeterDsl.testPlan(new DslTestPlan.TestPlanChild[] { JmeterDsl.threadGroup(
                                    threads,
                                    iterations,
                                    dslHttpSampler,
                                    influxDbListener(INFLUX_DB_PATH)) })
                            .run()
                    :
                    JmeterDsl.testPlan(new DslTestPlan.TestPlanChild[] { JmeterDsl.threadGroup(
                                    threads,
                                    iterations,
                                    new BaseThreadGroup.ThreadGroupChild[] { dslHttpSampler,
                                            resultsTreeVisualizer() }) })
                            .run();
        }
        catch (InterruptedException | TimeoutException | IOException var5) {
            throw new RuntimeException(var5);
        }
    }

    public TestPlanStats getTestPlanStats(final int threads,
            final int iterations,
            final DslHttpSampler dslHttpSampler,
            final DslHttpSampler dslHttpSampler2) {
        try {
            return this.USE_BLAZEMETER_ENGINE ?
                    JmeterDsl.testPlan(new DslTestPlan.TestPlanChild[] { JmeterDsl.threadGroup(
                                    threads,
                                    iterations,
                                    new BaseThreadGroup.ThreadGroupChild[] { dslHttpSampler,
                                            dslHttpSampler2 }) })
                            .runIn((new BlazeMeterEngine(this.CLIENT_ID + ":" + this.CLIENT_SECRET))
                                    .testName("Division performance tests").totalUsers(10)
                                    .holdFor(
                                            Duration.ofMinutes(10L)).threadsPerEngine(5)
                                    .testTimeout(Duration.ofMinutes(20L)))
                    : this.USE_INFLUX_DB_LISTENER ?
                    JmeterDsl.testPlan(new DslTestPlan.TestPlanChild[] { JmeterDsl.threadGroup(
                                    threads,
                                    iterations,
                                    dslHttpSampler,
                                    dslHttpSampler2,
                                    influxDbListener(INFLUX_DB_PATH)) })
                            .run()
                    :
                    JmeterDsl.testPlan(new DslTestPlan.TestPlanChild[] { JmeterDsl.threadGroup(
                                    threads,
                                    iterations,
                                    new BaseThreadGroup.ThreadGroupChild[] { dslHttpSampler,
                                            dslHttpSampler2,
                                            resultsTreeVisualizer() }) })
                            .run();
        }
        catch (InterruptedException | TimeoutException | IOException var5) {
            throw new RuntimeException(var5);
        }
    }

    public TestPlanStats getTestPlanStats(final int threads,
                                          final int iterations,
                                          final DslHttpSampler dslHttpSampler,
                                          final DslHttpSampler dslHttpSampler2,
                                          final DslHttpSampler dslHttpSampler3) {
        try {
            return this.USE_BLAZEMETER_ENGINE ?
                    JmeterDsl.testPlan(new DslTestPlan.TestPlanChild[] { JmeterDsl.threadGroup(
                                    threads,
                                    iterations,
                                    new BaseThreadGroup.ThreadGroupChild[] { dslHttpSampler,
                                            dslHttpSampler2, dslHttpSampler3 }) })
                            .runIn((new BlazeMeterEngine(this.CLIENT_ID + ":" + this.CLIENT_SECRET))
                                    .testName("Division performance tests").totalUsers(10)
                                    .holdFor(
                                            Duration.ofMinutes(10L)).threadsPerEngine(5)
                                    .testTimeout(Duration.ofMinutes(20L)))
                    : this.USE_INFLUX_DB_LISTENER ?
                    JmeterDsl.testPlan(new DslTestPlan.TestPlanChild[] { JmeterDsl.threadGroup(
                                    threads,
                                    iterations,
                                    dslHttpSampler,
                                    dslHttpSampler2,
                                    dslHttpSampler3,
                                    influxDbListener(INFLUX_DB_PATH)) })
                            .run()
                    :
                    JmeterDsl.testPlan(new DslTestPlan.TestPlanChild[] { JmeterDsl.threadGroup(
                                    threads,
                                    iterations,
                                    new BaseThreadGroup.ThreadGroupChild[] { dslHttpSampler,
                                            dslHttpSampler2, dslHttpSampler3,
                                            resultsTreeVisualizer(), dashboardVisualizer() }) })
                            .run();
        }
        catch (InterruptedException | TimeoutException | IOException var5) {
            throw new RuntimeException(var5);
        }
    }

    public TestPlanStats getTestPlanStats(final int threads,
                                          final int iterations,
                                          final DslHttpSampler dslHttpSampler,
                                          final DslHttpSampler dslHttpSampler2,
                                          final DslHttpSampler dslHttpSampler3,
                                          final DslHttpSampler dslHttpSampler4) {
        try {
            return this.USE_BLAZEMETER_ENGINE ?
                    JmeterDsl.testPlan(new DslTestPlan.TestPlanChild[] { JmeterDsl.threadGroup(
                                    threads,
                                    iterations,
                                    new BaseThreadGroup.ThreadGroupChild[] { dslHttpSampler,
                                            dslHttpSampler2, dslHttpSampler3, dslHttpSampler4}) })
                            .runIn((new BlazeMeterEngine(this.CLIENT_ID + ":" + this.CLIENT_SECRET))
                                    .testName("Division performance tests").totalUsers(10)
                                    .holdFor(
                                            Duration.ofMinutes(10L)).threadsPerEngine(5)
                                    .testTimeout(Duration.ofMinutes(20L)))
                    : this.USE_INFLUX_DB_LISTENER ?
                    JmeterDsl.testPlan(new DslTestPlan.TestPlanChild[] { JmeterDsl.threadGroup(
                                    threads,
                                    iterations,
                                    dslHttpSampler,
                                    dslHttpSampler2,
                                    dslHttpSampler3,
                                    dslHttpSampler4,
                                    influxDbListener(INFLUX_DB_PATH)) })
                            .run()
                    :
                    JmeterDsl.testPlan(new DslTestPlan.TestPlanChild[] { JmeterDsl.threadGroup(
                                    threads,
                                    iterations,
                                    new BaseThreadGroup.ThreadGroupChild[] { dslHttpSampler,
                                            dslHttpSampler2, dslHttpSampler3, dslHttpSampler4,
                                            resultsTreeVisualizer(), dashboardVisualizer() }) })
                            .run();
        }
        catch (InterruptedException | TimeoutException | IOException var5) {
            throw new RuntimeException(var5);
        }
    }

    private DslHttpSampler getHttpSampler(final String url, final String httpMethod) {
        return this.getHttpSampler(url, httpMethod, (Object) null);
    }

    private DslHttpSampler getHttpSampler(final String url,
            final String httpMethod,
            final Object body) {
        DslHttpSampler httpSampler =
                JmeterDsl.httpSampler(url).method(httpMethod).contentType(
                        ContentType.APPLICATION_JSON);
        if (body != null) {
            try {
                httpSampler.body(this.mapper.writeValueAsString(body));
            }
            catch (JsonProcessingException var6) {
                throw new RuntimeException(var6);
            }
        }
        return httpSampler;
    }

    private DslHttpSampler getHttpSampler(final String url,
            final String httpMethod,
            final HashMap<String, String> params) {
        DslHttpSampler httpSampler =
                JmeterDsl.httpSampler(url).method(httpMethod).contentType(
                        ContentType.APPLICATION_FORM_URLENCODED);
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                httpSampler.param(entry.getKey(), entry.getValue());
            }
        }
        return httpSampler;
    }

    public DslHttpSampler getBaseHttpSampler(final String name, final String url,
            final String httpMethod, final String xTenantId) {
        return JmeterDsl.httpSampler(name, url)
                .header("X-Tenant-Id", xTenantId)
                .header("Authorization", "Bearer " + "${REQUEST_TOKEN}")
                .method(httpMethod).contentType(
                        ContentType.APPLICATION_JSON)
                .children(
                        jsr223PreProcessor(s -> s.vars.put("REQUEST_TOKEN",
                                buildRequestToken(s.vars)))
                );
    }

    private String buildRequestToken(JMeterVariables vars) {
        return new BaseAuthData().getAccessToken();
    }

}
