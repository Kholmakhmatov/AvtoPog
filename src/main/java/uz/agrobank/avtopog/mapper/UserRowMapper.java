package uz.agrobank.avtopog.mapper;

import org.springframework.jdbc.core.RowMapper;
import uz.agrobank.avtopog.model.User;
import uz.agrobank.avtopog.model.enums.RoleEnum;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("ID"));
            user.setUsername(rs.getString("USERNAME"));
            user.setPhone(rs.getString("PHONE"));
            user.setFirstName(rs.getString("FIRSTNAME"));
            user.setPassword(rs.getString("PASSWORD"));
            user.setRole(RoleEnum.valueOf(rs.getString("ROLE")));
            user.setActive(rs.getBoolean("ACTIVE"));
            user.setCreatedAt(rs.getTimestamp("CREATEDAT").toLocalDateTime());
            return user;
    }
}
