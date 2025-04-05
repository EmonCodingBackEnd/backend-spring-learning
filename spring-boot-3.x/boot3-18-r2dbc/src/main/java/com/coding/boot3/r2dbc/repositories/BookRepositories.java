package com.coding.boot3.r2dbc.repositories;

import com.coding.boot3.r2dbc.entity.TBook;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface BookRepositories extends R2dbcRepository<TBook, Long> {
    // 默认继承了一堆CRUD方法；类似 mybatis-plus 的 IService 接口

    // 不推荐直接使用 ? 占位符；推荐使用 :name 形式的命名参数
    /*@Query("""
            select tb.*, ta.name from t_book tb\s
            left join t_author ta on ta.id = tb.author_id\s
            where tb.id = ?;""")*/
    @Query("""
            select tb.*, ta.name from t_book tb\s
            left join t_author ta on ta.id = tb.author_id\s
            where tb.id = :bookId;""")
    Mono<TBook> findBookAndAuthor(@Param("bookId") Long bookId);
}
