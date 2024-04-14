package spring.oldboy.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "chat")
public class Chat implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    /*
    Формируем связь между User и Chat (мы не стали организовывать связь Многие-ко-Многим - ManyToMany),
    делаем это через промежуточную (связующую) таблицу 'users_chat' см. DOC/SqlScripts/CreateTable.sql.

    И так, один Chat будет содержать нескольких User-ов. Мы ушли от связи ManyToMany через связующую
    таблицу 'users_chat', в которой каждая запись дает соответствие Chat-a к User-у (или наоборот).
    Мы создали отдельную сущность UserChat (для работы с таблицей 'users_chat') которая и будет хранить
    данные этого соответствия (соответственно, сущности UserChat-ов хранят множество соответствий
    Chat-ов к User-ам).

    Как и в сущности User, тут мы реализуем связь OneToMany - один Chat может быть связан с множеством
    записей в таблице 'users_chat' или с множеством сущностей UserChat, которые хранят взаимосвязь
    User-ов и Chat-ов.

    Наша сущность Chat будет хранить список этих взаимосвязей или коллекцию сущностей UserChat.

    Естественно в сущности UserChat будет поле хранящее данные о Chat-е и оно будет помечено как
    @ManyToOne, т.е. обратной к текущей аннотацией. И конечно, имя этого поля будет - 'chat', что
    и указано в параметре картирования - mappedBy = "chat"

    Точно такая же ситуация будет и в сущности User см. User.java
    */
    @Builder.Default
    @OneToMany(mappedBy = "chat")
    private List<UserChat> userChats = new ArrayList<>();
}
