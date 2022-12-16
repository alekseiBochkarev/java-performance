package test_cases.virtualdbgroup;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static util.AllureAttachments.addAllureAttaches;

public class VirtualDbGroupLoadTest extends VirtualDbGroupLoadTestData {

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("")
    @Link(name = "", url = "")
    @TmsLink("")
    @Owner("Aleksei.Bochkarev")
    @Tags({ @Tag("7.5") })
    void verifyCreate() {
        TestPlanStats stats =
                jmeterUtil.getTestPlanStats(threads,
                        iterations,
                        getHttpSamplerForCreateVirtualDbGroup());
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
                getHttpSamplerForCreateVirtualDbGroup(),
                getHttpSamplerForCreateVirtualDb(),
                getHttpSamplerForUpdateVirtualDbGroup());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 3, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_UPDATE * 3> stats.overall().sampleTimePercentile99().toMillis(),
                "VirtualDbGroup update is very slowly");
    }

    @Test
    @DisplayName("verify Batch Create")
    public void verifyBatchCreate() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateVirtualDbGroupBatch());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_CREATE_BATCH > stats.overall().sampleTimePercentile99()
                .toMillis(), "Batch virtualDbGroup created is very slowly");
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
                getHttpSamplerForPatchVirtualDbGroup());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 2, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_UPDATE * 2> stats.overall().sampleTimePercentile99().toMillis(),
                "virtualDbGroup patch is very slowly");
    }

    @Test
    @DisplayName("verify Batch Delete")
    public void verifyBatchDelete() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateVirtualDbGroupBatch(),
                getHttpSamplerForDeleteVirtualDbGroupBatch());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 2, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_CREATE_BATCH * 2> stats.overall().sampleTimePercentile99()
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
                jmeterUtil.getTestPlanStats(threads,
                        iterations,
                        getHttpSamplerForGetVirtualDbGroups());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_GET_ALL > stats.overall().sampleTimePercentile99().toMillis(),
                "virtualDbGroup GetAll is very slowly");
    }

    @Test
    @DisplayName("verify Batch Update")
    public void verifyBatchUpdate() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateVirtualDbGroupBatch(),
                getHttpSamplerForCreateVirtualDb(),
                getHttpSamplerForUpdateVirtualDbGroupBatch());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 3, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_CREATE_BATCH * 3> stats.overall().sampleTimePercentile99()
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
                getHttpSamplerForFindVirtualDbGroup());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 2, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_FIND * 2> stats.overall().sampleTimePercentile99()
                .toMillis(), "VirtualDbGroup find is very slowly");
    }

    @Test
    @DisplayName("verify Batch VirtualDbGroup Patch")
    public void verifyBatchUpdatePatch() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateVirtualDbGroupBatch(),
                getHttpSamplerForCreateVirtualDb(),
                getHttpSamplerForUpdateVirtualDbGroupPatchBatch());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 3, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_CREATE_BATCH * 3> stats.overall().sampleTimePercentile99()
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
                getHttpSamplerForDeleteVirtualDbGroup());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 2, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_UPDATE * 2> stats.overall().sampleTimePercentile99()
                .toMillis(), "VirtualDbGroup deleted is very slowly");
    }
}
