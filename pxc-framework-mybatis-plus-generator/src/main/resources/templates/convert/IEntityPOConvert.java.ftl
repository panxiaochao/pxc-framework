package ${infrastructure}.convert;

import ${application}.api.response.${entity}QueryResponse;
import ${domain}.entity.${entity};
import ${infrastructure}.po.${entity}PO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * ${table.comment!}持久化对象结构映射
 *
 * @author ${author}
 * @since ${date}
 */
@Mapper
public interface I${entity}POConvert {

    /**
     * ${table.comment!}持久化对象结构映射实例
     */
    I${entity}POConvert INSTANCE = Mappers.getMapper(I${entity}POConvert.class);

    /**
     * ${table.comment!}实体 转 ${table.comment!}持久化对象
     *
     * @param ${entity?uncap_first} ${table.comment!}实体
     * @return ${table.comment!}持久化对象
     */
    ${entity}PO fromEntity(${entity} ${entity?uncap_first});

    /**
     * ${table.comment!}实体 转 ${table.comment!}持久化对象
     *
     * @param ${entity?uncap_first}List ${table.comment!}实体
     * @return ${table.comment!}持久化对象
     */
    List<${entity}PO> fromEntity(List<${entity}> ${entity?uncap_first}List);

    /**
     * ${table.comment!}持久化对象 转 ${table.comment!}实体
     *
     * @param ${entity?uncap_first}PO ${table.comment!}持久化对象
     * @return ${table.comment!}实体
     */
    ${entity} toEntity(${entity}PO ${entity?uncap_first}PO);

    /**
     * ${table.comment!}持久化对象 转 ${table.comment!}实体
     *
     * @param ${entity?uncap_first}POList ${table.comment!}持久化对象
     * @return ${table.comment!}实体
     */
    List<${entity}> toEntity(List<${entity}PO> ${entity?uncap_first}POList);

    /**
     * ${table.comment!}持久化对象 转 ${table.comment!}查询响应数据传输对象
     *
     * @param ${entity?uncap_first}PO ${table.comment!}持久化对象
     * @return ${table.comment!}查询响应数据传输对象
     */
    ${entity}QueryResponse toQueryResponse(${entity}PO ${entity?uncap_first}PO);

    /**
     * ${table.comment!}持久化对象列表 转 ${table.comment!}查询响应数据传输对象列表
     *
     * @param ${entity?uncap_first}POList ${table.comment!}持久化对象列表
     * @return ${table.comment!}查询响应数据传输对象列表
     */
    List<${entity}QueryResponse> toQueryResponse(List<${entity}PO> ${entity?uncap_first}POList);
}
