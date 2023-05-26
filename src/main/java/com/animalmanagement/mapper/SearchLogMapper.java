package com.animalmanagement.mapper;

import com.animalmanagement.entity.SearchLog;
import com.animalmanagement.example.SearchLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SearchLogMapper {
    long countByExample(SearchLogExample example);

    int deleteByExample(SearchLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SearchLog record);

    int insertSelective(SearchLog record);

    List<SearchLog> selectByExampleWithRowbounds(SearchLogExample example, RowBounds rowBounds);

    List<SearchLog> selectByExample(SearchLogExample example);

    SearchLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SearchLog record, @Param("example") SearchLogExample example);

    int updateByExample(@Param("record") SearchLog record, @Param("example") SearchLogExample example);

    int updateByPrimaryKeySelective(SearchLog record);

    int updateByPrimaryKey(SearchLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table searchlog
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    SearchLog selectOneByExample(SearchLogExample example);
}