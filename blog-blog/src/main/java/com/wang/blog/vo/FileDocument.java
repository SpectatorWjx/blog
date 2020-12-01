package com.wang.blog.vo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author wjx
 * @date 2019/08/13
 */
@Document
@Data
public class FileDocument {

    @Id
    private String id;
    private String name;
    private Long size;
    private Date uploadDate;
    private String md5;
    private byte[] content;
    private String contentType;
    private String suffix;
    private String description;
    private String gridfsId;

    public FileDocument() {}

    public FileDocument(String id, String name, Long size, Date uploadDate, String md5, byte[] content, String contentType, String suffix, String description, String gridfsId) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.uploadDate = uploadDate;
        this.md5 = md5;
        this.content = content;
        this.contentType = contentType;
        this.suffix = suffix;
        this.description = description;
        this.gridfsId = gridfsId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        FileDocument fileInfo = (FileDocument) object;
        return java.util.Objects.equals(size, fileInfo.size)
                && java.util.Objects.equals(name, fileInfo.name)
                && java.util.Objects.equals(description, fileInfo.description)
                && java.util.Objects.equals(uploadDate, fileInfo.uploadDate)
                && java.util.Objects.equals(md5, fileInfo.md5)
                && java.util.Objects.equals(id, fileInfo.id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, description, size, uploadDate, md5, id);
    }

    @Override
    public String toString() {
        return "FileDocument{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", uploadDate=" + uploadDate +
                ", md5='" + md5 + '\'' +
                ", content=" + content +
                ", contentType='" + contentType + '\'' +
                ", suffix='" + suffix + '\'' +
                ", description='" + description + '\'' +
                ", gridfsId='" + gridfsId + '\'' +
                '}';
    }
}
