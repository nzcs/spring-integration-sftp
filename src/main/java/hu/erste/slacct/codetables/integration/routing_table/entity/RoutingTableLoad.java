package hu.erste.slacct.codetables.integration.routing_table.entity;

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
import java.time.ZonedDateTime;


@Data
@NoArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "ROUTING_TABLE_LOAD")
public class RoutingTableLoad {

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SLACCT_CODE_TABLES_SEQ", sequenceName = "SLACCT_CODE_TABLES_SEQ")
    @GeneratedValue(generator = "SLACCT_CODE_TABLES_SEQ")
    Long id;

    @Column(name = "VERSION")
    int version;

    @Column(name = "VALID_FROM")
    LocalDate validFrom;

    @Column(name = "LOAD_TIME")
    ZonedDateTime loadTime = ZonedDateTime.now();
}
