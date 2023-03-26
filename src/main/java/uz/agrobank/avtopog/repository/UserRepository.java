package uz.agrobank.avtopog.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.agrobank.avtopog.repository.imp.UserRepositoryImp;
import uz.agrobank.avtopog.mapper.UserRowMapper;
import uz.agrobank.avtopog.model.User;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class UserRepository implements UserRepositoryImp {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate  namedParameterJdbcTemplate;
    @Override
    public User findByUserName(String userName) {
        try {
            String sql="select * from USERS where USERNAME = ? ";
            return  jdbcTemplate.queryForObject(sql, new Object[]{userName}, new UserRowMapper());
        }catch (Exception e){
            return  null;
        }


    }

    @Override
    public User findUserByUsernameAndActive(String userName, Boolean active) {
        try {

            String sql="select * from USERS where USERNAME=? and active=?";
            return  jdbcTemplate.queryForObject(sql, new Object[]{userName,active}, new UserRowMapper());
        }catch (Exception e){
            return  null;
        }
    }

    @Override
    public User findById(Long id) {
        try {

            String sql="select * from USERS where ID=?";
            return  jdbcTemplate.queryForObject(sql, new Object[]{id}, new UserRowMapper());
        }catch (Exception e){
            return  null;
        }
    }

    @Override
    public User findByUsernameAndIdNot(String username, Long id) {
        try {

            String sql="select * from users where id!=? and username=?";
            return  jdbcTemplate.queryForObject(sql, new Object[]{id,username}, new UserRowMapper());
        }catch (Exception e){
            return  null;
        }
    }

    @Override
    public User findByPhoneAndIdNot(String username, Long id) {

        try {
            String sql = "select * from users where id!=? and phone=?";
            return  jdbcTemplate.queryForObject(sql, new Object[]{id,username}, new UserRowMapper());
        }catch (Exception e){
            return  null;
        }
    }

    @Override
    public int update(User user) {
        String sql="UPDATE USERS set ACTIVE=?,FIRSTNAME=?,PASSWORD=?, PHONE=?,ROLE=?,USERNAME=? where ID=?";
        return jdbcTemplate.update(sql, user.getActive(),user.getFirstName(),user.getPassword(),user.getPhone(),user.getRole().name(),user.getUsername(),user.getId());
    }

    @Override
    public Long findMaxId() {
        String sql="select max(id) from USERS";
        return jdbcTemplate.queryForObject(sql,Long.class);
    }

    @Override
    public List<User> findAllByUsernameAndOffset(String username, Integer size, Integer offset) {
        try {
            String sql = "SELECT * \n" +
                    "FROM USERS\n" +
                    "WHERE\n" +
                    "    (:userName IS NOT NULL AND UPPER(USERNAME) LIKE upper('%'|| :userName ||'%')) or\n" +
                    "    (:userName is null and USERNAME is not null )\n" +
                    "ORDER BY ID\n" +
                    "OFFSET :offset ROWS FETCH NEXT :size ROWS ONLY";
            MapSqlParameterSource params=new MapSqlParameterSource();
            params.addValue("userName",username);
            params.addValue("offset",offset);
            params.addValue("size",size);
            return namedParameterJdbcTemplate.query(sql, params, new UserRowMapper());
        }catch (Exception e){
            return  null;
        }
    }

    @Override
    public Long findAllByUsernameCount(String username) {
        String sql ="SELECT count(ID) " +
                "FROM USERS " +
                "WHERE" +
                "    (:username IS NOT NULL AND UPPER(USERNAME) LIKE upper('%'|| :username ||'%')) or " +
                "    (:username is null and USERNAME is not null ) ";
        MapSqlParameterSource params=new MapSqlParameterSource();
        params.addValue("username",username);
        return namedParameterJdbcTemplate.queryForObject(sql,params,Long.class);
    }

    @Override
    public Integer findAnotherAdmin(Long id) {
        String sql ="select case when exists(SELECT id FROM users WHERE id != :id AND role = 'ADMIN' AND active = 1)\n" +
                "    then 1 else 0 end as result from DUAL";
        MapSqlParameterSource parms=new MapSqlParameterSource();
        parms.addValue("id",id);
        return namedParameterJdbcTemplate.queryForObject(sql,parms,Integer.class);
    }

    @Override
    public int save(User admin) {
        String sql ="INSERT INTO USERS (ID, ACTIVE, CREATEDAT, FIRSTNAME, PASSWORD, PHONE, ROLE, USERNAME) values (:id,:active,:createdAt,:firstName,:password,:phone,:role,:username)";
        MapSqlParameterSource parms=new MapSqlParameterSource();
        parms.addValue("id",admin.getId());
        parms.addValue("active",admin.getActive());
        parms.addValue("createdAt",admin.getCreatedAt());
        parms.addValue("firstName",admin.getFirstName());
        parms.addValue("password",admin.getPassword());
        parms.addValue("phone",admin.getPhone());
        parms.addValue("role",admin.getRole().name());
        parms.addValue("username",admin.getUsername());

        return namedParameterJdbcTemplate.update(sql,parms);
    }
}
