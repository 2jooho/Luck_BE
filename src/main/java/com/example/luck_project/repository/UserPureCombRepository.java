package com.example.luck_project.repository;

import com.example.luck_project.domain.PureInfoEntity;
import com.example.luck_project.domain.UserPureCombinationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserPureCombRepository extends JpaRepository<UserPureCombinationEntity, String> {
    Optional<UserPureCombinationEntity> findTop1ByLuckCnctnInAndVersYear(List<String> luckCnctnList, String versYear);
    Optional<List<UserPureCombinationEntity>> findTop3ByPureYearInOrPureDayInAndLuckCnctn(List<String> pureTypeList, List<String> pureTypeList2, String luckCnctn);

    Optional<List<UserPureCombinationEntity>> findTop3ByPureYearAndLuckCnctnAndPureDayIn(String pureType, String luckCnctn, List<String> pureTypeList);

    Integer countByLuckCnctnIn(List<String> luckCnctnList);

}
