package test_cases.groups;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static util.AllureAttachments.addAllureAttaches;

public class GroupLoadTest extends GroupLoadTestData {

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("Test performance create group unified platform")
    @Link(name = "PORT-5888", url = "https://jira-brandmaker.atlassian.net/browse/PORT-5888")
    @TmsLink("PORT-5888")
    @Owner("Aleksei.Bochkarev")
    @Tags({ @Tag("7.5") })
    void verifyCreate() {
        String performanceType = "maxPerformance";
        for (int i = 1; i < 100; i ++) {
            TestPlanStats stats =
                    jmeterUtil.getTestPlanStats(threads +  i,iterations, getHttpSamplerForCreateGroup());
            addAllureAttaches(stats);
            assertEquals(0, stats.overall().errorsCount(), "too much errors");
            //assertEquals((long) threads * (long) iterations, stats.overall().samplesCount());
            assertTrue(MAX_DELAY_TIME_FOR_CREATE > stats.overall().sampleTimePercentile99().toMillis(),
                    "response is very slowly");
        }
    }

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("Test performance update group unified platform")
    @Link(name = "PORT-5887", url = "https://jira-brandmaker.atlassian.net/browse/PORT-5887")
    @TmsLink("PORT-5887")
    @Owner("Aleksei.Bochkarev")
    @Tags({ @Tag("7.5") })
    void verifyUpdate() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateGroup(),
                getHttpSamplerForUpdateGroup());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 2, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_UPDATE * 2 > stats.overall().sampleTimePercentile99().toMillis(),
                "Group update is very slowly");
    }

    @Test
    void verifyJustUpdate() {
        TestPlanStats statsBefore = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateGroup());
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForJustUpdateGroup());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_UPDATE > stats.overall().sampleTimePercentile99().toMillis(),
                "Group update is very slowly");
    }

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("verify Batch Create")
    @Link(name = "PORT-5900", url = "https://jira-brandmaker.atlassian.net/browse/PORT-5900")
    @TmsLink("PORT-5900")
    @Owner("Konstantin.Zolotovskiy")
    @Tags({ @Tag("7.5") })
    public void verifyBatchCreate() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateGroupBatch());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_CREATE_BATCH > stats.overall().sampleTimePercentile99()
                .toMillis(), "Batch group created is very slowly");
    }

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("Test performance update patch group unified platform")
    @Link(name = "PORT-5886", url = "https://jira-brandmaker.atlassian.net/browse/PORT-5886")
    @TmsLink("PORT-5886")
    @Owner("Aleksei.Bochkarev")
    @Tags({ @Tag("7.5") })
    void verifyUpdatePatch() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateGroup(),
                getHttpSamplerForPatchGroup());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 2, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_UPDATE > stats.overall().sampleTimePercentile99().toMillis(),
                "group patch is very slowly");
    }

    @Test
    void verifyJustUpdatePatch() {
        TestPlanStats statsBefore = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateGroup());
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForJustPatchGroup());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_UPDATE > stats.overall().sampleTimePercentile99().toMillis(),
                "Group patch is very slowly");
    }

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("verify Batch Delete")
    @Link(name = "PORT-5901", url = "https://jira-brandmaker.atlassian.net/browse/PORT-5901")
    @TmsLink("PORT-5901")
    @Owner("Konstantin.Zolotovskiy")
    @Tags({ @Tag("7.5") })
    public void verifyBatchDelete() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateGroupBatch(),
                getHttpSamplerForDeleteGroupBatch());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 2, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_CREATE_BATCH > stats.overall().sampleTimePercentile99()
                .toMillis(), "Response is very slowly");
    }

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("Test performance get all groups unified platform")
    @Link(name = "PORT-5902", url = "https://jira-brandmaker.atlassian.net/browse/PORT-5902")
    @TmsLink("PORT-5902")
    @Owner("Aleksei.Bochkarev")
    @Tags({ @Tag("7.5") })
    void verifyGetAll() {
        TestPlanStats stats =
                jmeterUtil.getTestPlanStats(threads, iterations, getHttpSamplerForGetGroups());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_GET_ALL > stats.overall().sampleTimePercentile99().toMillis(),
                "group GetAll is very slowly");
    }

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("verify Batch Update")
    @Link(name = "PORT-5903", url = "https://jira-brandmaker.atlassian.net/browse/PORT-5903")
    @TmsLink("PORT-5903")
    @Owner("Konstantin.Zolotovskiy")
    @Tags({ @Tag("7.5") })
    public void verifyBatchUpdate() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateGroupBatch(),
                getHttpSamplerForUpdateGroupBatch());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 2, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_CREATE_BATCH > stats.overall().sampleTimePercentile99()
                .toMillis(), "Response is very slowly");
    }

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("Test performance verify find group unified platform")
    @Link(name = "PORT-5904", url = "https://jira-brandmaker.atlassian.net/browse/PORT-5904")
    @TmsLink("PORT-5904")
    @Owner("Aleksei.Bochkarev")
    @Tags({ @Tag("7.5") })
    void verifyFind() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateGroup(),
                getHttpSamplerForFindGroup());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 2, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_FIND > stats.overall().sampleTimePercentile99()
                .toMillis(), "Group find is very slowly");
    }

    @Test
    void verifyJustFind() {
        TestPlanStats statsBefore = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateGroup());
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForJustFindGroup());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_FIND > stats.overall().sampleTimePercentile99().toMillis(),
                "Group find is very slowly");
    }

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("verify Batch Group Patch")
    @Link(name = "PORT-5905", url = "https://jira-brandmaker.atlassian.net/browse/PORT-5905")
    @TmsLink("PORT-5905")
    @Owner("Konstantin.Zolotovskiy")
    @Tags({ @Tag("7.5") })
    public void verifyBatchUpdatePatch() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateGroupBatch(),
                getHttpSamplerForUpdateGroupPatchBatch());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 2, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_CREATE_BATCH > stats.overall().sampleTimePercentile99()
                .toMillis(), "Response is very slowly");
    }

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("Test performance verify delete group unified platform")
    @Link(name = "PORT-5906", url = "https://jira-brandmaker.atlassian.net/browse/PORT-5906")
    @TmsLink("PORT-5906")
    @Owner("Aleksei.Bochkarev")
    @Tags({ @Tag("7.5") })
    void verifyDelete() {
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateGroup(),
                getHttpSamplerForDeleteGroup());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations * 2, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_UPDATE > stats.overall().sampleTimePercentile99()
                .toMillis(), "Group deleted is very slowly");
    }

    @Test
    void verifyJustDelete() {
        TestPlanStats beforeStats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForCreateGroup());
        TestPlanStats stats = jmeterUtil.getTestPlanStats(threads,
                iterations,
                getHttpSamplerForJustDeleteGroup());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount());
        assertEquals((long) threads * (long) iterations, stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_UPDATE > stats.overall().sampleTimePercentile99()
                .toMillis(), "Group deleted is very slowly");
    }
}
