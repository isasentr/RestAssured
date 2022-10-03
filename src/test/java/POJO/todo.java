package POJO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class todo {

  private int userId;
  private int id;
  private String unvan;
  private Boolean completed;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getunvan() {
        return unvan;
    }

@JsonProperty("title")
    public void setunvan(String title) {
        this.unvan = title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Location{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + unvan + '\'' +
                ", completed=" + completed +
                '}';
    }
}
