package org.deepmagic.seata.e2e;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * E2EUtil
 *
 * @author chenbin
 * @since 2025/3/25 16:30
 */
public class E2EUtil {

    public static void writeE2EResFile(String outPutRes) {
        try {
            Files.write(Paths.get("/Users/chenbin/IdeaProjects/growup/growup-seata/src/main/resources/result.yaml"), outPutRes.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isInE2ETest() {
        Map<String, String> envs = System.getenv();
        String env = envs.getOrDefault("E2E_ENV", "");
        return "open".equals(env);
    }
}
