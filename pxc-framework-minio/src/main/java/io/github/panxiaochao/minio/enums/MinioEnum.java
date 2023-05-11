package io.github.panxiaochao.minio.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * {@code MinioEnum}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-05-11
 */
@Getter
@RequiredArgsConstructor
public enum MinioEnum {
    /**
     * Aac mime type enum.
     */
    AAC("acc", "AAC音频", "audio/aac"),

    /**
     * Abw mime type enum.
     */
    ABW("abw", "AbiWord文件", "application/x-abiword"),

    /**
     * Arc mime type enum.
     */
    ARC("arc", "存档文件", "application/x-freearc"),

    /**
     * Avi mime type enum.
     */
    AVI("avi", "音频视频交错格式", "video/x-msvideo"),

    /**
     * Azw mime type enum.
     */
    AZW("azw", "亚马逊Kindle电子书格式", "application/vnd.amazon.ebook"),

    /**
     * Bin mime type enum.
     */
    BIN("bin", "任何类型的二进制数据", "application/octet-stream"),

    /**
     * The Bmp.
     */
    BMP("bmp", "Windows OS / 2位图图形", "image/bmp"),

    /**
     * Bz mime type enum.
     */
    BZ("bz", "BZip存档", "application/x-bzip"),

    /**
     * Bz 2 mime type enum.
     */
    BZ2("bz2", "BZip2存档", "application/x-bzip2"),

    /**
     * Csh mime type enum.
     */
    CSH("csh", "C-Shell脚本", "application/x-csh"),

    /**
     * Css mime type enum.
     */
    CSS("css", "级联样式表（CSS）", "text/css"),

    /**
     * Csv mime type enum.
     */
    CSV("csv", "逗号分隔值（CSV）", "text/csv"),

    /**
     * Doc mime type enum.
     */
    DOC("doc", "微软Word文件", "application/msword"),

    /**
     * The Docx.
     */
    DOCX("docx", "Microsoft Word（OpenXML）", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),

    /**
     * The Eot.
     */
    EOT("eot", "MS Embedded OpenType字体", "application/vnd.ms-fontobject"),

    /**
     * Epub mime type enum.
     */
    EPUB("epub", "电子出版物（EPUB）", "application/epub+zip"),

    /**
     * Gz mime type enum.
     */
    GZ("gz", "GZip压缩档案", "application/gzip"),

    /**
     * Gif mime type enum.
     */
    GIF("gif", "图形交换格式（GIF）", "image/gif"),

    /**
     * Htm mime type enum.
     */
    HTM("htm", "超文本标记语言（HTML）", "text/html"),

    /**
     * Html mime type enum.
     */
    HTML("html", "超文本标记语言（HTML）", "text/html"),

    /**
     * Ico mime type enum.
     */
    ICO("ico", "图标格式", "image/vnd.microsoft.icon"),

    /**
     * Ics mime type enum.
     */
    ICS("ics", "iCalendar格式", "text/calendar"),

    /**
     * Jar mime type enum.
     */
    JAR("jar", "Java存档", "application/java-archive"),

    /**
     * Jpeg mime type enum.
     */
    JPEG("jpeg", "JPEG图像", "image/jpeg"),

    /**
     * Jpg mime type enum.
     */
    JPG("jpg", "JPEG图像", "image/jpeg"),

    /**
     * Js mime type enum.
     */
    JS("js", "JavaScript", "text/javascript"),

    /**
     * Json mime type enum.
     */
    JSON("json", "JSON格式", "application/json"),

    /**
     * Jsonld mime type enum.
     */
    JSONLD("jsonld", "JSON-LD格式", "application/ld+json"),

    /**
     * Mid mime type enum.
     */
    MID("mid", "乐器数字接口（MIDI）", "audio/midi"),

    /**
     * Midi mime type enum.
     */
    MIDI("midi", "乐器数字接口（MIDI）", "audio/midi"),

