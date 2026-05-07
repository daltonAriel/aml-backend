package com.aml.app.modules.menuRol;

import java.sql.Types;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class MenuRolId {

    @JdbcTypeCode(Types.VARCHAR)
    private UUID menuId;

    @JdbcTypeCode(Types.VARCHAR)
    private UUID rolId;

}
