package com.joy.xxfy.informationaldxn.staff.domain.enetiy;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joy.xxfy.informationaldxn.common.domain.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 工伤
 */
@Entity
@Data
@ToString(callSuper = true)
@Table(name = "staff_injury")
@Where(clause = "is_delete = 0")
public class StaffInjuryEntity extends BaseEntity {
    /**
     * 全局的个人信息
     */
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "staff_personal_id")
    @NotNull(message = "员工个人信息不能为空")
    private StaffPersonalEntity staffPersonal;

    /**
     * 工伤原因
     */
    @Lob
    @Column(nullable = false)
    private String injuryReasons;

    /**
     * 工伤时间
     */
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date injuryTime;

    /**
     * 工伤描述
     */
    @Lob
    private String injuryDescribes;


    /**
     * 主治医院
     */
    private String hospital;

    /**
     * 医治时间
     */
    private Date treatTime;

}
