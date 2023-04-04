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
package io.github.panxiaochao.common.utils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * {@code EasyTree}
 * <p> 快速树代码生成工具
 *
 * @param <T>
 * @author Lypxc
 * @since 2022/1/27
 */
public class EasyTreeUtil<T> {

    private Object rootKey;

    private List<T> list;

    private String entityFieldId;

    private String entityFieldParentId;

    private String idKey = "id";

    private String dataKey = "data";

    private String parentIdKey = "parentId";

    private String childrenKey = "children";

    private String extendKeys;

    private int deep = 3;

    private boolean isCacheData = Boolean.FALSE;

    private boolean isNullChildrenAsEmpty = Boolean.FALSE;

    public EasyTreeUtil() {
    }

    /**
     * 循环数据
     *
     * @param list 数据
     * @return EasyTree<T>
     */
    public EasyTreeUtil<T> list(List<T> list) {
        this.list = list;
        return this;
    }

    /**
     * 设置父节点，可选
     *
     * @param rootKey 父key
     * @return EasyTree<T>
     */
    public EasyTreeUtil<T> rootKey(Object rootKey) {
        this.rootKey = rootKey;
        return this;
    }

    /**
     * 设置实体类的字段Id, 循环关系需要知道ID字段
     *
     * @param entityFieldId 实体类的字段Id名
     * @return EasyTree<T>
     */
    public EasyTreeUtil<T> entityFieldId(String entityFieldId) {
        this.entityFieldId = entityFieldId;
        return this;
    }

    /**
     * 设置实体类的字段父Id, 循环关系作用需要知道parentId字段
     *
     * @param entityFieldParentId 实体类的字段父Id名
     * @return EasyTree<T>
     */
    public EasyTreeUtil<T> entityFieldParentId(String entityFieldParentId) {
        this.entityFieldParentId = entityFieldParentId;
        return this;
    }


    /**
     * 设置输出idName别名
     *
     * @param idName 别名id的key
     * @return EasyTree<T>
     */
    public EasyTreeUtil<T> setAliasIdKeyName(String idName) {
        this.idKey = idName;
        return this;
    }

    /**
     * 设置输出parentIdName别名
     *
     * @param parentIdName 别名parentId的key
     * @return EasyTree<T>
     */
    public EasyTreeUtil<T> setAliasParentIdKeyName(String parentIdName) {
        this.parentIdKey = parentIdName;
        return this;
    }

    /**
     * 设置输出childrenName别名
     *
     * @param childrenName 别名childrenName的key
     * @return EasyTree<T>
     */
    public EasyTreeUtil<T> setAliasChildrenKeyName(String childrenName) {
        this.childrenKey = childrenName;
        return this;
    }

    /**
     * 扩展参数：可以自定义设置别名，根据不同的前端组件来适配
     * 比如Element UI，Ant Design等
     * 格式：{组件字段key: 自己实体类中字段, ...}
     *
     * @param extendKeys 扩展参数Json
     * @return EasyTree<T>
     */
    public EasyTreeUtil<T> extendKeys(String extendKeys) {
        this.extendKeys = extendKeys;
        return this;
    }

    /**
     * 是否缓存当前节点数据作为参数, 默认key是data
     *
     * @param isCacheData true or false
     * @return EasyTree<T>
     */
    public EasyTreeUtil<T> isCacheData(boolean isCacheData) {
        this.isCacheData = isCacheData;
        return this;
    }

    /**
     * 是否子节点没有数据的情况下，给一个默认空集
     *
     * @param isNullChildrenAsEmpty true or false
     * @return EasyTree<T>
     */
    public EasyTreeUtil<T> isNullChildrenAsEmpty(boolean isNullChildrenAsEmpty) {
        this.isNullChildrenAsEmpty = isNullChildrenAsEmpty;
        return this;
    }

    /**
     * 循环深度层次，默认3层
     *
     * @param deep 深度
     * @return EasyTree<T>
     */
    public EasyTreeUtil<T> deep(int deep) {
        this.deep = deep;
        return this;
    }

    /**
     * @return List
     */
    public List<Map<String, Object>> build() {
        if (list == null || isBlank(entityFieldId) || isBlank(entityFieldParentId)) {
            throw new IllegalArgumentException("list, entityFieldId, entityFieldParentId参数都不能为空！");
        }
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        if (rootKey == null || isBlank(rootKey.toString())) {
            // 取出第一条进行循环获取
            T data = list.get(0);
            rootKey = forRootNode(list, data);
        }
        // 1.获取根节点
        List<Map<String, Object>> treeNodeList = getRootNodeMaps(list, rootKey);
        // 2.循环获取子集
        int forDeep = 1;
        forChildren(treeNodeList, list, forDeep);
        return treeNodeList;
    }

    /**
     * @param list list
     * @param data data
     * @return Object
     */
    private Object forRootNode(List<T> list, T data) {
        T node = null;
        for (T t : list) {
            if (getFieldValue(t, entityFieldId).equals(getFieldValue(data, entityFieldParentId))) {
                node = t;
                break;
            }
        }
        if (node == null) {
            return getFieldValue(data, entityFieldParentId);
        } else {
            return forRootNode(list, node);
        }
    }


