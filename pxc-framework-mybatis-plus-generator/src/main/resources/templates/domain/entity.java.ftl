package ${domain}.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * <p>${table.comment!} 实体. </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Getter
@Setter
@ToString
public class ${entity} {
<#list table.fields as field>

    <#if field.keyFlag>
    /**
    * ${field.comment}
    */
    private String ${field.propertyName};
    <#-- 普通字段 -->
    <#else>
    /**
     * ${field.comment}
     */
    private ${field.propertyType} ${field.propertyName};
    </#if>
</#list>
}