    /**
     * Mjs mime type enum.
     */
    MJS("mjs", "JavaScript模块", "text/javascript"),

    /**
     * Mp 3 mime type enum.
     */
    MP3("mp3", "MP3音频", "audio/mpeg"),

    /**
     * Mp 4 mime type enum.
     */
    MP4("mp4", "MP4视频", "video/mp4"),

    /**
     * Mpeg mime type enum.
     */
    MPEG("mpeg", "MPEG视频", "video/mpeg"),

    /**
     * Mpkg mime type enum.
     */
    MPKG("mpkg", "苹果安装程序包", "application/vnd.apple.installer+xml"),

    /**
     * Odp mime type enum.
     */
    ODP("odp", "OpenDocument演示文稿文档", "application/vnd.oasis.opendocument.presentation"),

    /**
     * Ods mime type enum.
     */
    ODS("ods", "OpenDocument电子表格文档", "application/vnd.oasis.opendocument.spreadsheet"),

    /**
     * Odt mime type enum.
     */
    ODT("odt", "OpenDocument文字文件", "application/vnd.oasis.opendocument.text"),

    /**
     * Oga mime type enum.
     */
    OGA("oga", "OGG音讯", "audio/ogg"),

    /**
     * Ogv mime type enum.
     */
    OGV("ogv", "OGG视频", "video/ogg"),

    /**
     * Ogx mime type enum.
     */
    OGX("ogx", "OGG", "application/ogg"),

    /**
     * Opus mime type enum.
     */
    OPUS("opus", "OPUS音频", "audio/opus"),

    /**
     * Otf mime type enum.
     */
    OTF("otf", "otf字体", "font/otf"),

    /**
     * Png mime type enum.
     */
    PNG("png", "便携式网络图形", "image/png"),

    /**
     * Pdf mime type enum.
     */
    PDF("pdf", "Adobe 可移植文档格式（PDF）", "application/pdf"),

    /**
     * Php mime type enum.
     */
    PHP("php", "php", "application/x-httpd-php"),

    /**
     * The Ppt.
     */
    PPT("ppt", "Microsoft PowerPoint", "application/vnd.ms-powerpoint"),

    /**
     * The Pptx.
     */
    PPTX("pptx", "Microsoft PowerPoint（OpenXML）", "application/vnd.openxmlformats-officedocument.presentationml.presentation"),

    /**
     * Rar mime type enum.
     */
    RAR("rar", "RAR档案", "application/vnd.rar"),

    /**
     * Rtf mime type enum.
     */
    RTF("rtf", "富文本格式", "application/rtf"),

    /**
     * The Sh.
     */
    SH("sh", "Bourne Shell脚本", "application/x-sh"),

    /**
     * Svg mime type enum.
     */
    SVG("svg", "可缩放矢量图形（SVG）", "image/svg+xml"),

    /**
     * The Swf.
     */
    SWF("swf", "小型Web格式（SWF）或Adobe Flash文档", "application/x-shockwave-flash"),

    /**
     * Tar mime type enum.
     */
    TAR("tar", "磁带存档（TAR）", "application/x-tar"),

    /**
     * Tif mime type enum.
     */
    TIF("tif", "标记图像文件格式（TIFF）", "image/tiff"),

    /**
     * Tiff mime type enum.
     */
    TIFF("tiff", "标记图像文件格式（TIFF）", "image/tiff"),

    /**
     * Ts mime type enum.
     */
    TS("ts", "MPEG传输流", "video/mp2t"),

    /**
     * Ttf mime type enum.
     */
    TTF("ttf", "ttf字体", "font/ttf"),

    /**
     * The Txt.
     */
    TXT("txt", "文本（通常为ASCII或ISO 8859- n", "text/plain"),

    /**
     * Vsd mime type enum.
     */
    VSD("vsd", "微软Visio", "application/vnd.visio"),

