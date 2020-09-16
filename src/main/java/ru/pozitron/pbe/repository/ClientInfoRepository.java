package ru.pozitron.pbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pozitron.pbe.domain.ClientInfo;

public interface ClientInfoRepository extends JpaRepository<ClientInfo,Long> {

}
