package ru.pozitron.pbe.repository;

import org.springframework.data.repository.CrudRepository;
import ru.pozitron.pbe.domain.Code;
import ru.pozitron.pbe.domain.CodeType;
import ru.pozitron.pbe.domain.User;

public interface CodeRepository extends CrudRepository<Code, Long> {
    Code findByValue(String value);
    Code findByUserAndCodeTypeAndValueNotNull(User user, CodeType codeType);
    Long countByUserAndCodeTypeAndValueNotNull(User user,CodeType codeType);
}
