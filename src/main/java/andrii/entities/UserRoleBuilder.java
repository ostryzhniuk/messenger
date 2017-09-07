package andrii.entities;

public class UserRoleBuilder {

    private final UserRole userRole;

    public UserRoleBuilder() {
        this.userRole = new UserRole();
    }

    public void setDefaultAuthority(){
        userRole.setAuthority("USER");
    }

    public void setAuthority(String authority) {
        userRole.setAuthority(authority);
    }

    public void setUser(User user){
        userRole.setUser(user);
    }

    public UserRole getUserRole() {
        return userRole;
    }
}
