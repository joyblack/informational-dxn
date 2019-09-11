package com.joy.xxfy.informationaldxn.module.staff.domain.repository;


import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.staff.domain.enetiy.StaffBlacklistEntity;
import com.joy.xxfy.informationaldxn.module.staff.domain.enetiy.StaffPersonalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StaffBlacklistRepository extends BaseRepository<StaffBlacklistEntity>, JpaRepository<StaffBlacklistEntity, Long> {
    /**
     * 通过个人信息查找黑名单信息
     */
    StaffBlacklistEntity findFirstByStaffPersonal(StaffPersonalEntity staffPersonalEntity);

    /**
     * 通过个人信息删除黑名单
     */
    @Modifying
    @Query("update StaffBlacklistEntity b set b.isDelete = :isDelete where b.staffPersonal = :staffPersonal")
    void updateIsDeleteByStaffPersonal(@Param("isDelete") Boolean isDelete, @Param("staffPersonal") StaffPersonalEntity personalEntity);

    // 通过身份证获取
    @Query("from StaffBlacklistEntity b where b.staffPersonal.idNumber = :idNumber")
    StaffBlacklistEntity findAllByIdNumber(@Param("idNumber") String idNumber);
}
