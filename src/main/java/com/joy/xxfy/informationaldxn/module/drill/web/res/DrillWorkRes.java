package com.joy.xxfy.informationaldxn.module.drill.web.res;

import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillHoleEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillWorkEntity;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class DrillWorkRes extends DrillWorkEntity {
    private List<DrillHoleEntity> drillHole = new ArrayList<>();
}
