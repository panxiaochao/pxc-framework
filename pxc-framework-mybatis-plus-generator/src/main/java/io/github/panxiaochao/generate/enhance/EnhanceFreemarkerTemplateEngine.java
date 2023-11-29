/*
 * Copyright © 2022-2023 Lypxc (545685602@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.panxiaochao.generate.enhance;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import io.github.panxiaochao.core.utils.StringPools;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * Freemarker 模版引擎增强.
 * </p>
 *
 * @author Lypxc
 * @since 2023-02-17
 */
public class EnhanceFreemarkerTemplateEngine extends FreemarkerTemplateEngine {

	private static final String SRC_MAIN_JAVA = "src/main/java";

	private static final String SRC_MAIN_RESOURCES = "src/main/resources";

	private static final String APPLICATION_JAVA_FILE = "Application.java";

	private static final String APPLICATION_YML_FILE = "application.yml";

	private static final String TEMPLATE_APPLICATION_JAVA_FILE_PATH = "/templates/application/";

	private static final String POM_XML_FILE = "pom.xml";

	private static final String TEMPLATE_POM_XML_FILE_PATH = "/templates/pom/";

	/**
	 * 渲染对象 MAP 信息
	 * @param configBuilder 配置信息
	 * @param tableInfo 表信息对象
	 * @return ignore
	 */
	@Override
	public Map<String, Object> getObjectMap(ConfigBuilder configBuilder, TableInfo tableInfo) {
		// 继承原先渲对象的逻辑
		Map<String, Object> objectMap = super.getObjectMap(configBuilder, tableInfo);
		// 以下加入自定义
		// 修改idType的类型
		for (TableField field : tableInfo.getFields()) {
			// 主键字段
			if (field.isKeyFlag()) {
				// 如果主键是自增类型，设置 idType 为 AUTO
				if (field.isKeyIdentityFlag()) {
					objectMap.put("idType", "AUTO");
				}
				else if ("String".equals(field.getPropertyType())) {
					objectMap.put("idType", "ASSIGN_UUID");
				}
				else {
					objectMap.put("idType", "ASSIGN_ID");
				}
				objectMap.put("idPropertyType", field.getPropertyType());
			}
		}
		return objectMap;
	}

	/**
	 * 批量输出 java xml 文件
	 */
	@Override
	public AbstractTemplateEngine batchOutput() {
		try {
			ConfigBuilder config = this.getConfigBuilder();
			List<TableInfo> tableInfoList = config.getTableInfoList();
			tableInfoList.forEach(tableInfo -> {
				Map<String, Object> objectMap = this.getObjectMap(config, tableInfo);
				Optional.ofNullable(config.getInjectionConfig()).ifPresent(t -> {
					// 添加自定义属性
					t.beforeOutputFile(tableInfo, objectMap);
					// 输出自定义文件
					outputCustomFile(t.getCustomFiles(), tableInfo, objectMap);
				});
				// entity
				outputEntityPO(tableInfo, objectMap);
				// mapper and xml
				outputMapper(tableInfo, objectMap);
				// service
				outputIService(tableInfo, objectMap);
				// controller
				outputController(tableInfo, objectMap);
				// 生成 Application 模块
				outputApplication(config, objectMap, tableInfo);
				// 生成 domain 模块
				outputDomain(config, objectMap, tableInfo);
				// 生成 Convert 模块
				outputConvert(config, objectMap, tableInfo);
			});
		}
		catch (Exception e) {
			throw new RuntimeException("无法创建文件，请检查配置信息！", e);
		}
		return this;
	}

