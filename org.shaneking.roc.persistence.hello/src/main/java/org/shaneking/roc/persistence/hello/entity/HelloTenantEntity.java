package org.shaneking.roc.persistence.hello.entity;

import com.github.liaochong.myexcel.core.annotation.ExcelModel;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.shaneking.ling.persistence.hello.NullSetter;
import org.shaneking.ling.persistence.sql.entity.sqllite.SqlliteDialectSqlEntities;
import org.shaneking.roc.persistence.entity.TenantEntity;
import org.springframework.stereotype.Component;

import javax.persistence.Table;

@Accessors(chain = true)
@Component
@ExcelModel(includeAllField = false, useFieldNameAsTitle = true)
@Table
@ToString(callSuper = true)
public class HelloTenantEntity extends TenantEntity implements SqlliteDialectSqlEntities, NullSetter {
  @Override
  public Class<? extends HelloTenantEntity> entityClass() {
    return HelloTenantEntity.class;
  }
}
