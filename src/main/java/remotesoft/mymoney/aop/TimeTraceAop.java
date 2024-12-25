package remotesoft.mymoney.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
//@Component //AOP는 특별관리대상으로 컴포넌트스캔보다는 @Bean으로 등록하는 것이 관례이다.
public class TimeTraceAop {

    @Around("execution(* remotesoft.mymoney..*(..))")
    public Object excute(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        System.out.println("START: "+joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            System.out.println("END: "+joinPoint.toString() + " elapsed time: "+elapsedTime + "ms");
        }
    }
}
