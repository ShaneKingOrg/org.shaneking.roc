package org.shaneking.roc.rr.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.persistence.entity.sql.Numbered;
import org.shaneking.ling.rr.Resp;
import org.shaneking.ling.zero.annotation.ZeroAnnotation;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.ling.zero.net.InetAddress0;
import org.shaneking.ling.zero.util.Date0;
import org.shaneking.ling.zero.util.UUID0;
import org.shaneking.roc.persistence.dao.CacheableDao;
import org.shaneking.roc.persistence.dao.NumberedCacheableDao;
import org.shaneking.roc.persistence.entity.sql.AuditLogEntities;
import org.shaneking.roc.persistence.entity.sql.ChannelEntities;
import org.shaneking.roc.persistence.entity.sql.TenantEntities;
import org.shaneking.roc.rr.Pub;
import org.shaneking.roc.rr.Req;
import org.shaneking.roc.rr.annotation.RrAudit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Aspect
@Component
@ConditionalOnProperty(prefix = "sk.roc.rr.audit", value = "enabled")
@Slf4j
@Order(400)///small will first
public class RrAuditAspect {
  @Value("${sk.roc.rr.audit.enabled:false}")
  private boolean enabled;

  @Autowired
  private CacheableDao cacheableDao;

  @Autowired
  private NumberedCacheableDao numberedCacheableDao;

  @Autowired
  private AuditLogEntities auditLogEntityClass;

  @Autowired
  private ChannelEntities channelEntityClass;

  @Autowired
  private TenantEntities tenantEntityClass;

  @Pointcut("execution(@org.shaneking.roc.rr.annotation.RrAudit * *..*.*(..))")
  private void pointcut() {
  }

  @Around("pointcut() && @annotation(rrAudit)")
  public Object around(ProceedingJoinPoint pjp, RrAudit rrAudit) throws Throwable {
    Object rtn = null;
    boolean ifExceptionThenInProceed = false;
    if (enabled) {
      if (pjp.getArgs().length > rrAudit.reqParamIdx() && pjp.getArgs()[rrAudit.reqParamIdx()] instanceof Req) {
        Req<?, ?> req = (Req<?, ?>) pjp.getArgs()[rrAudit.reqParamIdx()];
        log.info(OM3.writeValueAsString(req));

        req.getPub().setTracingId(String0.nullOrEmptyTo(req.getPub().getTracingId(), UUID0.cUl33()));
        AuditLogEntities auditLogEntity = null;
        try {
          auditLogEntity = auditLogEntityClass.entityClass().newInstance();
          auditLogEntity.setId(UUID0.cUl33());
          auditLogEntity.setInvalid(String0.N);
          auditLogEntity.setLastModifyDateTime(Date0.on().dateTime());
//          auditLogEntity.setLastModifyUserId();
          auditLogEntity.setVersion(0);
//          auditLogEntity.setChannelId();//access
//          auditLogEntity.setTenantId();//access
          auditLogEntity.setTracingId(req.getPub().getTracingId());
          auditLogEntity.setReqDatetime(Date0.on().datetimes());
//          auditLogEntity.setReqIps();///web
//          auditLogEntity.setReqUserId();//crypto
          auditLogEntity.setReqJsonStrRaw(OM3.writeValueAsString(req));
//          auditLogEntity.setReqJsonStr();//crypto
//          auditLogEntity.setReqUrl();///web
          auditLogEntity.setReqSignature(pjp.getSignature().toLongString());
//          auditLogEntity.setCached();//cache
//          auditLogEntity.setRespJsonStr();//crypto
//          auditLogEntity.setRespJsonStrCtx();
//          auditLogEntity.setRespJsonStrRaw();
          auditLogEntity.setRespIps(OM3.writeValueAsString(InetAddress0.localHostExactAddress()));
//          auditLogEntity.setRespDatetime();
          req.gnnCtx().setAuditLog(auditLogEntity);

          if (req.getPub() == null || String0.isNullOrEmpty(req.getPub().getChannelNo())) {
            rtn = Resp.failed(Pub.ERR_CODE__REQUIRED_CHANNEL_NUMBER, OM3.writeValueAsString(req.getPub()), req);
          } else {
            ChannelEntities channelEntity = numberedCacheableDao.oneByNo(channelEntityClass.entityClass(), req.getPub().getChannelNo(), true);
            if (channelEntity == null) {
              rtn = Resp.failed(Numbered.ERR_CODE__NOT_FOUND_BY_NUMBER, req.getPub().getChannelNo(), req);
            } else {
              req.gnnCtx().setChannel(channelEntity);
              if (req.gnnCtx().getAuditLog() != null) {
                req.gnnCtx().getAuditLog().setChannelId(channelEntity.getId());
              }

              TenantEntities tenantEntity = numberedCacheableDao.oneByNo(tenantEntityClass.entityClass(), String0.nullOrEmptyTo(req.getPub().getTenantNo(), req.getPub().getChannelNo()), true);
              if (tenantEntity == null) {
                rtn = Resp.failed(Numbered.ERR_CODE__NOT_FOUND_BY_NUMBER, String.valueOf(req.getPub().getTenantNo()), req);
              } else {
                req.gnnCtx().setTenant(tenantEntity);
                if (req.gnnCtx().getAuditLog() != null) {
                  req.gnnCtx().getAuditLog().setTenantId(tenantEntity.getId());
                }

                ifExceptionThenInProceed = true;
                rtn = pjp.proceed();
              }
            }
          }
        } catch (Throwable throwable) {
          log.error(OM3.lp(rtn, req, auditLogEntity), throwable);
          if (ifExceptionThenInProceed) {
            throw throwable;
          } else {
            rtn = pjp.proceed();
          }
        } finally {
          try {
            if (auditLogEntity != null) {
              auditLogEntity.setLastModifyDateTime(Date0.on().dateTime());
              auditLogEntity.setLastModifyUserId(auditLogEntity.getReqUserId());
              auditLogEntity.setRespJsonStrCtx(OM3.writeValueAsString(req.gnnCtx()));
              req.setCtx(null);
              if (rtn != null) {
                if (rtn instanceof Resp && ((Resp<?>) rtn).getData() instanceof Req) {
                  //cached scenario
                  Req<?, ?> respData = (Req<?, ?>) ((Resp<?>) rtn).getData();
                  if (OM3.OBJECT_ERROR_STRING.equalsIgnoreCase(auditLogEntity.getRespJsonStrCtx())) {
                    auditLogEntity.setRespJsonStrCtx(OM3.writeValueAsString(respData.gnnCtx()));
                  }
                  respData.setCtx(null);
                }
                auditLogEntity.setRespJsonStrRaw(OM3.writeValueAsString(rtn));
              }
              auditLogEntity.setRespDatetime(Date0.on().datetimes());

              log.info(OM3.writeValueAsString(auditLogEntity));
              cacheableDao.add(auditLogEntity.entityClass(), auditLogEntity);
            }
          } catch (Throwable throwable) {
            log.error(OM3.lp(rtn, req, auditLogEntity), throwable);
          }
        }
      } else {
        log.error(MessageFormat.format("{0} - {1} : {2}", ZeroAnnotation.ERR_CODE__ANNOTATION_SETTING_ERROR, pjp.getSignature().toLongString(), OM3.writeValueAsString(rrAudit)));
        rtn = pjp.proceed();
      }
    } else {
      rtn = pjp.proceed();
    }
    return rtn;
  }
}
