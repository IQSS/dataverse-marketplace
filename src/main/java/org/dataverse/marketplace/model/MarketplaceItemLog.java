package org.dataverse.marketplace.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "log_mkt_item")
public class MarketplaceItemLog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mkt_item_log_id_seq")
    @SequenceGenerator(
            name = "mkt_item_log_id_seq",
            sequenceName = "mkt_item_log_id_seq",
            allocationSize = 1)
    Integer id;

    @Column(name = "mkt_item_id")
    Integer mktItemId;

    @Column(name = "date_time")
    LocalDateTime dateTime;

    @Column(name = "user_id")
    Long userId;

    @Column(name = "operation")
    String operation;





}
