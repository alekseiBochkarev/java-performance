package test_cases.orgunit;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static util.AllureAttachments.addAllureAttaches;

public class OrgUnitLoadTest extends OrgUnitLoadTestData {

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("")
    @Link(name = "", url = "")
    @TmsLink("")
    @Owner("Aleksei.Bochkarev")
    @Tags({ @Tag("7.5") })
    void verifyCreate() {
        TestPlanStats stats =
                jmeterUtil.getTestPlanStats(threads, iterations, getHttpSamplerForCreateOrgUnit());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount(), "too much errors");
        assertEquals((long) threads * (long) iterations,
                stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_CREATE > stats.overall().sampleTimePercentile99().toMillis(),
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
                getHttpSamplerForCreateOrgUnit(),
                getHttpSamplerForUpdateOrgUnit());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 2, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_UPDATE > stats.overall().sampleTimePercentile99().toMillis(),
                "OrgUnit update is very slowly");
    }

    @Test
    @DisplayName("verify Batch Create")
    public void verifyBatchCreate() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateOrgUnitBatch());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_CREATE_BATCH > stats.overall().sampleTimePercentile99()
                .toMillis(), "Batch orgUnit created is very slowly");
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
                getHttpSamplerForCreateOrgUnit(),
                getHttpSamplerForPatchOrgUnit());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 2, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_UPDATE > stats.overall().sampleTimePercentile99().toMillis(),
                "orgUnit patch is very slowly");
    }

    @Test
    @DisplayName("verify Batch Delete")
    public void verifyBatchDelete() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateOrgUnitBatch(),
                getHttpSamplerForDeleteOrgUnitBatch());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 2, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_CREATE_BATCH > stats.overall().sampleTimePercentile99()
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
                jmeterUtil.getTestPlanStats(threads, iterations, getHttpSamplerForGetOrgUnits());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_GET_ALL > stats.overall().sampleTimePercentile99().toMillis(),
                "orgUnit GetAll is very slowly");
    }

    @Test
    @DisplayName("verify Batch Update")
    public void verifyBatchUpdate() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateOrgUnitBatch(),
                getHttpSamplerForUpdateOrgUnitBatch());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 2, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_CREATE_BATCH > stats.overall().sampleTimePercentile99()
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
                getHttpSamplerForCreateOrgUnit(),
                getHttpSamplerForFindOrgUnit());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 2, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_FIND > stats.overall().sampleTimePercentile99()
                .toMillis(), "OrgUnit find is very slowly");
    }

    @Test
    @DisplayName("verify Batch OrgUnit Patch")
    public void verifyBatchUpdatePatch() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateOrgUnitBatch(),
                getHttpSamplerForUpdateOrgUnitPatchBatch());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 2, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_CREATE_BATCH > stats.overall().sampleTimePercentile99()
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
                getHttpSamplerForCreateOrgUnit(),
                getHttpSamplerForDeleteOrgUnit());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 2, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_UPDATE > stats.overall().sampleTimePercentile99()
                .toMillis(), "OrgUnit deleted is very slowly");
    }
}
