package cz.muni.fi.services.implementations;

import cz.muni.fi.services.exceptions.ServiceDataAccessException;
import cz.muni.fi.services.interfaces.UserService;
import dao.entities.User;
import dao.interfaces.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

/**
 * @author Kostka on 25/04/2020.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void createUser(User user, String password) throws DataAccessException {
        try {
            String passwordHash = createHash(password);
            user.setPasswordHash(passwordHash);
            userDao.createUser(user);
        } catch (Throwable ex) {
            throw new ServiceDataAccessException("Could not create user.", ex);
        }
    }

    @Override
    public List<User> getAllUsers() throws DataAccessException {
        try {
            return userDao.getAllUsers();
        } catch (Throwable ex) {
            throw new ServiceDataAccessException("Could not get all users.", ex);
        }
    }

    @Override
    public User getUser(Long id) throws DataAccessException {
        try {
            return userDao.getUser(id);
        } catch (Throwable ex) {
            throw new ServiceDataAccessException("Could not get user.", ex);
        }
    }

    @Override
    public void updateUser(User user) throws DataAccessException {
        try {
            userDao.updateUser(user);
        } catch (Throwable ex) {
            throw new ServiceDataAccessException("Could not update user.", ex);
        }
    }

    @Override
    public void deleteUser(Long id) throws DataAccessException {
        try {
            userDao.deleteUser(id);
        } catch (Throwable ex) {
            throw new ServiceDataAccessException("Could not delete user.", ex);
        }
    }

    @Override
    public User getUserByEmail(String email) throws DataAccessException {
        try {
            return userDao.getUserByEmail(email);
        } catch (Throwable ex) {
            throw new ServiceDataAccessException("Could not get user by email.", ex);
        }
    }

    @Override
    public boolean isAdmin(Long id) throws DataAccessException {
        User user = getUser(id);
        if (user == null)
            throw new ServiceDataAccessException("User with the id doesn't exist.");
        return user.isAdmin();
    }

    @Override
    public boolean authenticate(User user, String password) throws DataAccessException {
        try {
            return passwordCheck(password, user.getPasswordHash());
        } catch (Throwable ex) {
            throw new ServiceDataAccessException("Could not authenticate the user.", ex);
        }
    }

    @Override
    public boolean changePassword(User user, String password, String newPassword) throws IllegalArgumentException {
        if (newPassword == null) {
            throw new IllegalArgumentException("New password cannot be null");
        }
        if (newPassword.isEmpty()) {
            throw new IllegalArgumentException("New password cannot be empty");
        }
        if (!passwordCheck(password, getUser(user.getId()).getPasswordHash())) {
            return false;
        }
        String newPasswordHash = createHash(newPassword);
        user.setPasswordHash(newPasswordHash);
        userDao.updateUser(user);
        return true;
    }

    /**
     * Checks password against user's password hash.
     * Used source: PA165 seminar project
     * @param password password to check
     * @param correctHash correct hash
     * @return true if password hash matches correct hash, false otherwise
     */
    private static boolean passwordCheck(String password, String correctHash) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        if (correctHash == null) {
            throw new IllegalArgumentException("Password hash is null");
        }

        String[] params = correctHash.split(":");
        int iterations = Integer.parseInt(params[0]);
        byte[] salt = fromHex(params[1]);
        byte[] hash = fromHex(params[2]);
        byte[] testHash = pbkdf2(password.toCharArray(), salt, iterations, hash.length);
        return slowEquals(hash, testHash);
    }

    /**
     * Converts hex to binary
     * Used source: PA165 seminar project
     * @param hex hexadecimal string
     * @return binary string
     */
    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }

    /**
     * Creates PBKDF2 hash of password.
     * Used source: PA165 seminar project
     * @param password the password
     * @param salt salt
     * @param iterations number of iterations of PBKDF2
     * @param bytes number of bytes
     * @return PBKDF2 hash of password
     */
    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Compares two byte arrays in length-constant time. This comparison method
     * is used so that password hashes cannot be extracted from an on-line
     * system using a timing attack and then attacked off-line.
     * Used source: PA165 seminar project
     * @param a the first byte array
     * @param b the second byte array
     * @return true if both byte arrays are the same, false if not
     */
    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++) {
            diff |= a[i] ^ b[i];
        }
        return diff == 0;
    }

    /**
     * Converts binary to hex
     * Used source: PA165 seminar project
     * @param array binary string
     * @return hexadecimal String
     */
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        return paddingLength > 0 ? String.format("%0" + paddingLength + "d", 0) + hex : hex;
    }

    /**
     * Creates a hash of a given password using PBKDF2 hashing
     * Used source: PA165 seminar project
     * @param password a given password
     * @return hash of a password
     */
    private static String createHash(String password) {
        if (password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        final int SALT_BYTE_SIZE = 24;
        final int HASH_BYTE_SIZE = 24;
        final int PBKDF2_ITERATIONS = 1000;

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);

        byte[] hash = pbkdf2(password.toCharArray(), salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
        return PBKDF2_ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
    }
}