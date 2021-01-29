package org.shaneking.roc.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;

@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class TenantEntity extends CacheableEntity {
  @Column(length = 10, unique = true, columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String name;

  @Column(columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String description;

  //@see sktest.roc.rr.cfg.RrCfg.test4TenantEntity
  public abstract <T extends TenantEntity> Class<T> entityClass();
}