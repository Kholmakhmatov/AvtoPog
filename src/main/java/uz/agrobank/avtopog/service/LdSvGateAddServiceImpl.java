package uz.agrobank.avtopog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import uz.agrobank.avtopog.dto.LdSvGateDto;
import uz.agrobank.avtopog.model.LdSvGate;
import uz.agrobank.avtopog.model.LdSvGateAdd;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LdSvGateAddServiceImpl implements LdSvGateAddService {
    private final JdbcTemplate jdbcTemplate;


    @Override
    public List<Map<String, Object>> getByIdAndBranch(Long id, String branch) {
        return jdbcTemplate.queryForList("select * from ld_sv_gate where id = ? and branch = ?", id, branch);
    }

    ;

    @Override
    public LdSvGate getByBranchAndCardNumber(LdSvGateDto ldSvGateDto) {
        return null;
    }

    @Override
    public LdSvGateAdd getByIdAndBranchAdd(LdSvGateDto ldSvGateDto) {
        return null;
    }

    @Override
    public LdSvGateAdd getByBranchAndCardNumberAdd(LdSvGateDto ldSvGateDto) {
        return null;
    }

    @Override
    public LdSvGateAdd addNewCard(LdSvGateDto ldSvGateDto) {
        return null;
    }

    @Override
    public String deleteFromLdSvGate(LdSvGateDto ldSvGateDto) {
        return null;
    }

    @Override
    public String deleteFromLdSvGateAdd(LdSvGateDto ldSvGateDto) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getByAll() {
        return jdbcTemplate.queryForList("select * from ld_sv_gate");
    }

}
