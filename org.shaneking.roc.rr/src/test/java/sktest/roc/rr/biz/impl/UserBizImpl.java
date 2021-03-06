package sktest.roc.rr.biz.impl;

import org.shaneking.ling.rr.Resp;
import org.shaneking.roc.persistence.dao.CacheableDao;
import org.shaneking.roc.persistence.hello.HelloUserEntity;
import org.shaneking.roc.rr.Req;
import org.shaneking.roc.rr.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBizImpl {

  @Autowired
  private CacheableDao cacheableDao;

  @RrAudit
  @RrAccess
  @RrCrypto
  public Resp<Req<HelloUserEntity, Integer>> add(Req<HelloUserEntity, Integer> req) {
    Resp<Req<HelloUserEntity, Integer>> resp = Resp.success(req);
    req.getPri().setRtn(cacheableDao.add(HelloUserEntity.class, CacheableDao.pti(req.getPri().getObj(), req.gnnCtx().gnaTenantId())));
    return resp;
  }

  @RrAudit
  @RrAccess
  @RrCrypto
  public Resp<Req<String, Integer>> rmvById(Req<String, Integer> req) {
    Resp<Req<String, Integer>> resp = Resp.success(req);
    HelloUserEntity userEntity = new HelloUserEntity();
    userEntity.setId(req.getPri().getObj());
    req.getPri().setRtn(cacheableDao.rmvById(HelloUserEntity.class, CacheableDao.ptu(userEntity, req.gnnCtx().gnaTenantId())));
    return resp;
  }

  @RrAudit
  @RrAccess
  @RrCrypto
  public Resp<Req<HelloUserEntity, Integer>> modByIdVer(Req<HelloUserEntity, Integer> req) {
    Resp<Req<HelloUserEntity, Integer>> resp = Resp.success(req);
    req.getPri().setRtn(cacheableDao.modByIdVer(HelloUserEntity.class, CacheableDao.ptu(req.getPri().getObj(), req.gnnCtx().gnaTenantId())));
    return resp;
  }

  @RrLimiting(prop = "sktest.roc.rr.biz.impl.UserBizImpl.lst", limit = 1)
  @RrAudit
  @RrAccess
  @RrCache
  @RrCrypto
  public Resp<Req<HelloUserEntity, List<HelloUserEntity>>> lst(Req<HelloUserEntity, List<HelloUserEntity>> req) {
    Resp<Req<HelloUserEntity, List<HelloUserEntity>>> resp = Resp.success(req);
    req.getPri().setRtn(cacheableDao.lst(HelloUserEntity.class, CacheableDao.pts(req.getPri().getObj(), req.gnnCtx().gnaTenantId())));
    return resp;
  }
}
