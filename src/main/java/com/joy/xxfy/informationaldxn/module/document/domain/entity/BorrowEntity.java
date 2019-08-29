package com.joy.xxfy.informationaldxn.module.document.domain.entity;

import com.joy.xxfy.informationaldxn.module.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.document.domain.enums.PermissionTypeEnum;
import com.joy.xxfy.informationaldxn.module.user.domain.entity.UserEntity;
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
     * 借阅时间
     */
    @Column(nullable = false)
    private Date borrowTime;

}
