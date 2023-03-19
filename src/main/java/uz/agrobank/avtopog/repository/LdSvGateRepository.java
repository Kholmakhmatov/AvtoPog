package uz.agrobank.avtopog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.agrobank.avtopog.model.LdSvGate;

import java.util.List;

public interface LdSvGateRepository extends JpaRepository<LdSvGate, Long> {
    List<LdSvGate> findAllByIdAndBranch(Long id, String branch);

    @Query(nativeQuery = true, value = "select id, branch, card_number, expiry_date, name, phone,  sign_card, sign_client, sms, state from ld_sv_gate_add la \n" +
            "where  \n" +
            "    case when (?1 is not null and ?2 is null  and ?3 is null ) then  id=?1 \n" +
            "         when (?1 is not null and ?2 is not null  and ?3 is null ) then (id=?1 and branch=?2) \n" +
            "         when (?1 is not null and ?2 is not null  and ?3 is not null )  then (id=?1 and branch=?2 and card_number=?3)\n" +
            "         when (?1 is  null and ?2 is not null  and ?3 is  null )  then ( branch=?2)\n" +
            "         when (?1 is  null and ?2 is not null  and ?3 is not null )  then ( branch=?2 and card_number=?3)\n" +
            "         when (?1 is not null and ?2 is not null  and ?3 is  null )  then ( branch=?2 and id=?1)\n" +
            "        when (?1 is  null and ?2 is  null  and ?3 is not null )  then ( card_number=?3)\n" +
            "         when (?1 is not null and ?2 is  null  and ?3 is not null )  then (id=?1  and card_number=?3) " +
            "end                                                                                          " +
            "                                                                                              \n" +
            "union select id, branch, card_number, expiry_date, name, phone, sign_card, sign_client, sms, state from ld_sv_gate lg " +
            "where  \n" +
            "    case when (?4 is not null and ?5 is null  and ?6 is null ) then  id=?4 \n" +
            "         when (?4 is not null and ?5 is not null  and ?6 is null ) then (id=?4 and branch=?5) \n" +
            "         when (?4 is not null and ?5 is not null  and ?6 is not null )" +
            "  then (id=?4 and branch=?5 and card_number=?6)\n" +
            "         when (?4 is  null and ?5 is not null  and ?6 is  null )  then ( branch=?5)\n" +
            "         when (?4 is  null and ?5 is not null  and ?6 is not null )  then ( branch=?5 and card_number=?6)\n" +
            "         when (?4 is not null and ?5 is not null  and ?6 is  null )  then ( branch=?5 and id=?4)\n" +
            "        " +
            "when (?4 is  null and ?5 is  null  and ?6 is not null )  then ( card_number=?6)\n" +
            "         when (?4 is not null and ?5 is  null  and ?6 is not null )  then (id=?4  and card_number=?6)\n" +
            "         end  order by id limit ?7 offset ?8")
    List<LdSvGate> findAllActiveUnion(Long id, String branch, String cardNumber, Long id2, String branch2, String cardNumber2, Integer size, Integer offset);

    @Query(nativeQuery = true, value = "select count(id) from ld_sv_gate_add  \n" +
            "where  \n" +
            "    case when (?1 is not null and ?2 is null  and ?3 is null ) then  id=?1 \n" +
            "         when (?1 is not null and ?2 is not null  and ?3 is null ) then (id=?1 and branch=?2) \n" +
            "         when (?1 is not null and ?2 is not null  and ?3 is not null )  then (id=?1 and branch=?2 and card_number=?3)\n" +
            "         when (?1 is  null and ?2 is not null  and ?3 is  null )  then ( branch=?2)\n" +
            "         when (?1 is  null and ?2 is not null  and ?3 is not null )  then ( branch=?2 and card_number=?3)\n" +
            "         when (?1 is not null and ?2 is not null  and ?3 is  null )  then ( branch=?2 and id=?1)\n" +
            "        when (?1 is  null and ?2 is  null  and ?3 is not null )  then ( card_number=?3)\n" +
            "         when (?1 is not null and ?2 is  null  and ?3 is not null )  then (id=?1  and card_number=?3) " +
            "end                                                                                          " +
            "                                                                                              \n" +
            "union select count(id) from ld_sv_gate  " +
            "where  \n" +
            "    case when (?4 is not null and ?5 is null  and ?6 is null ) then  id=?4 \n" +
            "         when (?4 is not null and ?5 is not null  and ?6 is null ) then (id=?4 and branch=?5) \n" +
            "         when (?4 is not null and ?5 is not null  and ?6 is not null )" +
            "  then (id=?4 and branch=?5 and card_number=?6)\n" +
            "         when (?4 is  null and ?5 is not null  and ?6 is  null )  then ( branch=?5)\n" +
            "         when (?4 is  null and ?5 is not null  and ?6 is not null )  then ( branch=?5 and card_number=?6)\n" +
            "         when (?4 is not null and ?5 is not null  and ?6 is  null )  then ( branch=?5 and id=?4)\n" +
            "        " +
            "when (?4 is  null and ?5 is  null  and ?6 is not null )  then ( card_number=?6)\n" +
            "         when (?4 is not null and ?5 is  null  and ?6 is not null )  then (id=?4  and card_number=?6)\n" +
            "         end  ")
    List<Integer> findAllActiveCountUnion(Long id, String branch, String cardNumber, Long id2, String branch2, String cardNumber2);
}
