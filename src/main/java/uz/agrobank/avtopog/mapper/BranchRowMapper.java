package uz.agrobank.avtopog.mapper;

import org.springframework.jdbc.core.RowMapper;
import uz.agrobank.avtopog.model.Branch;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BranchRowMapper implements RowMapper<Branch> {
    @Override
    public Branch mapRow(ResultSet rs, int rowNum) throws SQLException {
        Branch branch=new Branch();
        branch.setId(rs.getString("ID"));
        branch.setRegId(rs.getString("REG_ID"));
        branch.setRegName(rs.getString("REG_NAME"));
        return branch;
    }
}
