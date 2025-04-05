package com.coding.boot3.r2dbc;

import com.coding.boot3.r2dbc.entity.TAuthor;
import com.coding.boot3.r2dbc.entity.TBook;
import com.coding.boot3.r2dbc.repositories.AuthorRepositories;
import com.coding.boot3.r2dbc.repositories.BookRepositories;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.r2dbc.core.DatabaseClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 最佳实践：
 * 1、Spring Data R2DBC，基础的CRUD用 R2dbcRepository
 * 2、自定义复杂的SQL（单表以及不太复杂的多表SQL）：@Query
 * 3、多表查询复杂结果集： DatabaseClient 自定义SQL以及结果封装
 */
@SpringBootTest
public class R2dbcTests {

    /**
     * 缺点：join 查询不好做；单表查询好做
     */
    @Autowired
    R2dbcEntityTemplate template; // CRUD API; 更多API操作示例：https://docs.spring.io/spring-data/relational/reference/r2dbc/entity-persistence.html

    /**
     * 贴近底层，join 操作好做；复杂查询也好做
     */
    @Autowired
    DatabaseClient client; // 数据库客户端

    @Autowired
    AuthorRepositories authorRepo;

    @Autowired
    BookRepositories bookRepo;

    @Autowired
    R2dbcCustomConversions r2dbcCustomConversions;

    @Test
    void r2dbcEntityTemplate() throws InterruptedException {
        // 1、Criteria构造查询条件
        Criteria criteria = Criteria.empty();
        criteria.and("id").is(1L).and("name").is("张三");
        // 2、封装为Query对象
        Query query = Query.query(criteria);
        // 3、执行查询
        template.select(query, TAuthor.class).subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    void insert() {
        template.insert(TAuthor.class).using(new TAuthor(null, "Joshua Bloch")).then().subscribe();
    }

    @Test
    void databaseClient() throws InterruptedException {
        client.sql("select * from t_author where id=?")
                .bind(0, 2L)
                .fetch()
                .all()
                .map(row -> {
                    TAuthor tAuthor = new TAuthor();
                    tAuthor.setId(Long.parseLong(row.get("id").toString()));
                    tAuthor.setName(row.get("name").toString());
                    return tAuthor;
                })
                .subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    void authorRepositories() throws InterruptedException {
        authorRepo.findAll().subscribe(System.out::println);
        authorRepo.findAllByIdInAndNameLike(java.util.List.of(1L, 2L), "张%").subscribe(System.out::println);
        authorRepo.findComplexAll(java.util.List.of(1L, 2L), "张%").subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    void bookRepositories() throws InterruptedException {
        bookRepo.findAll().subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    void bookAndAuthor() throws InterruptedException {
        bookRepo.findBookAndAuthor(1L).subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    void booAndAuthor2() throws InterruptedException {
        client.sql("""
                        select tb.*, ta.name from t_book tb\s
                        left join t_author ta on ta.id = tb.author_id\s
                        where tb.id = :bookId;""")
                .bind("bookId", 1L)
                .fetch()
                .all()
                .map(row -> {
                    TBook tBook = new TBook();
                    tBook.setId(Long.parseLong(row.get("id").toString()));
                    tBook.setTitle(row.get("title").toString());
                    tBook.setAuthorId(Long.parseLong(row.get("author_id").toString()));
                    tBook.setPublishTime(LocalDateTime.parse(row.get("publish_time").toString()));
                    tBook.setAuthor(new TAuthor(Long.parseLong(row.get("author_id").toString()), row.get("name").toString()));
                    return tBook;
                })
                .subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    void oneToN() throws InterruptedException {
        client.sql("""
                        select ta.name, tb.* from t_author ta\s
                        left join t_book tb on tb.author_id = ta.id\s
                        where tb.id is not null and ta.id = :authorId\s
                        order by ta.id
                        """)
                .bind("authorId", 2L)
                .fetch().all()
                .bufferUntilChanged(rowMap -> rowMap.get("author_id").toString())
                .map(list -> {
                    TAuthor tAuthor = new TAuthor();
                    tAuthor.setId(Long.parseLong(list.get(0).get("author_id").toString()));
                    tAuthor.setName(list.get(0).get("name").toString());

                    List<TBook> books = list.stream().map(rowMap ->
                            new TBook(Long.parseLong(rowMap.get("id").toString()),
                                    rowMap.get("title").toString(),
                                    Long.parseLong(rowMap.get("author_id").toString()),
                                    LocalDateTime.parse(rowMap.get("publish_time").toString()))).toList();
                    tAuthor.setBooks(books);
                    return tAuthor;
                })
                .subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(1);
    }

}