	/**
	 * 输出实体文件
	 * @param tableInfo 表信息
	 * @param objectMap 渲染数据
	 */
	private void outputEntityPO(TableInfo tableInfo, Map<String, Object> objectMap) {
		String entityName = tableInfo.getEntityName() + "PO";
		String entityPath = getPathInfo(OutputFile.entity);
		if (StringUtils.isNotBlank(entityName) && StringUtils.isNotBlank(entityPath)) {
			getTemplateFilePath(template -> template.getEntity(getConfigBuilder().getGlobalConfig().isKotlin()))
				.ifPresent((entity) -> {
					String entityFile = String.format((entityPath + File.separator + "%s" + suffixJavaOrKt()),
							entityName);
					outputFile(getOutputFile(entityFile, OutputFile.entity), objectMap, entity,
							getConfigBuilder().getStrategyConfig().entity().isFileOverride());
				});
		}
	}

	/**
	 * 输出IService文件
	 * @param tableInfo 表信息
	 * @param objectMap 渲染数据
	 */
	private void outputIService(TableInfo tableInfo, Map<String, Object> objectMap) {
		// IMpService.java
		String entityName = tableInfo.getEntityName();
		// 判断是否要生成service接口
		if (tableInfo.isServiceInterface()) {
			String servicePath = getPathInfo(OutputFile.service);
			if (StringUtils.isNotBlank(tableInfo.getServiceName()) && StringUtils.isNotBlank(servicePath)) {
				getTemplateFilePath(TemplateConfig::getService).ifPresent(service -> {
					String serviceFile = String.format(
							(servicePath + File.separator + "I" + tableInfo.getServiceName() + suffixJavaOrKt()),
							entityName);
					outputFile(getOutputFile(serviceFile, OutputFile.service), objectMap, service,
							getConfigBuilder().getStrategyConfig().service().isFileOverride());
				});
			}
		}
		// MpServiceImpl.java
		String serviceImplPath = getPathInfo(OutputFile.serviceImpl);
		if (StringUtils.isNotBlank(tableInfo.getServiceImplName()) && StringUtils.isNotBlank(serviceImplPath)) {
			getTemplateFilePath(TemplateConfig::getServiceImpl).ifPresent(serviceImpl -> {
				String implFile = String.format(
						(serviceImplPath + File.separator + tableInfo.getServiceImplName() + suffixJavaOrKt()),
						entityName);
				outputFile(getOutputFile(implFile, OutputFile.serviceImpl), objectMap, serviceImpl,
						getConfigBuilder().getStrategyConfig().service().isFileOverride());
			});
		}
	}

	/**
	 * 输出 Application
	 * @param config 配置汇总
	 */
	private void outputApplication(ConfigBuilder config, Map<String, Object> objectMap, TableInfo tableInfo) {
		String entityName = tableInfo.getEntityName();
		String parentPath = getPathInfo(OutputFile.parent);
		String parentPackage = config.getPackageConfig().getParent();

		objectMap.put("application", parentPackage + ".application");

		String filePath = "";
		String templatePath = "";
		String[] requests = new String[] { "Create", "Update", "Query" };
		for (String request : requests) {
			filePath = parentPath + "/application/api/request/" + objectMap.get("entity") + request + "Request.java";
			templatePath = String.format("/templates/request/Entity%sRequest.java.ftl", request);
			try {
				super.outputFile(new File(filePath), objectMap, templatePath,
						config.getStrategyConfig().entity().isFileOverride());
			}
			catch (Exception exception) {
				throw new RuntimeException("创建" + filePath + "文件失败！", exception);
			}
		}
		String[] responses = new String[] { "Query", "" };
		for (String response : responses) {
			filePath = parentPath + "/application/api/response/" + objectMap.get("entity") + response + "Response.java";
			templatePath = String.format("/templates/response/Entity%sResponse.java.ftl", response);
			try {
				super.outputFile(new File(filePath), objectMap, templatePath,
						config.getStrategyConfig().entity().isFileOverride());
			}
			catch (Exception exception) {
				throw new RuntimeException("创建" + filePath + "文件失败！", exception);
			}
		}

		String serviceAppName = entityName + "AppService";
		objectMap.put("serviceAppName", serviceAppName);
		filePath = parentPath + "/application/service/" + serviceAppName + ".java";
		templatePath = "/templates/application/service.java.ftl";
		try {
			super.outputFile(new File(filePath), objectMap, templatePath,
					config.getStrategyConfig().entity().isFileOverride());
		}
		catch (Exception exception) {
			throw new RuntimeException("创建" + filePath + "文件失败！", exception);
		}
	}

