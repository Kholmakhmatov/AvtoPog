package uz.agrobank.avtopog.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "BRANCH")
public class Branch {
    @Id
    private Long id;
    private String branch;

}
