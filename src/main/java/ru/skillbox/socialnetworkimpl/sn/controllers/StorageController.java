package ru.skillbox.socialnetworkimpl.sn.controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PersonRequest;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;
import ru.skillbox.socialnetworkimpl.sn.api.responses.StorageResponse;
import ru.skillbox.socialnetworkimpl.sn.services.AccountServiceImpl;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.ProfileService;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/storage")
public class StorageController {

    private final ProfileService profileService;
    private final AccountServiceImpl accountService;

    public StorageController(ProfileService profileService, AccountServiceImpl accountService){
        this.profileService = profileService;
        this.accountService = accountService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponsePlatformApi> putPathToUserImageIntoStorage(HttpServletRequest request,
                                                                             @RequestBody PersonRequest personRequest,
                                                                             @RequestParam String type,
                                                                             @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "drpvjtm2d",
                "api_key", "729424573911346",
                "api_secret", "E6ILMQoxBehko-yHpDRDl06gc_k"));

        Map params = ObjectUtils.asMap(
                "public_id", "userImages/" + file.getOriginalFilename(),
                "overwrite", true,
                "notification_url", "http://localhost:8080/api/v1/storage",
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

        StorageResponse storageResponse = new StorageResponse();
        storageResponse.setId("1255677876543");
        storageResponse.setOwnerId(accountService.getCurrentUser().getId());
        storageResponse.setFileName(file.getOriginalFilename());
        storageResponse.setRelativeFilePath(url);
        storageResponse.setRawFileURL(url);
        storageResponse.setFileFormat("JPG");
        storageResponse.setFileType("IMAGE");
        storageResponse.setCreatedAt(new Date());
        storageResponse.setBytes(file.getBytes());

        ResponsePlatformApi api = ResponsePlatformApi.builder()
                .error("Ok")
                .timestamp(new Date().getTime())
                .data(storageResponse).build();
        return new ResponseEntity<>(api, HttpStatus.OK);
    }
}
