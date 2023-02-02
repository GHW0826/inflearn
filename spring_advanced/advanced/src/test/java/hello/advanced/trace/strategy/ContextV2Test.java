package hello.advanced.trace.strategy;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class ContextV2Test {
    /**
     *전략 패턴 적용
     */
    @Test
    void strategyV1() {
        ContextV2 context = new ContextV2();
        context.execute(new StrategyLogic1());
        context.execute(new StrategyLogic2());
    }
}