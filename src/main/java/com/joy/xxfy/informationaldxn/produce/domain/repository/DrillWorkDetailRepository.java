package com.joy.xxfy.informationaldxn.produce.domain.repository;

import com.joy.xxfy.informationaldxn.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.produce.domain.entity.DrillHoleEntity;
import com.joy.xxfy.informationaldxn.produce.domain.entity.DrillWorkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DrillWorkDetailRepository extends BaseRepository<DrillHoleEntity>, JpaRepository<DrillHoleEntity, Long> {
   // drill_work_id = ?
   List<DrillHoleEntity> findAllByDrillWork(DrillWorkEntity drillWorkEntity);

   // select * where drill_work_id = ?
   @Query("from DrillHoleEntity d where d.drillWork.id = :drillWorkId")
   List<DrillHoleEntity> findAllByDrillWorkId(@Param("drillWorkId") Long drillWorkId);

   // set is_delete = ?
   @Modifying
   @Query("update DrillHoleEntity d set d.isDelete = :isDelete where d.drillWork = :drillWork")
   void updateIsDeleteByDrillWork(@Param("drillWork") DrillWorkEntity drillWorkEntity, @Param("isDelete") Boolean isDelete);

}
