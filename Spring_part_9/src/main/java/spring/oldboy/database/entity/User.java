package spring.oldboy.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private LocalDate birthDate;

    private String firstname;

    private String lastname;

    @Enumerated(EnumType.STRING)
    private Role role;

    /*
    Настраиваем связь Многие-к-Одному, т.е. много юзеров может быть связанно с одной
    компанией, задаем ленивый механизм чтения - по требованию (N+1), задаем по какому
    полю таблицы 'company' идет связь (если не указывать, Hibernate сделает это сам,
    взяв имя в нижнем регистре и добавив _id). Для наглядности лучше именовать самому.
    */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    /*
    В данной ситуации один User может участвовать в нескольких Chat-ах, однако мы решили
    уйти от связи ManyToMany, т.к. и один Chat будет содержать множество User-ов. Мы решили
    оптимизировать данную ситуацию через связующую таблицу 'users_chat', в которой каждая
    запись дает соответствие пользователя к чату (или наоборот). И у нас для работы с данной
    таблицей создана отдельная сущность UserChat которая и будет хранить данные этого
    соответствия (а если точнее сущности, т.е. множество UserChat-ов).

    Вот тут мы и реализуем связь OneToMany - один User может быть связан с множеством записей
    в таблице 'users_chat' или с множеством сущностей UserChat, которые хранят взаимосвязь
    User-ов и Chat-ов, а сама сущность User будет хранить список этих взаимосвязей или
    коллекцию сущностей UserChat.

    Естественно в сущности UserChat будет поле хранящее данные о User-е и оно будет помечено как
    @ManyToOne, т.е. обратной к текущей аннотацией. И конечно, имя этого поля будет - 'user', что
    и указано в параметре картирования - mappedBy = "user"

    Точно такая же ситуация будет и в сущности Chat см. Chat.java
    */
    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<UserChat> userChats = new ArrayList<>();
}
