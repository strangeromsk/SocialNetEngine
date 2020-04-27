package ru.skillbox.socialnetworkimpl.sn.services;

import org.springframework.http.ResponseEntity;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PersonEditBody;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.ProfileService;

import javax.servlet.http.HttpSession;

public class ProfileServiceImpl implements ProfileService {


    @Override
    public ResponseEntity<ResponsePlatformApi> getCurrentUser(HttpSession session) {
        return null;
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> editCurrentUser(HttpSession session, PersonEditBody personEditBody) {
        return null;
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> deleteCurrentUser(HttpSession session) {
        return null;
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> getUserById(HttpSession session, long id) {
        return null;
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> searchPerson(HttpSession session, String firstName,
                                                            String lastName, int ageFrom, int ageTo, int countryId,
                                                            int cityId, int offset, int itemPerPage) {
        return null;
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> blockUserById(HttpSession session, long id) {
        return null;
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> unblockUserById(HttpSession session, long id) {
        return null;
    }
}
