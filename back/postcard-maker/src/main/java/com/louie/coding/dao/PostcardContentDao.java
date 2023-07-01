package com.louie.coding.dao;

import com.louie.coding.entity.PostcardContent;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostcardContentDao {
    void addPostcardContent(PostcardContent postcardContent);

    Long getMaxVersionByPostcardId(Long postcardId);
}
