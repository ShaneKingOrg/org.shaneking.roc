package sktest.roc.rr;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.roc.persistence.hello.HelloUserEntity;
import org.shaneking.roc.rr.Ctx;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CtxTest {

  @Test
  void testToString() {
    assertAll(
      () -> assertEquals("Ctx(auditLog=null, channel=null, proxyChannel=null, jon=null, language=null, tenant=null, user=null, rtuMap={}, crtList=[], trtList=[])", new Ctx().toString()),
      () -> assertEquals("{\"rtuMap\":{},\"crtList\":[],\"trtList\":[]}", OM3.writeValueAsString(new Ctx())),
      () -> assertEquals("{\"jon\":{},\"language\":\"language\",\"user\":{\"dd\":\"N\"},\"rtuMap\":{},\"crtList\":[],\"trtList\":[]}", OM3.writeValueAsString(new Ctx().setJon(OM3.createObjectNode()).setLanguage("language").setUser(new HelloUserEntity())))
    );
  }
}
