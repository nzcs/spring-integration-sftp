package hu.erste.slacct.codetables.integration.routing_table.repository;

import hu.erste.slacct.codetables.integration.routing_table.entity.RoutingTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RoutingTableRepository extends JpaRepository<RoutingTable, Long> {

    @Query("select rt from RoutingTable rt " +
            " where rt.routingTableLoad.version = :version " +
            "   and rt.routingTableLoad.validFrom = :validFrom")
    List<RoutingTable> findAllBy(int version, LocalDate validFrom);
}
