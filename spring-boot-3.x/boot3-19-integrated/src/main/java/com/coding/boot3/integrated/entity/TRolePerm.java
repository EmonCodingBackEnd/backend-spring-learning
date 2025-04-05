package com.coding.boot3.integrated.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Table(name = "t_role_perm")
public class TRolePerm {
    @Id
    private Long id;

    private Long roleId;

    private Long permId;

    private Instant createTime;

    private Instant updateTime;

}
