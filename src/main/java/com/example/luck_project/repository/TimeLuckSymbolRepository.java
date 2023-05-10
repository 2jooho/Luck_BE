package com.example.luck_project.repository;

import com.example.luck_project.domain.LuckCalendaEntity;
import com.example.luck_project.domain.TimeLuckSymbolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TimeLuckSymbolRepository extends JpaRepository<TimeLuckSymbolEntity, String> {
    //시주 정보 조회
    Optional<TimeLuckSymbolEntity> findByKoDayTopAndTimeType(String kDayTop, String timeType);

}
