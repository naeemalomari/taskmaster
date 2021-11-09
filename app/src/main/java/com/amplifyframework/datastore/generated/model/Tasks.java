package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Tasks type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Tasks")
@Index(name = "byTasks", fields = {"taskID","name"})
public final class Tasks implements Model {
  public static final QueryField ID = field("Tasks", "id");
  public static final QueryField TITLE = field("Tasks", "title");
  public static final QueryField BODY = field("Tasks", "body");
  public static final QueryField STATE = field("Tasks", "state");
  public static final QueryField TASK_ID = field("Tasks", "taskID");
  public static final QueryField NAME = field("Tasks", "name");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String title;
  private final @ModelField(targetType="String", isRequired = true) String body;
  private final @ModelField(targetType="String", isRequired = true) String state;
  private final @ModelField(targetType="ID", isRequired = true) String taskID;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getTitle() {
      return title;
  }
  
  public String getBody() {
      return body;
  }
  
  public String getState() {
      return state;
  }
  
  public String getTaskId() {
      return taskID;
  }
  
  public String getName() {
      return name;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Tasks(String id, String title, String body, String state, String taskID, String name) {
    this.id = id;
    this.title = title;
    this.body = body;
    this.state = state;
    this.taskID = taskID;
    this.name = name;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Tasks tasks = (Tasks) obj;
      return ObjectsCompat.equals(getId(), tasks.getId()) &&
              ObjectsCompat.equals(getTitle(), tasks.getTitle()) &&
              ObjectsCompat.equals(getBody(), tasks.getBody()) &&
              ObjectsCompat.equals(getState(), tasks.getState()) &&
              ObjectsCompat.equals(getTaskId(), tasks.getTaskId()) &&
              ObjectsCompat.equals(getName(), tasks.getName()) &&
              ObjectsCompat.equals(getCreatedAt(), tasks.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), tasks.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTitle())
      .append(getBody())
      .append(getState())
      .append(getTaskId())
      .append(getName())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Tasks {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("title=" + String.valueOf(getTitle()) + ", ")
      .append("body=" + String.valueOf(getBody()) + ", ")
      .append("state=" + String.valueOf(getState()) + ", ")
      .append("taskID=" + String.valueOf(getTaskId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static TitleStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Tasks justId(String id) {
    return new Tasks(
      id,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      title,
      body,
      state,
      taskID,
      name);
  }
  public interface TitleStep {
    BodyStep title(String title);
  }
  

  public interface BodyStep {
    StateStep body(String body);
  }
  

  public interface StateStep {
    TaskIdStep state(String state);
  }
  

  public interface TaskIdStep {
    NameStep taskId(String taskId);
  }
  

  public interface NameStep {
    BuildStep name(String name);
  }
  

  public interface BuildStep {
    Tasks build();
    BuildStep id(String id);
  }
  

  public static class Builder implements TitleStep, BodyStep, StateStep, TaskIdStep, NameStep, BuildStep {
    private String id;
    private String title;
    private String body;
    private String state;
    private String taskID;
    private String name;
    @Override
     public Tasks build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Tasks(
          id,
          title,
          body,
          state,
          taskID,
          name);
    }
    
    @Override
     public BodyStep title(String title) {
        Objects.requireNonNull(title);
        this.title = title;
        return this;
    }
    
    @Override
     public StateStep body(String body) {
        Objects.requireNonNull(body);
        this.body = body;
        return this;
    }
    
    @Override
     public TaskIdStep state(String state) {
        Objects.requireNonNull(state);
        this.state = state;
        return this;
    }
    
    @Override
     public NameStep taskId(String taskId) {
        Objects.requireNonNull(taskId);
        this.taskID = taskId;
        return this;
    }
    
    @Override
     public BuildStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String title, String body, String state, String taskId, String name) {
      super.id(id);
      super.title(title)
        .body(body)
        .state(state)
        .taskId(taskId)
        .name(name);
    }
    
    @Override
     public CopyOfBuilder title(String title) {
      return (CopyOfBuilder) super.title(title);
    }
    
    @Override
     public CopyOfBuilder body(String body) {
      return (CopyOfBuilder) super.body(body);
    }
    
    @Override
     public CopyOfBuilder state(String state) {
      return (CopyOfBuilder) super.state(state);
    }
    
    @Override
     public CopyOfBuilder taskId(String taskId) {
      return (CopyOfBuilder) super.taskId(taskId);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
  }
  
}
