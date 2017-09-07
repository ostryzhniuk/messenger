package andrii.entities;

import javax.persistence.*;

@Entity
@Table(name = "user_friendship")
public class UserFriendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "friendship_id", nullable = false)
    private Friendship friendship;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public enum Condition {
        FRIENDS, SUBSCRIBER, NOT_REVIEWED, REJECTED
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Condition condition;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Friendship getFriendship() {
        return friendship;
    }

    public void setFriendship(Friendship friendship) {
        this.friendship = friendship;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}
