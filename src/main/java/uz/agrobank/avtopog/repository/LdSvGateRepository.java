package uz.agrobank.avtopog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.agrobank.avtopog.model.LdSvGate;

import java.util.List;

public interface LdSvGateRepository extends JpaRepository<LdSvGate, Long> {
    @Query(nativeQuery = true,value = "SELECT id,\n" +
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
            "WHERE (?1 IS NOT NULL AND ?2 IS NULL AND ?3 IS NULL and id = ?1)\n" +
            "   or (?1 IS NOT NULL AND ?2 IS NOT NULL AND ?3 IS NULL and id = ?1 AND branch = ?2)\n" +
            "   or (?1 IS NOT NULL AND ?2 IS NOT NULL AND ?3 IS NOT NULL and id = ?1 AND branch = ?2 AND card_number = ?3)\n" +
            "   or (?1 IS NULL AND ?2 IS NOT NULL AND ?3 IS NULL and branch = ?2)\n" +
            "   or (?1 IS NULL AND ?2 IS NOT NULL AND ?3 IS NOT NULL and branch = ?2 AND card_number = ?3)\n" +
            "   or (?1 IS NOT NULL AND ?2 IS NULL AND ?3 IS NOT NULL and id = ?1 AND card_number = ?3)\n" +
            "   or (?1 IS NULL AND ?2 IS NULL AND ?3 IS NOT NULL and card_number = ?3)\n" +
            "UNION\n" +
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
            "WHERE (?4 IS NOT NULL AND ?5 IS NULL AND ?6 IS NULL and id = ?4)\n" +
            "   or (?4 IS NOT NULL AND ?5 IS NOT NULL AND ?6 IS NULL and id = ?4 AND branch = ?5)\n" +
            "   or (?4 IS NOT NULL AND ?5 IS NOT NULL AND ?6 IS NOT NULL and id = ?4 AND branch = ?5 AND card_number = ?6)\n" +
            "   or (?4 IS NULL AND ?5 IS NOT NULL AND ?6 IS NULL and branch = ?5)\n" +
            "   or (?4 IS NULL AND ?5 IS NOT NULL AND ?6 IS NOT NULL and branch = ?5 AND card_number = ?6)\n" +
            "   or (?4 IS NOT NULL AND ?5 IS NULL AND ?6 IS NOT NULL and id = ?4 AND card_number = ?6)\n" +
            "   or (?4 IS NULL AND ?5 IS NULL AND ?6 IS NOT NULL and card_number = ?6)\n" +
            "ORDER BY id\n" +
            "OFFSET ?8 ROWS FETCH NEXT ?7 ROWS ONLY")
    List<LdSvGate> findAllActiveUnion(Long id, String branch, String cardNumber, Long id2, String branch2, String cardNumber2, Integer size, Integer offset);

    @Query(nativeQuery = true, value = "select count(ID)  from (SELECT la.ID\n" +
            "               FROM ld_sv_gate_add la\n" +
            "               WHERE (?1 IS NOT NULL AND ?2 IS NULL AND ?3 IS NULL and id = ?1)\n" +
            "                  or (?1 IS NOT NULL AND ?2 IS NOT NULL AND ?3 IS NULL and id = ?1 AND branch = ?2)\n" +
            "                  or (?1 IS NOT NULL AND ?2 IS NOT NULL AND ?3 IS NOT NULL and id = ?1 AND branch = ?2 AND card_number = ?3)\n" +
            "                  or (?1 IS NULL AND ?2 IS NOT NULL AND ?3 IS NULL and branch = ?2)\n" +
            "                  or (?1 IS NULL AND ?2 IS NOT NULL AND ?3 IS NOT NULL and branch = ?2 AND card_number = ?3)\n" +
            "                  or (?1 IS NOT NULL AND ?2 IS NULL AND ?3 IS NOT NULL and id = ?1 AND card_number = ?3)\n" +
            "                  or (?1 IS NULL AND ?2 IS NULL AND ?3 IS NOT NULL and card_number = ?3)\n" +
            "               UNION\n" +
            "               SELECT lg.ID\n" +
            "               FROM ld_sv_gate lg\n" +
            "               WHERE (?4 IS NOT NULL AND ?5 IS NULL AND ?6 IS NULL and id = ?4)\n" +
            "                  or (?4 IS NOT NULL AND ?5 IS NOT NULL AND ?6 IS NULL and id = ?4 AND branch = ?5)\n" +
            "                  or (?4 IS NOT NULL AND ?5 IS NOT NULL AND ?6 IS NOT NULL and id = ?4 AND branch = ?5 AND card_number = ?6)\n" +
            "                  or (?4 IS NULL AND ?5 IS NOT NULL AND ?6 IS NULL and branch = ?5)\n" +
            "                  or (?4 IS NULL AND ?5 IS NOT NULL AND ?6 IS NOT NULL and branch = ?5 AND card_number = ?6)\n" +
            "                  or (?4 IS NOT NULL AND ?5 IS NULL AND ?6 IS NOT NULL and id = ?4 AND card_number = ?6)\n" +
            "                  or (?4 IS NULL AND ?5 IS NULL AND ?6 IS NOT NULL and card_number = ?6))")
    Integer findAllActiveCountUnion(Long id, String branch, String cardNumber, Long id2, String branch2, String cardNumber2);
}
