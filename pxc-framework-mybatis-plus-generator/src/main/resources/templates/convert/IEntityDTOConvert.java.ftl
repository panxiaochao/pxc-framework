package ${application}.api.assembler.mapper;

import ${application}.api.request.${entity}CreateRequest;
import ${application}.api.request.${entity}QueryRequest;
import ${application}.api.request.${entity}UpdateRequest;
import ${application}.api.response.${entity}QueryResponse;
import ${application}.api.response.${entity}Response;
import ${domain}.entity.${entity};
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * ${table.comment!}数据传输对象结构映射
 *
 * @author ${author}
 * @since ${date}
 */
@Mapper
public interface I${entity}DTOConvert {

    /**
     * ${table.comment!}数据传输对象结构映射实例
     */
    I${entity}DTOConvert INSTANCE = Mappers.getMapper(I${entity}DTOConvert.class);

    /**
     * ${table.comment!}创建请求数据传输对象 转 ${table.comment!}实体
     *
     * @param createRequest ${table.comment!}创建请求数据传输对象
     * @return ${table.comment!}实体
     */
    ${entity} fromCreateRequest(${entity}CreateRequest createRequest);

    /**
     * ${table.comment!}更新请求数据传输对象 转 ${table.comment!}实体
     *
     * @param updateRequest ${table.comment!}更新请求数据传输对象
     * @return ${table.comment!}实体
     */
    ${entity} fromUpdateRequest(${entity}UpdateRequest updateRequest);

    /**
     * ${table.comment!}查询请求数据传输对象 转 ${table.comment!}实体
     *
     * @param queryRequest ${table.comment!}查询请求数据传输对象
     * @return ${table.comment!}实体
     */
    ${entity} fromQueryRequest(${entity}QueryRequest queryRequest);

    /**
     * ${table.comment!}实体 转 ${table.comment!}响应数据传输对象
     *
     * @param ${entity?uncap_first} ${table.comment!}实体
     * @return ${table.comment!}响应数据传输对象
     */
    ${entity}Response toResponse(${entity} ${entity?uncap_first});

    /**
     * ${table.comment!}实体 转 ${table.comment!}查询响应数据传输对象
     *
     * @param ${entity?uncap_first} ${table.comment!}实体
     * @return ${table.comment!}查询响应数据传输对象
     */
    ${entity}QueryResponse toQueryResponse(${entity} ${entity?uncap_first});

    /**
     * ${table.comment!}实体列表 转 ${table.comment!}查询响应数据传输对象列表
     *
     * @param ${entity?uncap_first}List ${table.comment!}实体列表
     * @return ${table.comment!}查询响应数据传输对象列表
     */
    List<${entity}QueryResponse> toQueryResponse(List<${entity}> ${entity?uncap_first}List);
}
