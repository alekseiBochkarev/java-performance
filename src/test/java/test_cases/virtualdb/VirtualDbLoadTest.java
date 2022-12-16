package test_cases.virtualdb;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static util.AllureAttachments.addAllureAttaches;

public class VirtualDbLoadTest extends VirtualDbLoadTestData {

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
                        getHttpSamplerForCreateVirtualDb());
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
                getHttpSamplerForCreateVirtualDb(),
                getHttpSamplerForUpdateVirtualDb());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 2, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_UPDATE > stats.overall().sampleTimePercentile99().toMillis(),
                "VirtualDb update is very slowly");
    }

    @Test
    @DisplayName("verify Batch Create")
    public void verifyBatchCreate() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateVirtualDbBatch());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_CREATE_BATCH > stats.overall().sampleTimePercentile99()
                .toMillis(), "Batch virtualDb created is very slowly");
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
                getHttpSamplerForCreateVirtualDb(),
                getHttpSamplerForPatchVirtualDb());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 2, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_UPDATE > stats.overall().sampleTimePercentile99().toMillis(),
                "virtualDb patch is very slowly");
    }

    @Test
    @DisplayName("verify Batch Delete")
    public void verifyBatchDelete() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateVirtualDbBatch(),
                getHttpSamplerForDeleteVirtualDbBatch());
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
                jmeterUtil.getTestPlanStats(threads, iterations, getHttpSamplerForGetVirtualDbs());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_GET_ALL > stats.overall().sampleTimePercentile99().toMillis(),
                "virtualDb GetAll is very slowly");
    }

    @Test
    @DisplayName("verify Batch Update")
    public void verifyBatchUpdate() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateVirtualDbBatch(),
                getHttpSamplerForUpdateVirtualDbBatch());
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
                getHttpSamplerForCreateVirtualDb(),
                getHttpSamplerForFindVirtualDb());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 2, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_FIND > stats.overall().sampleTimePercentile99()
                .toMillis(), "VirtualDb find is very slowly");
    }

    @Test
    @DisplayName("verify Batch VirtualDb Patch")
    public void verifyBatchUpdatePatch() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateVirtualDbBatch(),
                getHttpSamplerForUpdateVirtualDbPatchBatch());
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
                getHttpSamplerForCreateVirtualDb(),
                getHttpSamplerForDeleteVirtualDb());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 2, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_UPDATE > stats.overall().sampleTimePercentile99()
                .toMillis(), "VirtualDb deleted is very slowly");
    }
}
