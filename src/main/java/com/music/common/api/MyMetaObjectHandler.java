package com.music.common.api;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    /*
    * 公共字段填充
    *
    * */

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("insert自动填充");
        metaObject.setValue("createTime", new Date());
        metaObject.setValue("updateTime", new Date());

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("update自动填充");

        metaObject.setValue("updateTime", new Date());
    }
}
