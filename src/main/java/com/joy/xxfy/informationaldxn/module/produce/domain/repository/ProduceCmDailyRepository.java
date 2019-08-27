package com.joy.xxfy.informationaldxn.module.produce.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillHoleEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillWorkEntity;
import com.joy.xxfy.informationaldxn.module.produce.domain.entity.ProduceCmDailyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ProduceCmDailyRepository extends BaseRepository<ProduceCmDailyEntity>, JpaRepository<ProduceCmDailyEntity, Long> {
   // 获取某平台/某日的日报信息：belong_company_id = ? and daily_time = ?
   ProduceCmDailyEntity findAllByBelongCompanyAndDailyTime(DepartmentEntity company, Date dailyTime);

}
