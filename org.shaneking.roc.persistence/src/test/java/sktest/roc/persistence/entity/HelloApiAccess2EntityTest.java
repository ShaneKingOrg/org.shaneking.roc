package sktest.roc.persistence.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloApiAccess2EntityTest extends SKUnit {

  @Test
  void testToString() {
    assertAll(
      () -> Assertions.assertEquals("HelloApiAccess2Entity(super=ApiAccess2Entity(super=TenantChannelizedEntity(super=TenantedEntity(super=CacheableEntity(super=AbstractIdAdtVerSqlEntity(id=null, invalid=null, lastModifyDateTime=null, lastModifyUserId=null, version=null), lastModifyUser=null), tenantId=null), channelId=null), allowUrl=null, denyUrl=null))", new HelloApiAccess2Entity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new HelloApiAccess2Entity().nullSetter()))
    );
  }
}
