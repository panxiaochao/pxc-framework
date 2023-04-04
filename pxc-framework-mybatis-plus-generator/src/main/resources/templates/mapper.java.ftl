package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
<#if mapperAnnotationClass??>
    import ${mapperAnnotationClass.name};
</#if>

/**
* <p> ${table.comment!} 持久化接口
    *
    * @author ${author}
    * @since ${date}
    */
    <#if mapperAnnotationClass??>
        @${mapperAnnotationClass.simpleName}
    </#if>
    <#if kotlin>
        interface ${table.mapperName} : ${superMapperClass}<${entity}>
    <#else>
        public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

        }
    </#if>
