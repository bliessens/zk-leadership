package be.benoit.sizk;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.leader.event.AbstractLeaderEvent;
import org.springframework.integration.leader.event.OnGrantedEvent;
import org.springframework.integration.leader.event.OnRevokedEvent;

import java.time.LocalTime;

@SpringBootApplication
@ImportResource("classpath*:/spring/integration/zk-client.xml")
public class DistributedApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DistributedApp.class).run(args);
    }

    @Bean
    public ApplicationListener<AbstractLeaderEvent> listener() {

        return new ApplicationListener<AbstractLeaderEvent>() {
            @Override
            public void onApplicationEvent(AbstractLeaderEvent event) {
                if (OnGrantedEvent.class.isInstance(event)) {
                    System.out.println("Leadership granted " + LocalTime.now());
                } else if (OnRevokedEvent.class.isInstance(event)) {
                    System.out.println("Leadership revoke " + LocalTime.now());
                } else {
                    System.out.println("Other: " + event.toString());
                }
            }
        };
    }

//    @Bean
//    public Interruptor interruptor(final Candidate candidate) {
//        return new Interruptor(candidate);
//    }

//    @Bean
//    public Candidate myCandidate() {
//        return new MyCandidate();
//    }

//    static class Interruptor implements ApplicationListener<OnGrantedEvent> {
//
//        private final Timer timer = new Timer();
//        private Candidate candidate;
//
//        public Interruptor(Candidate candidate) {
//            this.candidate = candidate;
//        }
//
//        @Override
//        public void onApplicationEvent(OnGrantedEvent event) {
//
//        }
//    }

//    private static class MyCandidate implements Candidate {
//        private final Timer timer = new Timer();
//
//        @Override
//        public String getRole() {
//            return "integrator";
//        }
//
//        @Override
//        public String getId() {
//            return UUID.randomUUID().toString();
//        }
//
//        @Override
//        public void onGranted(Context ctx) throws InterruptedException {
//            System.out.println();
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    System.out.println("Yielding leadership");
//                    ctx.yield();
//                }
//            }, 5000);
//        }
//
//        @Override
//        public void onRevoked(Context ctx) {
//            System.out.println();
//        }
//    }
}