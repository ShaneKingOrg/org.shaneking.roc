package sktest.roc.persistence.hello.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.hello.entity.HelloApiAccessUrlEntity;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class HelloApiAccessUrlEntityTest extends SKUnit {

  @Test
  void createTableIfNotExistSql() throws IOException {
    Files.write(tstOFiles().toPath(), new HelloApiAccessUrlEntity().createTableIfNotExistSql().getBytes());
    Assertions.assertEquals(String.join(String0.BR_LINUX, Files.readAllLines(tstOFiles().toPath())).trim(), new HelloApiAccessUrlEntity().createTableIfNotExistSql().trim());
  }

  @Test
  void entityClass() {
    assertNotNull(new HelloApiAccessUrlEntity().entityClass());
  }

  @Test
  void testToString() {
    assertAll(
      () -> Assertions.assertEquals("HelloApiAccessUrlEntity(super=ApiAccessUrlEntity(super=ApiAccessOpEntity(super=TenantedChannelizedEntity(super=CacheableEntity(super=AbstractIdNoAdtVerSqlEntity(id=null, no=null, invalid=null, lastModifyDateTime=null, lastModifyUserId=null, version=null), lastModifyUser=null), channelId=null, tenantId=null), op=null), url=null))", new HelloApiAccessUrlEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new HelloApiAccessUrlEntity().nullSetter()))
    );
  }
}
