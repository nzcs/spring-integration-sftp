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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static javax.persistence.FetchType.LAZY;


@Data
@NoArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "ROUTING_TABLE")
public class RoutingTable {

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SLACCT_CODE_TABLES_SEQ", sequenceName = "SLACCT_CODE_TABLES_SEQ")
    @GeneratedValue(generator = "SLACCT_CODE_TABLES_SEQ")
    Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ROUTING_TABLE_LOAD_ID")
    RoutingTableLoad routingTableLoad;

    @Column(name = "GIRO_CODE")
    int giroCode;

    @Column(name = "VIBER_SIGN")
    String viberSign;

    @Column(name = "VIBER_BIC")
    String viberBic;

    @Column(name = "GIRO_ACCOUNT_NO_LENGTH")
    int giroAccountLength;

    @Column(name = "GIRO_BANK")
    String giroBank;

    @Column(name = "GIRO_ADDRESS")
    String giroAddress;

    @Column(name = "GIRO_NATURE")
    String giroNature;

    @Column(name = "GIRO_DIRECT")
    String giroDirect;

    @Column(name = "GIRO_GID")
    String giroGid;

    @Column(name = "VIBER_TANDT")
    String viberTandt;

    @Column(name = "VIBER_SEND")
    String viberSend;

    @Column(name = "VIBER_RECEIVE")
    String viberReceive;

    @Column(name = "AFR_NATURE")
    String afrNature;

    @Column(name = "AFR_DIRECT_BIC")
    String afrDirectBic;

    @Column(name = "AFR_INDIRECT_BIC")
    String afrIndirectBic;

    @Column(name = "PRIMARY_BANK_CODE")
    int primaryBankCode;
}
