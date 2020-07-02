package ru.skillbox.socialnetworkimpl.sn.controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PersonRequest;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.ProfileService;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/storage/")
public class StorageController {

    private String id;

    private String type;

    private final ProfileService profileService;

    public StorageController(ProfileService profileService)
    {
        this.profileService = profileService;
    }

    @PutMapping()
    public ResponseEntity<ResponsePlatformApi> putPathToUserImageIntoStorage(HttpServletRequest request,
                                                                             @RequestBody PersonRequest personRequest,
                                                                             @RequestParam MultipartFile file) throws IOException {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "drpvjtm2d",
                "api_key", "729424573911346",
                "api_secret", "E6ILMQoxBehko-yHpDRDl06gc_k"));

        Map params = ObjectUtils.asMap(
                "public_id", "userImages/" + file.getOriginalFilename(),
                "overwrite", true,
                "notification_url", "https://localhost:8080/api/v1/storage",
                "resource_type", "image"
        );

        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();

        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();

        Map uploadResult = cloudinary.uploader().upload(convFile, params);
        convFile.delete();
        String url = (String) uploadResult.get("url");

        personRequest.setPhoto(url);

        return profileService.editCurrentUser(request.getSession(), personRequest);
    }
}
