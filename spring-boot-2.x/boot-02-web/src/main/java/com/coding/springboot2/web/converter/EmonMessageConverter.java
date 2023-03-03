package com.coding.springboot2.web.converter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.coding.springboot2.web.bean.Person;

/**
 * 自定义的Converter
 */
public class EmonMessageConverter implements HttpMessageConverter<Person> {
    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return clazz.isAssignableFrom(Person.class);
    }

    /**
     * 服务器统计所有MessageConverter都能写出哪些内容类型
     * 
     * @return
     */
    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return MediaType.parseMediaTypes("application/emon");
    }

    @Override
    public Person read(Class<? extends Person> clazz, HttpInputMessage inputMessage)
        throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    public void write(Person person, MediaType contentType, HttpOutputMessage outputMessage)
        throws IOException, HttpMessageNotWritableException {
        // 自定义协议数据的写出
        String data = person.getUserName() + ";" + person.getAge() + ";" + person.getBirth();
        // 写出去
        OutputStream body = outputMessage.getBody();
        body.write(data.getBytes(StandardCharsets.UTF_8));
    }
}
