package andrii.services;

import andrii.dao.UserDAO;
import andrii.dao.UserRoleDAO;
import andrii.dto.UserDTO;
import andrii.dto.AuthenticationDTO;
import andrii.dto.UserSignUpDTO;
import andrii.entities.User;
import andrii.entities.UserRole;
import andrii.entities.UserRoleBuilder;
import andrii.utils.ImageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserRoleDAO userRoleDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${resources.path}")
    private String resourcesPath;

    public AuthenticationDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            return new AuthenticationDTO(-1, authentication.getName(), authentication.getAuthorities());
        }

        return new AuthenticationDTO(
                getUserId(authentication.getName()),
                authentication.getName(),
                authentication.getAuthorities());
    }

    @Transactional
    public void save(UserSignUpDTO userDTO) {
        User user = userDTO.convertToEntity();

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        userDAO.save(user);
        userRoleDAO.save(createUserRole(user));
    }

    private UserRole createUserRole(User user){
        UserRoleBuilder userRoleBuilder = new UserRoleBuilder();
        userRoleBuilder.setUser(user);
        userRoleBuilder.setDefaultAuthority();
        return userRoleBuilder.getUserRole();
    }

    @Transactional
    public UserDTO getUserById(Integer userId, boolean loadPhoto) {
        UserDTO userDTO = UserDTO.convertToDTO(userDAO.getUser(userId));
        if (loadPhoto) {
            userDTO.setPhoto(loadPhoto(userDTO.getId()));
        }
        return userDTO;
    }

    @Transactional
    public UserDTO getUserByEmail(String email){
        return UserDTO.convertToDTO(userDAO.getUser(email));
    }

    @Transactional
    public Integer getUserId(String email){
        return getUserByEmail(email).getId();
    }

    @Transactional
    public List<UserDTO> getUsers() {
        List<UserDTO> userDTOList = convertToDTOList(userDAO.getObjects());
        userDTOList.forEach(userDTO -> userDTO.setPhoto(loadPhoto(userDTO.getId())));
        return userDTOList;
    }

    public List<UserDTO> convertToDTOList(List<User> userList) {
        return userList
                .stream()
                .map(user -> UserDTO.convertToDTO(user))
                .collect(Collectors.toList());
    }

    public String loadPhoto(Integer userId){
        String separator = FileSystems.getDefault().getSeparator();
        Path path = Paths.get(resourcesPath + "messenger" + separator + "images"
                + separator + "avatars" + separator + userId + ".jpg");
        return ImageHandler.loadEncodedImage(path);
    }

    private void savePhoto(String photoBASE64, Integer userId) {
        String separator = FileSystems.getDefault().getSeparator();
        Path path = Paths.get(resourcesPath + "messenger" + separator + "images"
                + separator + "avatars" + separator + userId + ".jpg");

        ImageHandler.save(ImageHandler.decodeBase64Image(photoBASE64), path);
    }

}
