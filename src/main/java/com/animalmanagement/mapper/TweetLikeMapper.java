package com.animalmanagement.mapper;

import com.animalmanagement.entity.TweetLikeKey;
import com.animalmanagement.example.TweetLikeExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface TweetLikeMapper {
    long countByExample(TweetLikeExample example);

    int deleteByExample(TweetLikeExample example);

    int deleteByPrimaryKey(TweetLikeKey key);

    int insert(TweetLikeKey record);

    int insertSelective(TweetLikeKey record);

    List<TweetLikeKey> selectByExampleWithRowbounds(TweetLikeExample example, RowBounds rowBounds);

    List<TweetLikeKey> selectByExample(TweetLikeExample example);

    int updateByExampleSelective(@Param("record") TweetLikeKey record, @Param("example") TweetLikeExample example);

    int updateByExample(@Param("record") TweetLikeKey record, @Param("example") TweetLikeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tweetlike
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    TweetLikeKey selectOneByExample(TweetLikeExample example);
}