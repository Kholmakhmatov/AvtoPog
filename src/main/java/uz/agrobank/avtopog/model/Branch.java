package uz.agrobank.avtopog.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@NoArgsConstructor
@Entity
@Table(name = "BRANCH")
public class Branch {
    @Id
    private Long id;
    private String branch;

}
