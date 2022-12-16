package test_cases.user;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static util.AllureAttachments.addAllureAttaches;

public class UserLoadTest extends UserLoadTestData {

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("")
    @Link(name = "", url = "")
    @TmsLink("")
    @Owner("Aleksei.Bochkarev")
    @Tags({ @Tag("7.5") })
    void verifyCreate() {
        TestPlanStats stats =
                jmeterUtil.getTestPlanStats(threads, iterations,
                        getHttpSamplerForCreateVirtualDbGroup(),
                        getHttpSamplerForCreateOrgUnit(),
                        getHttpSamplerForCreateUser());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount(), "too much errors");
        assertEquals((long) threads * (long) iterations * 3,
                stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_CREATE * 3> stats.overall().sampleTimePercentile99().toMillis(),
                "response is very slowly");
    }

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("")
    @Link(name = "", url = "")
    @TmsLink("")
    @Owner("Aleksei.Bochkarev")
    @Tags({ @Tag("7.5") })
    void verifyUpdate() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateVirtualDbGroup(),
                getHttpSamplerForCreateOrgUnit(),
                getHttpSamplerForCreateUser(),
                getHttpSamplerForUpdateUser());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 4, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_UPDATE * 4> stats.overall().sampleTimePercentile99().toMillis(),
                "User update is very slowly");
    }

    @Test
    @DisplayName("verify Batch Create")
    public void verifyBatchCreate() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateVirtualDbGroup(),
                getHttpSamplerForCreateOrgUnit(),
                getHttpSamplerForCreateUserBatch());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 3, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_CREATE_BATCH * 3> stats.overall().sampleTimePercentile99()
                .toMillis(), "Batch user created is very slowly");
    }

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("")
    @Link(name = "", url = "")
    @TmsLink("")
    @Owner("Aleksei.Bochkarev")
    @Tags({ @Tag("7.5") })
    void verifyUpdatePatch() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateVirtualDbGroup(),
                getHttpSamplerForCreateOrgUnit(),
                getHttpSamplerForCreateUser(),
                getHttpSamplerForPatchUser());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 4, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_UPDATE * 4> stats.overall().sampleTimePercentile99().toMillis(),
                "user patch is very slowly");
    }

    @Test
    @DisplayName("verify Batch Delete")
    public void verifyBatchDelete() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateVirtualDbGroup(),
                getHttpSamplerForCreateOrgUnit(),
                getHttpSamplerForCreateUserBatch(),
                getHttpSamplerForDeleteUserBatch());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 4, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_CREATE_BATCH * 4> stats.overall().sampleTimePercentile99()
                .toMillis(), "Response is very slowly");
    }

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("")
    @Link(name = "", url = "")
    @TmsLink("")
    @Owner("Aleksei.Bochkarev")
    @Tags({ @Tag("") })
    void verifyGetAll() {
        TestPlanStats stats =
                jmeterUtil.getTestPlanStats(threads, iterations, getHttpSamplerForGetUsers());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_GET_ALL > stats.overall().sampleTimePercentile99().toMillis(),
                "user GetAll is very slowly");
    }

    @Test
    @DisplayName("verify Batch Update")
    public void verifyBatchUpdate() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateVirtualDbGroup(),
                getHttpSamplerForCreateOrgUnit(),
                getHttpSamplerForCreateUserBatch(),
                getHttpSamplerForUpdateUserBatch());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 4, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_CREATE_BATCH * 4> stats.overall().sampleTimePercentile99()
                .toMillis(), "Response is very slowly");
    }

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("")
    @Link(name = "", url = "")
    @TmsLink("")
    @Owner("Aleksei.Bochkarev")
    @Tags({ @Tag("") })
    void verifyFind() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateVirtualDbGroup(),
                getHttpSamplerForCreateOrgUnit(),
                getHttpSamplerForCreateUser(),
                getHttpSamplerForFindUser());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 4, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_FIND * 4> stats.overall().sampleTimePercentile99()
                .toMillis(), "User find is very slowly");
    }

    @Test
    @DisplayName("verify Batch User Patch")
    public void verifyBatchUpdatePatch() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateVirtualDbGroup(),
                getHttpSamplerForCreateOrgUnit(),
                getHttpSamplerForCreateUserBatch(),
                getHttpSamplerForUpdateUserPatchBatch());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 4, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_CREATE_BATCH * 4> stats.overall().sampleTimePercentile99()
                .toMillis(), "Response is very slowly");
    }

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("")
    @Link(name = "", url = "")
    @TmsLink("")
    @Owner("Aleksei.Bochkarev")
    @Tags({ @Tag("7.5") })
    void verifyDelete() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateVirtualDbGroup(),
                getHttpSamplerForCreateOrgUnit(),
                getHttpSamplerForCreateUser(),
                getHttpSamplerForDeleteUser());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 4, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_UPDATE * 4> stats.overall().sampleTimePercentile99()
                .toMillis(), "User deleted is very slowly");
    }
}
