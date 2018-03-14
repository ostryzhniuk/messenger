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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

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

    public Integer getCurrentUserId(){
        return getCurrentUser().getId();
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
    public User getUser(Integer userId) {
        return userDAO.get(userId);
    }

    @Transactional
    public UserDTO getUser(Integer userId, boolean loadPhoto) {
        UserDTO userDTO = UserDTO.convertToDTO(getUser(userId));
        if (loadPhoto) {
            userDTO.setPhoto(loadPhoto(userDTO.getId()));
        }
        return userDTO;
    }

    @Transactional
    public UserDTO getUser(String email){
        return UserDTO.convertToDTO(userDAO.get(email));
    }

    @Transactional
    public Integer getUserId(String email){
        return getUser(email).getId();
    }

    @Transactional
    public List<UserDTO> search(String parameter) {

        Set<User> userSet = new HashSet<>();

        for (String substring : parameter.split(" ")) {
            userSet.addAll(userDAO.search(substring));
        }

        List<UserDTO> userDTOList = convertToDTOList(userSet);
        userDTOList.forEach(userDTO -> userDTO.setPhoto(loadPhoto(userDTO.getId())));

        return userDTOList;
    }

    @Transactional
    public UserDTO getChatParticipant(Integer chatId, String userEmail, boolean loadPhoto) {
        UserDTO userDTO = UserDTO.convertToDTO(
                userDAO.getChatParticipant(
                        chatId,
                        getUserId(userEmail)));
        if (loadPhoto) {
            userDTO.setPhoto(loadPhoto(userDTO.getId()));
        }
        return userDTO;
    }

    @Transactional
    public void update(UserDTO userDTO) {
        User user = buildUser(userDTO);
        userDAO.update(user);
        savePhoto(userDTO.getPhoto(), user.getId());
    }

    @Transactional
    public User buildUser(UserDTO userDTO) {
        User user = userDAO.get(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        return user;
    }

    public List<UserDTO> convertToDTOList(Collection<User> users) {
        return users
                .stream()
                .map(user -> UserDTO.convertToDTO(user))
                .collect(Collectors.toList());
    }

    public String loadPhoto(Integer userId){
        String separator = FileSystems.getDefault().getSeparator();
        Path path = Paths.get(resourcesPath + "messenger" + separator + "images"
                + separator + "avatars" + separator + userId + ".jpg");
        try {
            return ImageHandler.loadEncodedImage(path);
        } catch (FileNotFoundException e) {
            try {
                return ImageHandler.loadEncodedImage(getPathToDefaultPhoto());
            } catch (FileNotFoundException e1) {
                LOGGER.error("Photo is not found", e, e1);
                return null;
            }
        }
    }

    private void savePhoto(String photoBASE64, Integer userId) {
        String separator = FileSystems.getDefault().getSeparator();
        Path path = Paths.get(resourcesPath + "messenger" + separator + "images"
                + separator + "avatars" + separator + userId + ".jpg");

        ImageHandler.save(ImageHandler.decodeBase64Image(photoBASE64), path);
    }

    private Path getPathToDefaultPhoto() throws FileNotFoundException {
        String separator = FileSystems.getDefault().getSeparator();
        URL noPhotoURL = getClass().getClassLoader().getResource("image" + separator + "no_photo.jpg");

        if (noPhotoURL == null) {
            throw new FileNotFoundException("Default photo is not found");
        } else {
            return Paths.get(noPhotoURL.getPath());
        }
    }

}
