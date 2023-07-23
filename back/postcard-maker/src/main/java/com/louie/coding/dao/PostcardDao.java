package com.louie.coding.dao;

import com.louie.coding.entity.Postcard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostcardDao {
    Postcard getPostcardWithContentBySpecificVersion(@Param("id") Long id, @Param("userId") Long userId, @Param("version") Long version);

    void addPostcard(Postcard postcard);

    Postcard getRecentUpdatedPostcardWithContentByUserId(Long userId);

    Integer getPostcardCountByUserId(Long userId);

    // todo change map to annotation
    Postcard getPostcardWithContentByUserIdAndId(@Param("id") Long postcardId, @Param("userId") Long userId);

    Postcard getByIdAndUserId(@Param("id") Long postcardId, @Param("userId") Long userId);

    void updatePostcard(Postcard postcardDb);
//    Postcard getRecentProjectByUserId(Long userId);
//
//    void addProject(Postcard postcard);
//
//    void addCanvas(Canvas canvas);
//
//    void addElements(@Param("elements") List<Element> elements);
//
//    void addElement(Element element);
//
//    void updateElement(Element element);
//
//    void updateProjectTimeByProjectIdAndUserId(@Param("updateTime") Date now, @Param("projectId") Long projectId, @Param("userId") Long userId);
//
//    void updateCanvas(Canvas canvas);
//
//    void updateProjectInfoByProjectIdAndUserId(Postcard postcard);
//
//    List<Postcard> getPostCardsWithPagination(Map<String, Object> params);

}
