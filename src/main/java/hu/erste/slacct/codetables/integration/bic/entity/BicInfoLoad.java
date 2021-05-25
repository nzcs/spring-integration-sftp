package hu.erste.slacct.codetables.integration.bic.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "BIC_INFO_LOAD")
public class BicInfoLoad {

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SLACCT_CODE_TABLES_SEQ", sequenceName = "SLACCT_CODE_TABLES_SEQ")
    @GeneratedValue(generator = "SLACCT_CODE_TABLES_SEQ")
    Long id;

    @Column(name = "VERSION")
    int version;

    @Column(name = "LOAD_TIME")
    LocalDate loadTime;
}
