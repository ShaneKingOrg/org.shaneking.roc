package org.shaneking.roc.persistence.entity.sql.example;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.roc.persistence.entity.TenantedEntity;
import org.shaneking.roc.persistence.entity.sql.TenantReadableTenantEntities;

import javax.persistence.Column;

@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class TenantReadableTenantExample extends TenantedEntity implements TenantReadableTenantEntities {
  @Column(length = 40, columnDefinition = "default '' COMMENT ''")
  @ExcelColumn(style = {"title->color:red"})
  @Getter
  @Setter
  private String toTenantId;

  @Column(nullable = false, length = 1, columnDefinition = "default 'N' COMMENT ''")
  @ExcelColumn(style = {"title->color:red"})
  @Getter
  @Setter
  private String defaultReadable;
}
