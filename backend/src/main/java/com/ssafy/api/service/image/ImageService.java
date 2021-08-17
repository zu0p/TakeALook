package com.ssafy.api.service.image;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    void saveImage(MultipartFile image, Long productId) throws IOException;
}
