package uz.agrobank.avtopog.mapper;

import org.springframework.jdbc.core.RowMapper;
import uz.agrobank.avtopog.model.LdSvGateAdd;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LdSvGateAddRowMapper implements RowMapper<LdSvGateAdd> {
    @Override
    public LdSvGateAdd mapRow(ResultSet rs, int rowNum) throws SQLException {
        LdSvGateAdd ldSvGateAdd=new LdSvGateAdd();
        ldSvGateAdd.setId(rs.getLong("ID"));
        ldSvGateAdd.setBranch(rs.getString("BRANCH"));
        ldSvGateAdd.setCardNumber(rs.getString("CARD_NUMBER"));
        ldSvGateAdd.setExpiryDate(rs.getString("EXPIRY_DATE"));
        ldSvGateAdd.setName(rs.getString("NAME"));
        ldSvGateAdd.setPhone(rs.getString("PHONE"));
        ldSvGateAdd.setRg_user(rs.getLong("RG_USER"));
        ldSvGateAdd.setSignCard(rs.getInt("SIGN_CARD"));
        ldSvGateAdd.setSignClient(rs.getInt("SIGN_CLIENT"));
        ldSvGateAdd.setSms(rs.getString("SMS"));
        ldSvGateAdd.setSmsRecv(rs.getString("SMS_RECV"));
        ldSvGateAdd.setState(rs.getInt("STATE"));
        ldSvGateAdd.setStatus(rs.getInt("STATUS"));
        return ldSvGateAdd;
    }
}
