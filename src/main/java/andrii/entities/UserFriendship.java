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

    public enum Status {
        FRIENDS, SUBSCRIBER, NOT_REVIEWED, REJECTED
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    public static class UserFriendshipBuilder {

        private Friendship friendship;
        private User user;
        private Status status;

        public UserFriendshipBuilder(Friendship friendship, User user) {
            this.friendship = friendship;
            this.user = user;
        }

        public UserFriendship buildSubscriber() {
            status = Status.SUBSCRIBER;
            return new UserFriendship(this);
        }

        public UserFriendship buildNotReviewed() {
            status = Status.NOT_REVIEWED;
            return new UserFriendship(this);
        }
    }

    public UserFriendship() {
    }

    public UserFriendship(UserFriendshipBuilder userFriendshipBuilder) {
        this.friendship = userFriendshipBuilder.friendship;
        this.user = userFriendshipBuilder.user;
        this.status = userFriendshipBuilder.status;
    }

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
