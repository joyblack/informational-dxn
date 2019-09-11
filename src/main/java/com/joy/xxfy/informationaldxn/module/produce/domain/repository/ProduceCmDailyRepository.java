package com.joy.xxfy.informationaldxn.module.produce.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.produce.domain.entity.ProduceCmDailyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface ProduceCmDailyRepository extends BaseRepository<ProduceCmDailyEntity>, JpaRepository<ProduceCmDailyEntity, Long> {
   // 获取某平台/某日的日报信息：belong_company_id = ? and daily_time = ?
   ProduceCmDailyEntity findFirstByBelongCompanyAndDailyTime(DepartmentEntity company, Date dailyTime);

}
