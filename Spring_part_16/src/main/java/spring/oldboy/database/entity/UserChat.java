package spring.oldboy.database.entity;

/*
Связующая сущность для User и Chat, пытаемся оптимизировать связь ManyToMany,
через отдельную таблицу и связи OneToMany и ManyToOne. Т.е. у нас будет сущность,
которая при обращении к таблице 'users_chat' получит данные записи под нужным ID.
И сохранит в себе user_id (или данные о некой сущности User) и chat_id (или данные
о некой сущности Chat), которые между собой связаны - юзер участвует в чате (или
чат содержит данного юзера).
*/

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users_chat")
public class UserChat implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    Ситуация, в которой много User-ов связанны с множеством Chat-ов и наоборот, естественна.
    Но мы сделали связующую таблицу в которой каждая запись будет иметь только одно соответствие
    User-a и Chat-a зато таких записей будет много и мы всегда сможем сделать выборку, либо по
    User-у (ам), либо по Chat-у (ам) и получить нужный нам список.

    И так множество User-ов относятся к списку (имеют с ней связь) UserChat, как ManyToOne, еще
    раз, User-ов много, а 'список соответствия' один. С чатами ситуация абсолютно такая же см.
    ниже.
    */
    @ManyToOne(fetch = FetchType.LAZY)
    /* Связующее поле таблицы 'users_chat' для сущности User - user_id */
    @JoinColumn(name = "user_id")
    private User user;

    /*
    Как описано выше, список соответствия User-ов и Chat-ов один, а Chat-ов много отсюда связь -
    ManyToOne и связь настроена, как 'ленивая' (оптимизируем N+1). Поле в таблице для связи с
    сущностью Chat - chat_id.
    */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    /*
    Поскольку мы используем в сущности User связь OneToMany, т.е. для одной сущности User в
    таблице 'users_chat' может находиться несколько записей, посему в сущности User реализован
    список List<UserChat> этих соответствий, а сеттер, приведенный ниже, нужен, чтобы вносить
    нового User-a в этот список.
    */
    public void setUser(User user) {
        this.user = user;
        this.user.getUserChats().add(this);
    }
    /* Все что описано выше, для сущности User, справедливо и для сущности Chat */
    public void setChat(Chat chat) {
        this.chat = chat;
        this.chat.getUserChats().add(this);
    }
}
