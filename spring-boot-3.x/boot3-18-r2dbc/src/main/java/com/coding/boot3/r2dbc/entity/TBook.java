package com.coding.boot3.r2dbc.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("t_book")
@Data
@NoArgsConstructor
public class TBook {

    public TBook(Long id, String title, Long authorId, LocalDateTime publishTime) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.publishTime = publishTime;
    }

    @Id
    private Long id;
    private String title;
    private Long authorId;
    private LocalDateTime publishTime; // 在响应式中日期的映射用 Instant 或者 LocalXXX

    @Transient
    private TAuthor author;
}
