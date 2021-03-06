package sktest.roc.persistence.hello;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.test.SKUnit;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.hello.HelloApiAccessUrlEntity;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class HelloApiAccessUrlEntityTest extends SKUnit {

  @Test
  void createTableAndIndexIfNotExistSql() throws IOException {
    Files.write(tstOFiles().toPath(), new HelloApiAccessUrlEntity().createTableAndIndexIfNotExistSql().getBytes());
    Assertions.assertEquals(String.join(String0.BR_LINUX, Files.readAllLines(tstOFiles().toPath())).trim(), new HelloApiAccessUrlEntity().createTableAndIndexIfNotExistSql().trim());
  }

  @Test
  void entityClass() {
    assertNotNull(new HelloApiAccessUrlEntity().entityClass());
  }

  @Test
  void testToString() {
    assertAll(
      () -> Assertions.assertEquals("HelloApiAccessUrlEntity(super=ApiAccessUrlExample(super=TenantedChannelizedEntity(super=AbstractCacheableEntity(super=AbstractDialectSqlEntity(id=null, version=null, dd=N, invalid=null, lastModifyDateTime=null, lastModifyUserId=null, no=null), lastModifyUser=null), channelId=null, tenantId=null), op=null, url=null))", new HelloApiAccessUrlEntity().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new HelloApiAccessUrlEntity().nullSetter()))
    );
  }
}
