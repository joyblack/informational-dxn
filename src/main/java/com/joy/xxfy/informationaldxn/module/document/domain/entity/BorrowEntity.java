package com.joy.xxfy.informationaldxn.module.document.domain.entity;

import com.joy.xxfy.informationaldxn.module.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.module.common.enums.CommonYesEnum;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.document.domain.enums.ReturnStatusEnum;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

/**
 * 资料借阅
 */
@Entity
@Table(name = "document_borrow")
@Data
@ToString(callSuper = true)
@Where(clause = "is_delete = 0")
public class BorrowEntity extends BaseEntity {

    /**
     * 所属平台
     */
    @JoinColumn(name = "belong_company_id")
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    private DepartmentEntity belongCompany;

    /**
     * 资料名称
     */
    @Column(nullable = false)
    private String documentName;

    /**
     * 借阅人
     */
    @Column(nullable = false)
    private String borrowPeople;

    /**
     * 归还期限
     */
    @Column(nullable = false)
    private Date deadTime;

    /**
     * 归还状态
     */
    @Column(nullable = false)
    private ReturnStatusEnum returnStatus;


    /**
     * 超时归还，这里需要一个定时任务，定时设置是否超时
     */
    private CommonYesEnum isOverTime;

}
