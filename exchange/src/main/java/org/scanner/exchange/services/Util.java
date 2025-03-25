package org.scanner.exchange.services;

import com.fasterxml.jackson.databind.JsonNode;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Util {

    public static void sv(PreparedStatement ps, int i, Object value) throws SQLException {
        if (value == null)
            ps.setNull(i, java.sql.Types.NULL);
        if (value instanceof Double) {
            ps.setDouble(i, (Double) value);
        } else if (value instanceof Integer) {
            ps.setInt(i, (Integer) value);
//        } else if (value instanceof OffsetDateTime) {
//            var date = new Data(((OffsetDateTime) value).toInstant().toEpochMilli());
//            ps.setDate(i, date);
        } else if (value instanceof String) {
            ps.setString(i, (String) value);
        } else if (value instanceof Long) {
            ps.setLong(i, (Long) value);
        } else if (value instanceof LocalDate) {
            ps.setDate(i, Date.valueOf((LocalDate) value));
        } else if (value instanceof LocalDateTime) {
            ps.setTimestamp(i, Timestamp.valueOf((LocalDateTime) value));
        } else if (value instanceof Boolean) {
            ps.setInt(i, Boolean.TRUE.equals(value) ? 1 : 0);
        } else if (value instanceof Instant) {
            ps.setDate(i, new Date(((Instant) value).toEpochMilli()));
        }else if (value instanceof Date) {
            ps.setDate(i, (Date) value);
        }
    }

    public static JsonNode document(JsonNode message) {
        var result = message.get("result").get("list");
//        var rsl = Optional.ofNullable(result).orElse(result.get("category").get("list"));
        return result;
    }
}
