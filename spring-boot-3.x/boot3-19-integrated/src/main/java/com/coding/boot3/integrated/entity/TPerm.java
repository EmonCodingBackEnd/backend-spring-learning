package com.coding.boot3.integrated.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Table(name = "t_perm")
public class TPerm {
    @Id
    private Long id;

    private String value;

    private String uri;

    private String description;

    private Instant createTime;

    private Instant updateTime;

}
