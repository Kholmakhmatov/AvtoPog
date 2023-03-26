package uz.agrobank.avtopog.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.agrobank.avtopog.repository.imp.BranchRepositoryImp;
import uz.agrobank.avtopog.mapper.BranchRowMapper;
import uz.agrobank.avtopog.model.Branch;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BranchRepository implements BranchRepositoryImp {
private final JdbcTemplate jdbcTemplate;
private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public List<Branch> findBranchList() {
        try {
            String sql="select * from BRANCH";
            return jdbcTemplate.query(sql, new BranchRowMapper());
        }catch (Exception e){

            return null;
        }
    }

    @Override
    public Branch findById(String branch) {
        try {
            String sql="select * from BRANCH where ID=?";
            return jdbcTemplate.queryForObject(sql,new Object[]{branch} ,new BranchRowMapper());
        }catch (Exception e){
            return null;
        }
    }

}
