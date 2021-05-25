package hu.erste.slacct.codetables.integration.bic.repository;

import hu.erste.slacct.codetables.integration.bic.entity.BicInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BicInfoRepository extends JpaRepository<BicInfo, Long> {

}
