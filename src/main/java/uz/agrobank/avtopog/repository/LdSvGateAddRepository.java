package uz.agrobank.avtopog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.agrobank.avtopog.model.LdSvGateAdd;

import java.util.List;

public interface LdSvGateAddRepository extends JpaRepository<LdSvGateAdd,Long> {
    @Query(nativeQuery = true,value = "select * from ld_sv_gate_add where state=1 and \n" +
            "    case when (?1 is not null and ?2 is null  and ?3 is null ) then  id=?1 \n" +
            "         when (?1 is not null and ?2 is not null  and ?3 is null ) then (id=?1 and branch=?2) \n" +
            "         when (?1 is not null and ?2 is not null  and ?3 is not null )" +
            "  then (id=?1 and branch=?2 and card_number=?3)\n" +
            "         when (?1 is  null and ?2 is not null  and ?3 is  null )  then ( branch=?2)\n" +
            "         when (?1 is  null and ?2 is not null  and ?3 is not null )  then ( branch=?2 and card_number=?3)\n" +
            "         when (?1 is not null and ?2 is not null  and ?3 is  null )  then ( branch=?2 and id=?1)\n" +
            "        " +
            "when (?1 is  null and ?2 is  null  and ?3 is not null )  then ( card_number=?3)\n" +
            "         when (?1 is not null and ?2 is  null  and ?3 is not null )  then (id=?1  and card_number=?3)\n" +
            "         end  order by id limit ?4 offset ?5")
    List<LdSvGateAdd> findAllActive(Long id, String branch, String cardNumber, Integer size, Integer offset);
    @Query(nativeQuery = true,value = "select count(id) from ld_sv_gate_add where state=1 and \n" +
            "    case when (?1 is not null and ?2 is null  and ?3 is null ) then  id=?1 \n" +
            "         when (?1 is not null and ?2 is not null  and ?3 is null ) then (id=?1 and branch=?2) \n" +
            "         when (?1 is not null and ?2 is not null  and ?3 is not null )" +
            "  then (id=?1 and branch=?2 and card_number=?3)\n" +
            "         when (?1 is  null and ?2 is not null  and ?3 is  null )  then ( branch=?2)\n" +
            "         when (?1 is  null and ?2 is not null  and ?3 is not null )  then ( branch=?2 and card_number=?3)\n" +
            "         when (?1 is not null and ?2 is not null  and ?3 is  null )  then ( branch=?2 and id=?1)\n" +
            "        " +
            "when (?1 is  null and ?2 is  null  and ?3 is not null )  then ( card_number=?3)\n" +
            "         when (?1 is not null and ?2 is  null  and ?3 is not null )  then (id=?1  and card_number=?3)\n" +
            "         end  ")
    Integer findAllActiveCount(Long id, String branch, String cardNumber);
}
