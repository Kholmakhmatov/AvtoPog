package uz.agrobank.avtopog.mapper;

import org.springframework.jdbc.core.RowMapper;
import uz.agrobank.avtopog.model.LdSvGate;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Kholmakhmatov_A on 2/20/2023
 *
 * @Author : Kholmakhmatov_A
 * @Date : 2/20/2023
 * @Project : AvtoPog
 **/
public class LDRowMapper implements RowMapper<LdSvGate> {
    @Override
    public LdSvGate mapRow(ResultSet rs, int rowNum) throws SQLException {
        LdSvGate ldSVGATE = new LdSvGate();
        ldSVGATE.setId(rs.getLong("ID"));
        ldSVGATE.setBranch(rs.getString("BRANCH"));
        ldSVGATE.setCardNumber(rs.getString("CARD_NUMBER"));
        return ldSVGATE;
    }
}
