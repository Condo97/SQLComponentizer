package sqlcomponentizer.tableobjects;

import sqlcomponentizer.dbserializer.DBColumn;
import sqlcomponentizer.dbserializer.DBSerializable;

import java.time.LocalDateTime;

@DBSerializable(tableName = "Chat")
public class Chat {

    @DBColumn(name = "user_id")
    private Integer userID;

    @DBColumn(name = "chat_id")
    private Integer chatID;

    @DBColumn(name = "user_text")
    private String userText;

    @DBColumn(name = "ai_text")
    private String aiText;

    @DBColumn(name = "finish_reason")
    private String finishReason;

    @DBColumn(name = "date")
    private LocalDateTime date;

    public Chat(Integer userID, String userText, LocalDateTime date) {
        this.userID = userID;
        this.userText = userText;
        this.date = date;

        this.chatID = null;
        this.aiText = null;
        this.finishReason = null;
    }

    public Chat(int chatID, Integer userID, String userText, String aiText, String finishReason, LocalDateTime date) {
        this.chatID = chatID;
        this.userID = userID;
        this.userText = userText;
        this.aiText = aiText;
        this.finishReason = finishReason;
        this.date = date;
    }

    public Integer getChatID() {
        return chatID;
    }

    public Integer getUserID() {
        return userID;
    }

    public String getUserText() {
        return userText;
    }

    public String getAiText() {
        return aiText;
    }

    public String getFinishReason() {
        return finishReason;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
    }

    public void setAiText(String aiText) {
        this.aiText = aiText;
    }

    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
    }
}
