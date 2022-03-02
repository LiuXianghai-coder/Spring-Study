package org.xhliu.mybatis.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author xhliu
 * @time 2022-03-02-下午9:07
 */
public class Message implements Serializable {
    private Long id;
    private String msgId;
    private Integer status; // 消息状态，-1-待发送，0-发送中，1-发送失败 2-已发送
    private String content; // 消息内容
    private Integer deleted;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private MessageDetail messageDetail;

    public MessageDetail getMessageDetail() {
        return messageDetail;
    }

    public void setMessageDetail(MessageDetail messageDetail) {
        this.messageDetail = messageDetail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", msgId='" + msgId + '\'' +
                ", status=" + status +
                ", content='" + content + '\'' +
                ", deleted=" + deleted +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }


    public static final class Builder {
        private Long id;
        private String msgId;
        private Integer status; // 消息状态，-1-待发送，0-发送中，1-发送失败 2-已发送
        private String content; // 消息内容
        private Integer deleted;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder msgId(String msgId) {
            this.msgId = msgId;
            return this;
        }

        public Builder status(Integer status) {
            this.status = status;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder deleted(Integer deleted) {
            this.deleted = deleted;
            return this;
        }

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder updateTime(LocalDateTime updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public Message build() {
            Message message = new Message();
            message.setId(id);
            message.setMsgId(msgId);
            message.setStatus(status);
            message.setContent(content);
            message.setDeleted(deleted);
            message.setCreateTime(createTime);
            message.setUpdateTime(updateTime);
            return message;
        }
    }
}
