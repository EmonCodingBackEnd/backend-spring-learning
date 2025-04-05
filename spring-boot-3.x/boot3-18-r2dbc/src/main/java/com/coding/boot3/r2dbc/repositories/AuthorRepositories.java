package com.coding.boot3.r2dbc.repositories;

import com.coding.boot3.r2dbc.entity.TAuthor;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Collection;

@Repository
public interface AuthorRepositories extends R2dbcRepository<TAuthor, Long> {
    // 默认继承了一堆CRUD方法；类似 mybatis-plus 的 IService 接口

    Flux<TAuthor> findAllByIdInAndNameLike(Collection<Long> ids, String name);

    @Query("select * from t_author where id in (:ids) and name like :name")
    Flux<TAuthor> findComplexAll(Collection<Long> ids, String name);
}
