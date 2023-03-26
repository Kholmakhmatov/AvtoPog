package uz.agrobank.avtopog.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.agrobank.avtopog.repository.imp.LdSvGateAddRepositoryImp;
import uz.agrobank.avtopog.model.LdSvGateAdd;
@Repository
@RequiredArgsConstructor
public class LdSvGateAddRepository implements LdSvGateAddRepositoryImp {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public int save(LdSvGateAdd ldSvGateAdd) {
        String sql="INSERT INTO LD_SV_GATE_ADD (ID, BRANCH, CARD_NUMBER, EXPIRY_DATE, NAME, PHONE, RG_USER, SIGN_CARD, SIGN_CLIENT, SMS, SMS_RECV, STATE, STATUS) " +
                " values (:id,:branch,:cardNumber,:expiryDate,:name,:phone,:rgUser,:signCard,:signClient,:sms,:smsRecv,:state,:status)";
        MapSqlParameterSource params=new MapSqlParameterSource();
        params.addValue("id",ldSvGateAdd.getId());
        params.addValue("branch",ldSvGateAdd.getBranch());
        params.addValue("cardNumber",ldSvGateAdd.getCardNumber());
        params.addValue("expiryDate",ldSvGateAdd.getExpiryDate());
        params.addValue("name",ldSvGateAdd.getName());
        params.addValue("phone",ldSvGateAdd.getPhone());
        params.addValue("rgUser",ldSvGateAdd.getRg_user());
        params.addValue("signCard",ldSvGateAdd.getSignCard());
        params.addValue("signClient",ldSvGateAdd.getSignClient());
        params.addValue("sms",ldSvGateAdd.getSms());
        params.addValue("smsRecv",ldSvGateAdd.getSmsRecv());
        params.addValue("smsRecv",ldSvGateAdd.getSmsRecv());
        params.addValue("state",ldSvGateAdd.getState());
        params.addValue("status",ldSvGateAdd.getStatus());
        return namedParameterJdbcTemplate.update(sql,params);
    }
    @Override
    public int delete(LdSvGateAdd ldSvGateAdd) {
        String sql="DELETE from LD_SV_GATE_ADD where ID=:id and BRANCH=:branch and CARD_NUMBER=:cardNumber and EXPIRY_DATE=:expiryDate";
        MapSqlParameterSource params=new MapSqlParameterSource();
        params.addValue("id",ldSvGateAdd.getId());
        params.addValue("branch",ldSvGateAdd.getBranch());
        params.addValue("cardNumber",ldSvGateAdd.getCardNumber());
        params.addValue("expiryDate",ldSvGateAdd.getExpiryDate());
        return namedParameterJdbcTemplate.update(sql,params);
    }
}
