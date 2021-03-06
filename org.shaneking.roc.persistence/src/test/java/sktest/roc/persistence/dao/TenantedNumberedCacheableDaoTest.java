package sktest.roc.persistence.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.util.List0;
import org.shaneking.roc.persistence.dao.TenantedNumberedCacheableDao;
import org.shaneking.roc.test.SKSpringUnit;
import org.springframework.beans.factory.annotation.Autowired;
import sktest.roc.persistence.entity.TenantedNumberedEntityPrepare;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TenantedNumberedCacheableDaoTest extends SKSpringUnit {

  @Autowired
  private TenantedNumberedCacheableDao tenantedNumberedCacheableDao;

  @BeforeEach
  void beforeEach() {
    tenantedNumberedCacheableDao.getCacheableDao().rmvByIds(TenantedNumberedEntityPrepare.class, new TenantedNumberedEntityPrepare(), List0.newArrayList(tenantedNumberedCacheableDao.getCacheableDao().ids(TenantedNumberedEntityPrepare.class, new TenantedNumberedEntityPrepare()).split(String0.COMMA)));

    TenantedNumberedEntityPrepare helloGlobalNumberedEntity = new TenantedNumberedEntityPrepare();
    helloGlobalNumberedEntity.setTenantId(String0.ALPHABET).setNo(String0.ARY_HEX).initWithUserIdAndId(String0.ARY_HEX, String0.ARY_HEX);
    tenantedNumberedCacheableDao.getCacheableDao().add(TenantedNumberedEntityPrepare.class, helloGlobalNumberedEntity);
  }

  @Test
  void oneByNo() {
    assertEquals(String0.ARY_HEX, tenantedNumberedCacheableDao.oneByNo(TenantedNumberedEntityPrepare.class, String0.ARY_HEX, String0.ALPHABET).getId());
    assertEquals(String0.ARY_HEX, tenantedNumberedCacheableDao.oneByNo(TenantedNumberedEntityPrepare.class, String0.ARY_HEX, String0.ALPHABET).getId());
    assertNull(tenantedNumberedCacheableDao.oneByNo(TenantedNumberedEntityPrepare.class, String0.ARY_L62, String0.ALPHABET));
  }
}
