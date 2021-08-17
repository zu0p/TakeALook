package com.ssafy.api.service.image;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

import static org.kurento.jsonrpc.client.JsonRpcClient.log;

@Service
public class ImageServiceImpl implements ImageService{
    @Override
    public void saveImage(MultipartFile image, Long productId) throws IOException {
        String imageFileName = productId +".jpg";
        String absolutePath = new File("").getAbsolutePath();
        String changePath = absolutePath.replace("backend","frontend");
        String path = changePath + "/src/assets/pimages/";
        log.info("file saved {} ", path+imageFileName);

        File file = new File(path+imageFileName);
        if( file.exists() ){
            file.delete();
            file = new File(path+imageFileName);
        }

        log.info("file name {} ",file.toString());
        file.getParentFile().mkdirs();
        image.transferTo(file);
    }
}