    /**
     * Wav mime type enum.
     */
    WAV("wav", "波形音频格式", "audio/wav"),

    /**
     * Weba mime type enum.
     */
    WEBA("weba", "WEBM音频", "audio/webm"),

    /**
     * Webm mime type enum.
     */
    WEBM("webm", "WEBM视频", "video/webm"),

    /**
     * Webp mime type enum.
     */
    WEBP("webp", "WEBP图像", "image/webp"),

    /**
     * Woff mime type enum.
     */
    WOFF("woff", "Web开放字体格式（WOFF）", "font/woff"),

    /**
     * Woff 2 mime type enum.
     */
    WOFF2("woff2", "Web开放字体格式（WOFF）", "font/woff2"),

    /**
     * Xhtml mime type enum.
     */
    XHTML("xhtml", "XHTML", "application/xhtml+xml"),

    /**
     * Xls mime type enum.
     */
    XLS("xls", "微软Excel", "application/vnd.ms-excel"),

    /**
     * Xlsx mime type enum.
     */
    XLSX("xlsx", "微软Excel（OpenXML）", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),

    /**
     * Xml mime type enum.
     */
    XML("xml", "XML", "application/xml"),

    /**
     * Xul mime type enum.
     */
    XUL("xul", "XUL", "application/vnd.mozilla.xul+xml"),

    /**
     * Zip mime type enum.
     */
    ZIP("zip", "ZIP", "application/zip"),

    MIME_3GP("3gp", "3GPP audio/video container", "video/3gpp"),

    MIME_3GP_WITHOUT_VIDEO("3gp", "3GPP audio/video container doesn't contain video", "audio/3gpp2"),

    MIME_3G2("3g2", "3GPP2 audio/video container", "video/3gpp2"),

    MIME_3G2_WITHOUT_VIDEO("3g2", "3GPP2 audio/video container  doesn't contain video", "audio/3gpp2"),

    MIME_7Z("7z", "7-zip存档", "application/x-7z-compressed");

    /**
     * 扩展名
     */
    private final String extension;
    /**
     * 说明
     */
    private final String explain;
    /**
     * contentType/mime类型
     */
    private final String mimeType;

    /**
     * 通过扩展名获取枚举类型
     *
     * @param extension 扩展名
     * @return 枚举类 by extension
     */
    public static MinioEnum getByExtension(String extension) {
        if (extension == null || extension.length() == 0) {
            return null;
        }
        for (MinioEnum typesEnum : MinioEnum.values()) {
            if (extension.equals(typesEnum.getExtension())) {
                return typesEnum;
            }
        }
        return null;
    }

    /**
     * 通过扩展名获取枚举类型
     *
     * @param fileName 文件名
     * @return 枚举类 by file name
     */
    public static String getByFileName(String fileName) {
        if (fileName != null || fileName.length() == 0) {
            return null;
        }
        for (MinioEnum typesEnum : MinioEnum.values()) {
            if (typesEnum.getMimeType().equals(
                    fileName.substring(fileName.lastIndexOf(".")))) {
                return typesEnum.getMimeType();
            }
        }
        return null;
    }

    /**
     * Content-Type常用对照
     * 根据后缀获取Mime
     *
     * @param fileType 扩展名
     * @return mime类型 content type
     */
    public static String getContentType(String fileType) {
        MinioEnum mimeTypeEnum = getByExtension(fileType);
        if (mimeTypeEnum != null) {
            return mimeTypeEnum.getMimeType();
        }
        return "application/octet-stream";
    }

    /**
     * 通过文件类型进行获取后缀
     *
     * @param fileType 文件类型例如:image/png
     * @return file suffix
     */
    public static String suffix(String fileType) {
        MinioEnum[] values = MinioEnum.values();
        for (MinioEnum value : values) {
            if (value.getMimeType().equals(fileType)) {
                return value.getExtension();
            }
        }
        throw new IllegalArgumentException(fileType + "文件类型出错！");
    }
}
