package ru.pozitron.pbe.shedulingtasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.pozitron.pbe.domain.Code;
import ru.pozitron.pbe.repository.CodeRepository;

import java.time.Duration;
import java.time.LocalDateTime;


@Component
public class ScheduledTasks {
    @Autowired
    CodeRepository codeRepository;
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Scheduled(fixedRate = 30000)
    public void delCode(){
        LocalDateTime DateNow = LocalDateTime.now();
        Iterable<Code> codes = codeRepository.findAll();
        for (Code code:
             codes) {
            if (Math.abs(Duration.between(code.getDate(),DateNow).toSeconds()) >= 180){
                codeRepository.deleteById(code.getId());
                log.info("between " + Math.abs(Duration.between(code.getDate(), DateNow).toSeconds()));
            }
        }

    }
}
