package sktest.roc.rr;

import org.junit.jupiter.api.Test;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.util.UUID0;
import org.shaneking.roc.rr.Pub;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PubTest {

  @Test
  void testToString() {
    String cul33 = UUID0.cUl33();
    assertAll(
      () -> assertEquals("Pub(channelName=null, encoded=null, jon=null, tenantName=null, tracingId=null)", new Pub().toString()),
      () -> assertEquals("{}", OM3.writeValueAsString(new Pub())),
      () -> assertEquals("{\"channelName\":\"channelName\",\"encoded\":\"N\",\"jon\":{},\"tenantName\":\"tenantName\",\"tracingId\":\"" + cul33 + "\"}", OM3.writeValueAsString(new Pub().setChannelName("channelName").setEncoded(String0.N).setJon(OM3.createObjectNode()).setTenantName("tenantName").setTracingId(cul33)))
    );
  }
}