package org.shaneking.roc.persistence.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.shaneking.ling.jackson.databind.OM3;
import org.shaneking.ling.zero.lang.Object0;
import org.shaneking.ling.zero.lang.String0;
import org.shaneking.roc.persistence.annotation.EntityCacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class EntityCacheEvictAspect {
  @Autowired
  private EntityCacheAbstractWrapper entityCacheAbstractWrapper;

  @Pointcut("execution(@org.shaneking.roc.persistence.annotation.EntityCacheEvict * *..*.*(..))")
  private void pointcut() {
  }

  @After("pointcut() && @annotation(entityCacheable)")
  public void after(JoinPoint jp, EntityCacheable entityCacheable) throws Throwable {
    if (jp.getArgs().length > entityCacheable.clsIdx() && jp.getArgs()[entityCacheable.clsIdx()] instanceof Class) {
      Class clazz = (Class) jp.getArgs()[entityCacheable.clsIdx()];
      try {
        if (entityCacheable.pKeyIdx() > -1) {
          Object pKeyObj = jp.getArgs()[entityCacheable.pKeyIdx()];
          if (pKeyObj instanceof List) {
            //org.shaneking.roc.persistence.dao.CacheableDao.delByIds
            List<String> pKeyList = String0.isNullOrEmpty(entityCacheable.pKeyPath()) ? (List<String>) pKeyObj : ((List<Object>) pKeyObj).parallelStream().map(o -> String.valueOf(Object0.gs(o, entityCacheable.pKeyPath()))).filter(s -> !String0.isNullOrEmpty(s)).collect(Collectors.toList());
            log.info(MessageFormat.format("{0} - {1}({2}) : {3}", clazz.getName(), EntityCacheUtils.INFO_CODE__CACHE_HIT_PART, entityCacheAbstractWrapper.hdel(clazz.getName(), pKeyList.toArray(new String[0])), OM3.writeValueAsString(pKeyList)));
          } else {
            //org.shaneking.roc.persistence.dao.CacheableDao.delById(java.lang.Class<T>, T)
            //org.shaneking.roc.persistence.dao.CacheableDao.delById(java.lang.Class<T>, T, java.lang.String)
            String k = String.valueOf(String0.isNullOrEmpty(entityCacheable.pKeyPath()) ? pKeyObj : Object0.gs(pKeyObj, entityCacheable.pKeyPath()));
            if (String0.isNull2Empty(k)) {
              log.warn(MessageFormat.format("{0} - {1}", jp.getSignature().getName(), EntityCacheUtils.ERR_CODE__ANNOTATION_SETTING_ERROR));
            } else {
              log.info(MessageFormat.format("{0} - {1}({2}) : {3}", clazz.getName(), EntityCacheUtils.INFO_CODE__CACHE_HIT_PART, entityCacheAbstractWrapper.hdel(clazz.getName(), k), k));
            }
          }
        }
      } catch (Throwable e) {
        log.error(String.valueOf(clazz), e);
      }
    } else {
      log.warn(MessageFormat.format("{0} - {1}", jp.getSignature().getName(), EntityCacheUtils.ERR_CODE__ANNOTATION_SETTING_ERROR));
    }
  }
}
