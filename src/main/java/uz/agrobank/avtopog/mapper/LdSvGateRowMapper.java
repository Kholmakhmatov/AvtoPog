package uz.agrobank.avtopog.mapper;

import org.springframework.jdbc.core.RowMapper;
import uz.agrobank.avtopog.model.LdSvGate;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LdSvGateRowMapper implements RowMapper<LdSvGate> {
    @Override
    public LdSvGate mapRow(ResultSet rs, int rowNum) throws SQLException {
        LdSvGate ldSvGate=new LdSvGate();
        ldSvGate.setId(rs.getLong("ID"));
        ldSvGate.setBranch(rs.getString("BRANCH"));
        ldSvGate.setCardNumber(rs.getString("CARD_NUMBER"));
        ldSvGate.setExpiryDate(rs.getString("EXPIRY_DATE"));
        ldSvGate.setName(rs.getString("NAME"));
        ldSvGate.setPhone(rs.getString("PHONE"));
        ldSvGate.setSignCard(rs.getInt("SIGN_CARD"));
        ldSvGate.setSignClient(rs.getInt("SIGN_CLIENT"));
        ldSvGate.setSms(rs.getString("SMS"));
        ldSvGate.setState(rs.getInt("STATE"));
        return ldSvGate;
    }
}
