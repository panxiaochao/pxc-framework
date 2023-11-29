package ${application}.service;

import ${domain}.service.${entity}DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p> ${table.comment!} 服务类. </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
@RequiredArgsConstructor
public class ${serviceAppName} {

    /**
     * ${table.comment!} 服务类
     */
    private final ${entity}DomainService ${entity?uncap_first}DomainService;

}
