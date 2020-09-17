package ru.pozitron.pbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pozitron.pbe.domain.Code;
import ru.pozitron.pbe.domain.CodeType;
import ru.pozitron.pbe.domain.User;

public interface CodeRepository extends JpaRepository<Code, Long> {
    Code findByValue(String value);
    Code findByUserAndCodeTypeAndValueNotNull(User user, CodeType codeType);
    Long countByUserAndCodeTypeAndValueNotNull(User user,CodeType codeType);
    void deleteByUserAndCodeType(User user, CodeType codeType);
    Code findByUserAndCodeType(User user, CodeType recoveryPassword);

}
