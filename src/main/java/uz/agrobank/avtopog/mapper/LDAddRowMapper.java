package uz.agrobank.avtopog.mapper;

import org.springframework.jdbc.core.RowMapper;
import uz.agrobank.avtopog.model.LdSvGateAdd;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Kholmakhmatov_A on 2/20/2023
 *
 * @Author : Kholmakhmatov_A
 * @Date : 2/20/2023
 * @Project : AvtoPog
 **/
public class LDAddRowMapper implements RowMapper<LdSvGateAdd> {
    @Override
    public LdSvGateAdd mapRow(ResultSet rs, int rowNum) throws SQLException {
        LdSvGateAdd ld_SVGATE_add = new LdSvGateAdd();
        ld_SVGATE_add.setId(rs.getLong("ID"));
        ld_SVGATE_add.setBranch(rs.getString("BRANCH"));
        ld_SVGATE_add.setCardNumber(rs.getString("CARD_NUMBER"));
        return ld_SVGATE_add;
    }
}
