package com.example.taskmaster;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
//@RequiredArgsConstructor
public class TasksOrg {

    @PrimaryKey(autoGenerate = true)
    public Long id;


    @ColumnInfo(name="data_title")
    public String title;
    @ColumnInfo(name="data_body")
    public String body;
    @ColumnInfo(name="data_state")
    public String state;


    public TasksOrg(String title, String body, String state) {
        this.title = title;
        this.body = body;
        this.state = state;
    }


}
