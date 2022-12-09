package com.example.luck_project.repository;

import com.example.luck_project.domain.LuckCalendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LuckCalendaRepository extends JpaRepository<LuckCalendaEntity, String> {
    //양력의 정보 조회
    Optional<LuckCalendaEntity> findByCdSyAndCdSmAndCdSd(String year, String month, String day);

    //음력의 정보 조회
    Optional<LuckCalendaEntity> findByCdLyAndCdLmAndCdLd(String year, String month, String day);

}
