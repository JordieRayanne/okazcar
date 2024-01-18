package com.okazcar.okazcar.models.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleFile {
    private int articleId;
    private List<UserFile> userFiles = new ArrayList<>();
    private int totalCount = 1;
    public void addNewUserFile(UserFile newUserFile) {
        if (this.userFiles.isEmpty()) {
            this.userFiles.add(newUserFile);
            this.totalCount++;
            return ;
        }
        for (UserFile userFile: this.userFiles) {
            if (userFile.getUserId().trim().equals(newUserFile.getUserId().trim())) {
                return ;
            }
        }
        this.totalCount++;
        this.userFiles.add(newUserFile);
    }

    public void addNewUserFile(List<UserFile> newUserFile) {
        for (UserFile userFile: newUserFile) {
            addNewUserFile(userFile);
        }
    }
}
