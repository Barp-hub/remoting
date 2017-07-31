package io.github.riwcwt.mapper;

import io.github.riwcwt.entity.Event;
import org.apache.ibatis.jdbc.SQL;

public class EventSqlProvider {

    public String insertSelective(Event record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("event");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getTournament() != null) {
            sql.VALUES("tournament", "#{tournament,jdbcType=VARCHAR}");
        }
        
        if (record.getVname() != null) {
            sql.VALUES("vname", "#{vname,jdbcType=VARCHAR}");
        }
        
        if (record.getTeam() != null) {
            sql.VALUES("team", "#{team,jdbcType=VARCHAR}");
        }
        
        if (record.getEvent_date() != null) {
            sql.VALUES("event_date", "#{event_date,jdbcType=VARCHAR}");
        }
        
        if (record.getEvent_time() != null) {
            sql.VALUES("event_time", "#{event_time,jdbcType=VARCHAR}");
        }
        
        if (record.getVenue_address() != null) {
            sql.VALUES("venue_address", "#{venue_address,jdbcType=VARCHAR}");
        }
        
        if (record.getSection() != null) {
            sql.VALUES("section", "#{section,jdbcType=VARCHAR}");
        }
        
        if (record.getPrice() != null) {
            sql.VALUES("price", "#{price,jdbcType=VARCHAR}");
        }
        
        if (record.getService_fee() != null) {
            sql.VALUES("service_fee", "#{service_fee,jdbcType=VARCHAR}");
        }
        
        if (record.getCurrency() != null) {
            sql.VALUES("currency", "#{currency,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Event record) {
        SQL sql = new SQL();
        sql.UPDATE("event");
        
        if (record.getTournament() != null) {
            sql.SET("tournament = #{tournament,jdbcType=VARCHAR}");
        }
        
        if (record.getVname() != null) {
            sql.SET("vname = #{vname,jdbcType=VARCHAR}");
        }
        
        if (record.getTeam() != null) {
            sql.SET("team = #{team,jdbcType=VARCHAR}");
        }
        
        if (record.getEvent_date() != null) {
            sql.SET("event_date = #{event_date,jdbcType=VARCHAR}");
        }
        
        if (record.getEvent_time() != null) {
            sql.SET("event_time = #{event_time,jdbcType=VARCHAR}");
        }
        
        if (record.getVenue_address() != null) {
            sql.SET("venue_address = #{venue_address,jdbcType=VARCHAR}");
        }
        
        if (record.getSection() != null) {
            sql.SET("section = #{section,jdbcType=VARCHAR}");
        }
        
        if (record.getPrice() != null) {
            sql.SET("price = #{price,jdbcType=VARCHAR}");
        }
        
        if (record.getService_fee() != null) {
            sql.SET("service_fee = #{service_fee,jdbcType=VARCHAR}");
        }
        
        if (record.getCurrency() != null) {
            sql.SET("currency = #{currency,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("id = #{id,jdbcType=BIGINT}");
        
        return sql.toString();
    }
}