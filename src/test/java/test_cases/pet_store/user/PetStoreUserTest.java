package test_cases.pet_store.user;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static util.AllureAttachments.addAllureAttaches;

public class PetStoreUserTest extends PetStoreUserTestData {

    @Test
    @Severity(value = SeverityLevel.NORMAL)
    @Story("Pet Store")
    @Feature("User")
    @DisplayName("Create user")
    @Owner("Egor.Kosov")
    void checkCreateUser()
    {
        TestPlanStats stats =
                jmeterUtil.getTestPlanStats(threads, iterations, getHttpSamplerForCreateUser());
        addAllureAttaches(stats);
        assertEquals(0, stats.overall().errorsCount(), "too much errors");
        assertEquals((long) threads * (long) iterations,
                stats.overall().samplesCount());
        assertTrue(MAX_DELAY_TIME_FOR_CREATE > stats.overall().sampleTimePercentile99().toMillis(),
                "response is very slowly");
    }
}
