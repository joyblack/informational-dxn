package com.joy.xxfy.informationaldxn.module.staff.domain.repository;


import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.staff.domain.enetiy.StaffBlacklistEntity;
import com.joy.xxfy.informationaldxn.module.staff.domain.enetiy.StaffPersonalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StaffBlacklistRepository extends BaseRepository<StaffBlacklistEntity>, JpaRepository<StaffBlacklistEntity, Long> {
    /**
     * 通过个人信息查找黑名单信息
     */
    StaffBlacklistEntity findAllByStaffPersonal(StaffPersonalEntity staffPersonalEntity);

    // 通过身份证获取
    @Query("from StaffBlacklistEntity b where b.staffPersonal.idNumber = :idNumber")
    StaffBlacklistEntity findAllByIdNumber(@Param("idNumber") String idNumber);
}
