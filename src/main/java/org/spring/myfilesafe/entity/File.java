package org.spring.myfilesafe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "files")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String filePath;
    private String fileName;

    @ToString.Exclude
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "rights_to_view", joinColumns = @JoinColumn(name = "file_id"))
    @Column(name = "username")
    private List<String> usersForLooking;

    @ToString.Exclude
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "editing_rights", joinColumns = @JoinColumn(name = "file_id"))
    @Column(name = "username")
    private List<String> usersForEditing;
    // дата создания файла и ....последнего редактирования, UserName кто его редактировал, история редактирования-объект история редактирования
    //хранить копии файла при каждом изменении
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
