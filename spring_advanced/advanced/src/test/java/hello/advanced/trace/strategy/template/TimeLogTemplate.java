package hello.advanced.trace.strategy.template;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeLogTemplate {

    public void execute(Callback callback) {

        //비즈니스 로직 실행
        long startTime = System.currentTimeMillis();

        //위임
        callback.call();

        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }
}