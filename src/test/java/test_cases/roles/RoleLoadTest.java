package test_cases.roles;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static util.AllureAttachments.addAllureAttaches;

public class RoleLoadTest extends RoleLoadTestData {

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("Create role")
    @TmsLink("")
    @Owner("Aleksei.Bochkarev")
    @Tags({ @Tag("7.5") })
    void checkCreateRole() {
        TestPlanStats stats =
                jmeterUtil.getTestPlanStats(threads, iterations, getHttpSamplerForCreateRole());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount(), "too much errors");
        assertEquals((long) threads * (long) iterations,
                stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_CREATE > stats.overall().sampleTimePercentile99().toMillis(),
                "response is very slowly");
    }

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("verify Update")
    @TmsLink("")
    @Owner("Aleksei.Bochkarev")
    @Tags({ @Tag("7.5") })
    public void verifyUpdate() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateRole(),
                getHttpSamplerForUpdateRole());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 2, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_UPDATE > stats.overall().sampleTimePercentile99().toMillis(),
                "Role update is very slowly");
    }

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("verify Patch")
    @TmsLink("")
    @Owner("Aleksei.Bochkarev")
    @Tags({ @Tag("7.5") })
    public void verifyUpdatePatch() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateRole(),
                getHttpSamplerForPatchRole());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 2, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_UPDATE > stats.overall().sampleTimePercentile99().toMillis(),
                "role patch is very slowly");
    }

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("verify Get All")
    @TmsLink("")
    @Owner("Aleksei.Bochkarev")
    @Tags({ @Tag("7.5") })
    public void verifyGetAll() {
        TestPlanStats stats =
                jmeterUtil.getTestPlanStats(threads, iterations, getHttpSamplerForGetRoles());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_GET_ALL > stats.overall().sampleTimePercentile99().toMillis(),
                "RoleGetAll is very slowly");
    }

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("verify Find")
    @TmsLink("")
    @Owner("Aleksei.Bochkarev")
    @Tags({ @Tag("7.5") })
    public void verifyFind() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateRole(),
                getHttpSamplerForFindRole());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 2, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_FIND > stats.overall().sampleTimePercentile99()
                .toMillis(), "Role find is very slowly");
    }

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("verify Delete")
    @TmsLink("")
    @Owner("Aleksei.Bochkarev")
    @Tags({ @Tag("7.5") })
    public void verifyDelete() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateRole(),
                getHttpSamplerForDeleteRole());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 2, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_UPDATE > stats.overall().sampleTimePercentile99()
                .toMillis(), "Role deleted is very slowly");
    }

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("verify Batch Delete")
    @TmsLink("")
    @Owner("Konstantin.Zolotovskiy")
    @Tags({ @Tag("7.5") })
    public void verifyBatchDelete() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateRoleBatch(),
                getHttpSamplerForDeleteRoleBatch());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 2, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_CREATE_BATCH > stats.overall().sampleTimePercentile99()
                .toMillis(), "Role deleted is very slowly");
    }

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("verify Batch Create")
    @TmsLink("PORT-5863")
    @Owner("Konstantin.Zolotovskiy")
    @Tags({ @Tag("7.5") })
    public void verifyBatchCreate() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateRoleBatch());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_CREATE_BATCH > stats.overall().sampleTimePercentile99()
                .toMillis(), "Roles created is very slowly");
    }

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("verify Batch Update")
    @TmsLink("PORT-5864")
    @Owner("Konstantin.Zolotovskiy")
    @Tags({ @Tag("7.5") })
    public void verifyBatchUpdate() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateRoleBatch(),
                getHttpSamplerForUpdateRoleBatch());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 2, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_CREATE_BATCH > stats.overall().sampleTimePercentile99()
                .toMillis(), "Roles created is very slowly");
    }

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("verify Batch role Patch")
    @TmsLink("PORT-5885")
    @Owner("Konstantin.Zolotovskiy")
    @Tags({ @Tag("7.5") })
    public void verifyBatchUpdatePatch() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateRoleBatch(),
                getHttpSamplerForUpdateRolePatchBatch());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 2, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_CREATE_BATCH > stats.overall().sampleTimePercentile99()
                .toMillis(), "Roles created is very slowly");
    }
}
