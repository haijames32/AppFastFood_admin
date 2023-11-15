package hainb21127.poly.appfastfood_admin.DTO;

import java.util.Date;

public class MessageChat {
    public String id, sendId, receivedid, content, time, status;

    public MessageChat() {
    }

    public MessageChat(String sendId, String receivedid, String content, String time) {
        this.sendId = sendId;
        this.receivedid = receivedid;
        this.content = content;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getReceivedid() {
        return receivedid;
    }

    public void setReceivedid(String receivedid) {
        this.receivedid = receivedid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
