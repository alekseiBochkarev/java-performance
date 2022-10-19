package util;

import io.qameta.allure.Allure;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;

public class AllureAttachments {

    public static void addAllureAttaches(TestPlanStats stats) {
        Allure.addAttachment("stats", "text/plain", String.valueOf(stats.labels()));
        Allure.addAttachment("errorsCount", "text/plain",
                String.valueOf(stats.overall().errorsCount()));
        Allure.addAttachment("samplesCount", "text/plain",
                String.valueOf(stats.overall().samplesCount()));
        Allure.addAttachment("sampleTimePercentile99", "text/plain",
                String.valueOf(stats.overall().sampleTimePercentile99()));
    }

}
