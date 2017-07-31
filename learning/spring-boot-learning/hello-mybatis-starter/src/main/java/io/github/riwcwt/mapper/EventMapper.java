package io.github.riwcwt.mapper;

import io.github.riwcwt.entity.Event;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface EventMapper {
    @Delete({
        "delete from event",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into event (id, tournament, ",
        "vname, team, event_date, ",
        "event_time, venue_address, ",
        "section, price, ",
        "service_fee, currency)",
        "values (#{id,jdbcType=BIGINT}, #{tournament,jdbcType=VARCHAR}, ",
        "#{vname,jdbcType=VARCHAR}, #{team,jdbcType=VARCHAR}, #{event_date,jdbcType=VARCHAR}, ",
        "#{event_time,jdbcType=VARCHAR}, #{venue_address,jdbcType=VARCHAR}, ",
        "#{section,jdbcType=VARCHAR}, #{price,jdbcType=VARCHAR}, ",
        "#{service_fee,jdbcType=VARCHAR}, #{currency,jdbcType=VARCHAR})"
    })
    int insert(Event record);

    @InsertProvider(type=EventSqlProvider.class, method="insertSelective")
    int insertSelective(Event record);

    @Select({
        "select",
        "id, tournament, vname, team, event_date, event_time, venue_address, section, ",
        "price, service_fee, currency",
        "from event",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="tournament", property="tournament", jdbcType=JdbcType.VARCHAR),
        @Result(column="vname", property="vname", jdbcType=JdbcType.VARCHAR),
        @Result(column="team", property="team", jdbcType=JdbcType.VARCHAR),
        @Result(column="event_date", property="event_date", jdbcType=JdbcType.VARCHAR),
        @Result(column="event_time", property="event_time", jdbcType=JdbcType.VARCHAR),
        @Result(column="venue_address", property="venue_address", jdbcType=JdbcType.VARCHAR),
        @Result(column="section", property="section", jdbcType=JdbcType.VARCHAR),
        @Result(column="price", property="price", jdbcType=JdbcType.VARCHAR),
        @Result(column="service_fee", property="service_fee", jdbcType=JdbcType.VARCHAR),
        @Result(column="currency", property="currency", jdbcType=JdbcType.VARCHAR)
    })
    Event selectByPrimaryKey(Long id);

    @UpdateProvider(type=EventSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Event record);

    @Update({
        "update event",
        "set tournament = #{tournament,jdbcType=VARCHAR},",
          "vname = #{vname,jdbcType=VARCHAR},",
          "team = #{team,jdbcType=VARCHAR},",
          "event_date = #{event_date,jdbcType=VARCHAR},",
          "event_time = #{event_time,jdbcType=VARCHAR},",
          "venue_address = #{venue_address,jdbcType=VARCHAR},",
          "section = #{section,jdbcType=VARCHAR},",
          "price = #{price,jdbcType=VARCHAR},",
          "service_fee = #{service_fee,jdbcType=VARCHAR},",
          "currency = #{currency,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Event record);
}