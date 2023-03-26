package uz.agrobank.avtopog.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.agrobank.avtopog.exceptions.UniversalException;
import uz.agrobank.avtopog.repository.imp.LdSvGateRepositoryImp;
import uz.agrobank.avtopog.mapper.LdSvGateRowMapper;
import uz.agrobank.avtopog.model.LdSvGate;
import uz.agrobank.avtopog.model.LdSvGateAdd;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LdSvGateRepository implements LdSvGateRepositoryImp {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<LdSvGate> findAllActiveUnion(Long id, String branch, String cardNumber, Integer size, Integer offset) {
        try {
            String sql="SELECT id,\n" +
                    "       branch,\n" +
                    "       card_number,\n" +
                    "       expiry_date,\n" +
                    "       name,\n" +
                    "       phone,\n" +
                    "       sign_card,\n" +
                    "       sign_client,\n" +
                    "       sms,\n" +
                    "       state\n" +
                    "FROM ld_sv_gate_add la\n" +
                    "WHERE (:id IS NOT NULL AND :branch IS NULL AND :cardNumber IS NULL and id = :id)\n" +
                    "   or (:id IS NOT NULL AND :branch IS NOT NULL AND :cardNumber IS NULL and id = :id AND branch = :branch)\n" +
                    "   or (:id IS NOT NULL AND :branch IS NOT NULL AND :cardNumber IS NOT NULL and id = :id AND branch = :branch AND card_number = :cardNumber)\n" +
                    "   or (:id IS NULL AND :branch IS NOT NULL AND :cardNumber IS NULL and branch = :branch)\n" +
                    "   or (:id IS NULL AND :branch IS NOT NULL AND :cardNumber IS NOT NULL and branch = :branch AND card_number = :cardNumber)\n" +
                    "   or (:id IS NOT NULL AND :branch IS NULL AND :cardNumber IS NOT NULL and id = :id AND card_number = :cardNumber)\n" +
                    "   or (:id IS NULL AND :branch IS NULL AND :cardNumber IS NOT NULL and card_number = :cardNumber)\n" +
                    "UNION ALL \n" +
                    "SELECT id,\n" +
                    "       branch,\n" +
                    "       card_number,\n" +
                    "       expiry_date,\n" +
                    "       name,\n" +
                    "       phone,\n" +
                    "       sign_card,\n" +
                    "       sign_client,\n" +
                    "       sms,\n" +
                    "       state\n" +
                    "FROM ld_sv_gate lg\n" +
                    "WHERE (:id2 IS NOT NULL AND :branch2 IS NULL AND :cardNumber2 IS NULL and id = :id2)\n" +
                    "   or (:id2 IS NOT NULL AND :branch2 IS NOT NULL AND :cardNumber2 IS NULL and id = :id2 AND branch = :branch2)\n" +
                    "   or (:id2 IS NOT NULL AND :branch2 IS NOT NULL AND :cardNumber2 IS NOT NULL and id = :id2 AND branch = :branch2 AND card_number = :cardNumber2)\n" +
                    "   or (:id2 IS NULL AND :branch2 IS NOT NULL AND :cardNumber2 IS NULL and branch = :branch2)\n" +
                    "   or (:id2 IS NULL AND :branch2 IS NOT NULL AND :cardNumber2 IS NOT NULL and branch = :branch2 AND card_number = :cardNumber2)\n" +
                    "   or (:id2 IS NOT NULL AND :branch2 IS NULL AND :cardNumber2 IS NOT NULL and id = :id2 AND card_number = :cardNumber2)\n" +
                    "   or (:id2 IS NULL AND :branch2 IS NULL AND :cardNumber2 IS NOT NULL and card_number = :cardNumber2)\n" +
                    "ORDER BY id\n" +
                    "OFFSET :offset ROWS FETCH NEXT :size ROWS ONLY";

            MapSqlParameterSource params=new MapSqlParameterSource();
            params.addValue("id",id);
            params.addValue("id2",id);
            params.addValue("branch",branch);
            params.addValue("branch2",branch);
            params.addValue("cardNumber",cardNumber);
            params.addValue("cardNumber2",cardNumber);
            params.addValue("offset",offset);
            params.addValue("size",size);
            return namedParameterJdbcTemplate.query(sql,params, new LdSvGateRowMapper());
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    @Override
    public Integer findAllActiveCountUnion(Long id, String branch, String cardNumber) {
        try {
            String sql="select count(ID)  from (SELECT la.ID \n" +
                    "               FROM ld_sv_gate_add la\n" +
                    "               WHERE (:id IS NOT NULL AND :branch IS NULL AND :cardNumber IS NULL and id = :id)\n" +
                    "                  or (:id IS NOT NULL AND :branch IS NOT NULL AND :cardNumber IS NULL and id = :id AND branch = :branch)\n" +
                    "                  or (:id IS NOT NULL AND :branch IS NOT NULL AND :cardNumber IS NOT NULL and id = :id AND branch = :branch AND card_number = :cardNumber)\n" +
                    "                  or (:id IS NULL AND :branch IS NOT NULL AND :cardNumber IS NULL and branch = :branch)\n" +
                    "                  or (:id IS NULL AND :branch IS NOT NULL AND :cardNumber IS NOT NULL and branch = :branch AND card_number = :cardNumber)\n" +
                    "                  or (:id IS NOT NULL AND :branch IS NULL AND :cardNumber IS NOT NULL and id = :id AND card_number = :cardNumber)\n" +
                    "                  or (:id IS NULL AND :branch IS NULL AND :cardNumber IS NOT NULL and card_number = :cardNumber)\n" +
                    "               UNION ALL \n" +
                    "               SELECT lg.ID\n" +
                    "               FROM ld_sv_gate lg\n" +
                    "               WHERE (:id2 IS NOT NULL AND :branch2 IS NULL AND :cardNumber2 IS NULL and id = :id2)\n" +
                    "                  or (:id2 IS NOT NULL AND :branch2 IS NOT NULL AND :cardNumber2 IS NULL and id = :id2 AND branch = :branch2)\n" +
                    "                  or (:id2 IS NOT NULL AND :branch2 IS NOT NULL AND :cardNumber2 IS NOT NULL and id = :id2 AND branch = :branch2 AND card_number = :cardNumber2)\n" +
                    "                  or (:id2 IS NULL AND :branch2 IS NOT NULL AND :cardNumber2 IS NULL and branch = :branch2)\n" +
                    "                  or (:id2 IS NULL AND :branch2 IS NOT NULL AND :cardNumber2 IS NOT NULL and branch = :branch2 AND card_number = :cardNumber2)\n" +
                    "                  or (:id2 IS NOT NULL AND :branch2 IS NULL AND :cardNumber2 IS NOT NULL and id = :id2 AND card_number = :cardNumber2 )\n" +
                    "                  or (:id2 IS NULL AND :branch2 IS NULL AND :cardNumber2 IS NOT NULL and card_number = :cardNumber2))";
            MapSqlParameterSource params=new MapSqlParameterSource();
            params.addValue("id",id);
            params.addValue("id2",id);
            params.addValue("branch",branch);
            params.addValue("branch2",branch);
            params.addValue("cardNumber",cardNumber);
            params.addValue("cardNumber2",cardNumber);
            return namedParameterJdbcTemplate.queryForObject(sql,params,Integer.class);
        }catch (Exception e){
           throw  new UniversalException("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public int delete(LdSvGateAdd ldSvGateAdd) {
        String sql="DELETE from LD_SV_GATE where ID=:id and BRANCH=:branch and CARD_NUMBER=:cardNumber and EXPIRY_DATE=:expiryDate";
        MapSqlParameterSource params=new MapSqlParameterSource();
        params.addValue("id",ldSvGateAdd.getId());
        params.addValue("branch",ldSvGateAdd.getBranch());
        params.addValue("cardNumber",ldSvGateAdd.getCardNumber());
        params.addValue("expiryDate",ldSvGateAdd.getExpiryDate());
        return namedParameterJdbcTemplate.update(sql,params);
    }

}
