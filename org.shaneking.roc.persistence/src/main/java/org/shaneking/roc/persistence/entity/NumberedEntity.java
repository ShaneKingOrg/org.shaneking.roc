package org.shaneking.roc.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.Numbered;

import javax.persistence.Column;

@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class NumberedEntity extends TenantedEntity implements Numbered {
  @Column(length = 40, columnDefinition = "default '' COMMENT ''")
  @Getter
  @Setter
  private String no;
}