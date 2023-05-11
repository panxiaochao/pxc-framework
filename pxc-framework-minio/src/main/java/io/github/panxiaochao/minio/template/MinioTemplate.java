package io.github.panxiaochao.minio.template;

import io.github.panxiaochao.minio.properties.MinioProperties;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * {@code MinioTemplate}
 * <p> description: MinioTemplate 模版辅助类
 *
 * @author Lypxc
 * @since 2023-05-11
 */
public class MinioTemplate {

    private final MinioProperties minioProperties;

    private final MinioClient minioClient;

    /**
     * 桶名
     */
    private final String bucketName;

    /**
     * 初始化MinioClient
     *
     * @param minioProperties MinioProperties
     */
    public MinioTemplate(MinioProperties minioProperties) {
        this.minioProperties = minioProperties;
        this.bucketName = minioProperties.getBucket();
        this.minioClient = MinioClient.builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }

    // ---------------------------- 下面是方法 ----------------------------

    /**
     * 创建存储桶
     */
    public void makeBucket() {
        makeBucket(this.bucketName);
    }

    /**
     * 创建存储桶
     *
     * @param bucketName 桶名
     */
    public void makeBucket(String bucketName) {
        Assert.hasText(bucketName, "properties bucketName is required");
        try {
            if (!bucketExists(bucketName)) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            throw new RuntimeException("minio makeBucket is error!", e);
        }
    }

    /**
     * 删除存储桶
     *
     * @param bucketName 桶名
     */
    public void deleteBucket(String bucketName) {
        try {
            if (bucketExists(bucketName)) {
                minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            throw new RuntimeException("minio deleteBucket is error!", e);
        }
    }

    /**
     * 查询桶名是否存在
     *
     * @param bucketName 桶名
     * @return boolean
     */
    public boolean bucketExists(String bucketName) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            throw new RuntimeException("minio bucketExists is error!", e);
        }
    }

    /**
     * 列出所有的桶
     *
     * @return 返回数组
     */
    public List<String> listBuckets() {
        try {
            List<Bucket> list = minioClient.listBuckets();
            if (!CollectionUtils.isEmpty(list)) {
                return list.stream().map(Bucket::name).collect(Collectors.toList());
            }
            return Collections.emptyList();
        } catch (Exception e) {
            throw new RuntimeException("minio listBuckets is error!", e);
        }
    }

    /**
     * 获取存储桶下的多有文件和目录
     *
     * @param bucketName 桶名
     * @return 数组项
     */
    public List<Item> listFiles(String bucketName) {
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).recursive(true).build());
        if (null == results || (null == results.iterator() || false == results.iterator().hasNext())) {
            return Collections.emptyList();
        }
        final List<Item> list = new ArrayList<>();
        results.forEach(r -> {
            try {
                list.add(r.get());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return list;
    }

    /**
     * 上传文件，若文件名相同则覆盖
     *
     * @param inputStream 文件流
     * @param objectName  文件名
     */
    public void putObject(InputStream inputStream, String objectName) {
        putObject(inputStream, this.bucketName, objectName);
    }

    /**
     * 上传文件，若文件名相同则覆盖
     *
     * @param inputStream 文件流
     * @param bucketName  桶名
     * @param objectName  文件名
     */
    public void putObject(InputStream inputStream, String bucketName, String objectName) {
        Assert.hasText(bucketName, "properties bucketName is required");
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            // 使用 10MB 作为分段大小
                            .stream(inputStream, -1, this.minioProperties.getPartSize())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("minio putObject is error!", e);
        }
    }

    /**
     * 获取对象下载流
     *
     * @param objectName 文件名
     * @return 返回下载流
     */
    public InputStream getObjectInputStream(String objectName) {
        return getObjectInputStream(this.bucketName, objectName);
    }

    /**
     * 获取对象下载流
     *
     * @param bucketName 桶名
     * @param objectName 文件名
     * @return 返回下载流
     */
    public InputStream getObjectInputStream(String bucketName, String objectName) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("minio getObject is error!", e);
        }
    }

    /**
     * 将对象的数据下载到本地文件中
     *
     * @param objectName 文件名称
     * @param fileName   本地文件全限定名（包含本地存储路径）
     */
    public void downloadObject(String objectName, String fileName) {
        downloadObject(this.bucketName, objectName, fileName);
    }

    /**
     * 将对象的数据下载到本地文件中
     *
     * @param bucketName 存储桶名称
     * @param objectName 文件名称
     * @param fileName   本地文件全限定名（包含本地存储路径）
     */
    public void downloadObject(String bucketName, String objectName, String fileName) {
        try {
            DownloadObjectArgs downloadObjectArgs = DownloadObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .filename(fileName)
                    .build();
            minioClient.downloadObject(downloadObjectArgs);
        } catch (Exception e) {
            throw new RuntimeException("minio downloadObject is error!", e);
        }
    }

    /**
     * 删除一个对象
     */
    public void removeObject(String objectName) {
        removeObject(this.bucketName, objectName);
    }

    /**
     * 删除一个对象
     */
    public void removeObject(String bucketName, String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            throw new RuntimeException("minio deleteObject is error!", e);
        }
    }

    /**
     * 生成一个给HTTP请求带签名的URL, 指定了到期时间和自定义请求参数, 浏览器/移动端的客户端可以用这个URL进行下载, 设置一个失效时间，默认值是7天
     *
     * @param objectName 文件名称
     * @param expiry     失效时间(单位分钟)
     * @return 请求带签名的URL
     */
    public String getPresignedObjectUrl(String objectName, Integer expiry) {
        return getPresignedObjectUrl(this.bucketName, objectName, expiry);
    }

    /**
     * 生成一个给HTTP请求带签名的URL, 指定了到期时间和自定义请求参数, 浏览器/移动端的客户端可以用这个URL进行下载, 设置一个失效时间，默认值是7天
     *
     * @param bucketName 存储桶名称
     * @param objectName 文件名称
     * @param expiry     失效时间(单位分钟)
     * @return 请求带签名的URL
     */
    public String getPresignedObjectUrl(String bucketName, String objectName, Integer expiry) {
        try {
            GetPresignedObjectUrlArgs build = GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(objectName)
                    .expiry(expiry, TimeUnit.MINUTES)
                    .build();
            return minioClient.getPresignedObjectUrl(build);
        } catch (Exception e) {
            throw new RuntimeException("minio getPresignedObjectUrl is error!", e);
        }
    }

    /**
     * 获取对象的元数据
     *
     * @param objectName 文件名
     * @return 返回存储对象
     */
    public StatObjectResponse statObject(String objectName) {
        return statObject(this.bucketName, objectName);
    }

    /**
     * 获取对象的元数据
     *
     * @param bucketName 存储桶名称
     * @param objectName 文件名
     * @return 返回存储对象
     */
    public StatObjectResponse statObject(String bucketName, String objectName) {
        try {
            StatObjectArgs build = StatObjectArgs.builder().bucket(bucketName).object(objectName).build();
            return minioClient.statObject(build);
        } catch (Exception e) {
            throw new RuntimeException("minio statObject is error!", e);
        }
    }
}
