package hu.erste.slacct.codetables.integration.bic.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "BIC_INFO")
public class BicInfo {

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SLACCT_CODE_TABLES_SEQ", sequenceName = "SLACCT_CODE_TABLES_SEQ")
    @GeneratedValue(generator = "SLACCT_CODE_TABLES_SEQ")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BIC_INFO_LOAD_ID")
    BicInfoLoad bicInfoLoad;

    @Column(name = "MONTH")
    LocalDate month;

    @Column(name = "BIC8")
    String bic8;

    @Column(name = "BIC")
    String bic;

    @Column(name = "INSTITUTION")
    String institution;

    @Column(name = "CITY")
    String city;

    @Column(name = "ISO_COUNTRY_CODE")
    String isoCountryCode;
}
