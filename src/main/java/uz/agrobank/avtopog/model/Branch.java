package uz.agrobank.avtopog.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "BRANCH")
public class Branch {
    @Id
    private String id;
    @Column(name = "REG_NAME")
    private String regName;
    @Column(name = "REG_ID")
    private String regId;

}
