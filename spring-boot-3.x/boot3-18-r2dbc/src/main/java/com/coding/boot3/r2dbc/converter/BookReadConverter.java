package com.coding.boot3.r2dbc.converter;

import com.coding.boot3.r2dbc.entity.TAuthor;
import com.coding.boot3.r2dbc.entity.TBook;
import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.time.LocalDateTime;

@ReadingConverter  // 读取数据库数据的时候，把Row转换为TBook
public class BookReadConverter implements Converter<Row, TBook> {

    @Override
    public TBook convert(Row source) {
        // 自定义结果集的封装
        TBook tBook = new TBook();
        tBook.setId(source.get("id", Long.class));
        tBook.setTitle(source.get("title", String.class));
        tBook.setAuthorId(source.get("author_id", Long.class));
        tBook.setPublishTime(source.get("publish_time", LocalDateTime.class));
        if (source.getMetadata().contains("name")) {
            tBook.setAuthor(new TAuthor(source.get("author_id", Long.class), source.get("name", String.class)));
        }
        return tBook;
    }
}