	/**
	 * 输出 Convert
	 * @param config 配置汇总
	 */
	private void outputConvert(ConfigBuilder config, Map<String, Object> objectMap, TableInfo tableInfo) {
		String entityName = tableInfo.getEntityName();
		String parentPath = getPathInfo(OutputFile.parent);
		String parentPackage = config.getPackageConfig().getParent();

		objectMap.put("application", parentPackage + ".application");
		objectMap.put("domain", parentPackage + ".domain");
		objectMap.put("infrastructure", parentPackage + ".infrastructure");

		String filePath = "";
		String templatePath = "";
		String[] converts = new String[] { "PO", "DTO" };
		for (String convert : converts) {
			filePath = parentPath + "/convert/" + convert + "/I" + objectMap.get("entity") + convert + "Convert.java";
			templatePath = String.format("/templates/convert/IEntity%sConvert.java.ftl", convert);
			try {
				super.outputFile(new File(filePath), objectMap, templatePath,
						config.getStrategyConfig().entity().isFileOverride());
			}
			catch (Exception exception) {
				throw new RuntimeException("创建" + filePath + "文件失败！", exception);
			}
		}
	}

	/**
	 * 输出 Domain Entity.java
	 * @param config 配置汇总
	 */
	private void outputDomain(ConfigBuilder config, Map<String, Object> objectMap, TableInfo tableInfo) {
		String entityName = tableInfo.getEntityName();
		String parentPath = getPathInfo(OutputFile.parent);
		String parentPackage = config.getPackageConfig().getParent();

		objectMap.put("domain", parentPackage + ".domain.entity");

		String filePath = parentPath + "/domain/entity/" + objectMap.get("entity") + ".java";
		String templatePath = "/templates/domain/entity.java.ftl";
		try {
			super.outputFile(new File(filePath), objectMap, templatePath,
					config.getStrategyConfig().entity().isFileOverride());
		}
		catch (Exception exception) {
			throw new RuntimeException("创建 Entity.java 文件失败！", exception);
		}
	}

	/**
	 * 输出 application.yml
	 * @param config 配置汇总
	 */
	private void outputApplicationYml(ConfigBuilder config) {
		String outputDir = config.getGlobalConfig().getOutputDir();
		outputDir = outputDir.replace(SRC_MAIN_JAVA, SRC_MAIN_RESOURCES);
		Map<String, Object> objectMap = new HashMap<>(3);

		String applicationYmlFilePath = outputDir + File.separator + APPLICATION_YML_FILE;
		String templateApplicationYmlName = templateFilePath(APPLICATION_YML_FILE);
		String templateApplicationYmlPath = TEMPLATE_APPLICATION_JAVA_FILE_PATH + templateApplicationYmlName;
		try {
			super.writer(objectMap, templateApplicationYmlPath, new File(applicationYmlFilePath));
		}
		catch (Exception exception) {
			throw new RuntimeException("创建 application.yml 文件失败！", exception);
		}
	}

	/**
	 * 输出 pom.xml
	 * @param config 配置汇总
	 */
	private void outputPom(ConfigBuilder config) {
		String outputDir = config.getGlobalConfig().getOutputDir();
		outputDir = outputDir.replace(SRC_MAIN_JAVA, StringPools.EMPTY);
		Map<String, Object> objectMap = new HashMap<>(3);

		String pomFilePath = outputDir + POM_XML_FILE;
		String templatePomName = templateFilePath(POM_XML_FILE);
		String templatePomPath = TEMPLATE_POM_XML_FILE_PATH + templatePomName;
		try {
			super.writer(objectMap, templatePomPath, new File(pomFilePath));
		}
		catch (Exception exception) {
			throw new RuntimeException("创建 pom.xml 文件失败！", exception);
		}
	}

}