    /**
     * @param list    list
     * @param rootKey rootKey
     * @return List<Map < String, Object>>
     */
    private List<Map<String, Object>> getRootNodeMaps(List<T> list, Object rootKey) {
        return list.stream()
                .filter(data -> rootKey.equals(getFieldValue(data, entityFieldParentId)))
                .map(data -> {
                    Map<String, Object> rootNodeMap = new HashMap<>();
                    rootNodeMap.put(idKey, getFieldValue(data, entityFieldId));
                    rootNodeMap.put(parentIdKey, getFieldValue(data, entityFieldParentId));
                    if (isCacheData) {
                        rootNodeMap.put(dataKey, data);
                    }
                    if (!isBlank(extendKeys)) {
                        setExtendKeysMap(rootNodeMap, data);
                    }
                    return rootNodeMap;
                })
                .collect(Collectors.toList());
    }

    /**
     * @param treeNodeList treeNodeList
     */
    private void forChildren(List<Map<String, Object>> treeNodeList, List<T> list, int forDeep) {
        forDeep++;
        for (int i = 0; i < treeNodeList.size(); i++) {
            Map<String, Object> objectMap = treeNodeList.get(i);
            Object id = objectMap.get(idKey);
            if (forDeep > deep) {
                if (isNullChildrenAsEmpty) {
                    objectMap.put(childrenKey, new ArrayList<>());
                }
                break;
            }
            List<Map<String, Object>> tChildList = list.stream()
                    .filter(data -> id.equals(getFieldValue(data, entityFieldParentId)))
                    .map(data -> {
                        Map<String, Object> childTreeNode = new HashMap<>();
                        childTreeNode.put(idKey, getFieldValue(data, entityFieldId));
                        childTreeNode.put(parentIdKey, getFieldValue(data, entityFieldParentId));
                        if (isCacheData) {
                            childTreeNode.put(dataKey, data);
                        }
                        if (!isBlank(extendKeys)) {
                            setExtendKeysMap(childTreeNode, data);
                        }
                        return childTreeNode;
                    })
                    .collect(Collectors.toList());
            if (tChildList != null && !tChildList.isEmpty()) {
                objectMap.put(childrenKey, tChildList);
                forChildren(tChildList, list, forDeep);
            } else {
                if (isNullChildrenAsEmpty) {
                    objectMap.put(childrenKey, new ArrayList<>());
                }
            }
        }
    }

    /**
     * @param nodeMap nodeMap
     * @param data    data
     */
    private void setExtendKeysMap(Map<String, Object> nodeMap, T data) {
        extendKeys = extendKeys.trim();
        if (!extendKeys.startsWith("{") || !extendKeys.endsWith("}")) {
            throw new IllegalArgumentException("extendKeys参数格式不合法！");
        }
        Map<String, String> paramsMap = parseExtendKeys(extendKeys);
        for (String key : paramsMap.keySet()) {
            nodeMap.put(key, getFieldValue(data, paramsMap.get(key)));
        }
    }

    /**
     * @param extendKeys extendKeys
     * @return Map<String, String>
     */
    private Map<String, String> parseExtendKeys(String extendKeys) {
        String paramsJson = extendKeys
                .replace("{", "")
                .replace("}", "")
                .replaceAll("\"", "");
        Map<String, String> paramsJsonMap = new HashMap<>();
        String[] paramsJsons = paramsJson.split(",");
        Arrays.stream(paramsJsons).forEach(str -> {
            String[] keyValue = str.split(":");
            paramsJsonMap.put(keyValue[0].trim(), keyValue[1].trim());
        });
        return paramsJsonMap;
    }

    /**
     * Get field value object.
     *
     * @param obj       the obj
     * @param fieldName the field name
     * @return the object
     */
    public Object getFieldValue(final Object obj, final String fieldName) {
        if (Objects.isNull(obj) || isBlank(fieldName)) {
            return null;
        }
        return getFieldValue(obj, getField(obj.getClass(), fieldName));
    }

    /**
     * Get field.
     *
     * @param beanClass the bean class
     * @param fieldName the name
     * @return the field
     * @throws SecurityException the security exception
     */
    public Field getField(final Class<?> beanClass, final String fieldName) throws SecurityException {
        Field[] fields = beanClass.getDeclaredFields();
        Field field = Arrays.stream(fields).filter(s -> Objects.equals(fieldName, s.getName()))
                .findFirst().orElse(null);
        if (Objects.isNull(field)) {
            // 判断是否有继承
            Class<?> superClassCls = beanClass.getSuperclass();
            if (Objects.nonNull(superClassCls)) {
                fields = superClassCls.getDeclaredFields();
                field = Arrays.stream(fields).filter(s -> Objects.equals(fieldName, s.getName()))
                        .findFirst().orElse(null);
            }
        }
        return field;
    }

    /**
     * Gets field value.
     *
     * @param obj   the obj
     * @param field the field
     * @return the field value
     */
    public Object getFieldValue(final Object obj, final Field field) {
        if (Objects.isNull(obj) || Objects.isNull(field)) {
            return null;
        }
        try {
            field.setAccessible(true);
            return field.get(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("获取属性值" + field.getName() + "失败，请检查对应实体类字段名！");
        }
    }

    /**
     * <p>Checks if a CharSequence is empty (""), null or whitespace only.</p>
     *
     * <pre>
     * isBlank(null)      = true
     * isBlank("")        = true
     * isBlank(" ")       = true
     * isBlank("bob")     = false
     * isBlank("  bob  ") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace only
     */
    private boolean isBlank(final CharSequence cs) {
        final int strLen = length(cs);
        if (strLen == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets a CharSequence length
     *
     * @param cs a CharSequence
     * @return CharSequence length
     */
    private int length(final CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }
}
