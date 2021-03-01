package org.shaneking.roc.persistence.entity.sql;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.roc.persistence.entity.ChannelizedEntity;

import javax.persistence.Column;

@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class ApiAccess3Entity extends ChannelizedEntity implements ApiAccess3Entities {
  @Column(columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String allowSignature;

  @Column(columnDefinition = "default '' COMMENT ''")
  @ExcelColumn
  @Getter
  @Setter
  private String denySignature;
}