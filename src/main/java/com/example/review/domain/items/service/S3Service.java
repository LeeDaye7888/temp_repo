package com.example.review.domain.items.service;

import static com.example.review.global.exception.ErrorCode.*;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.review.global.exception.BusinessException;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public AmazonS3Client amazonS3Client() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
            .withRegion(region)
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .build();
    }

    public List<String> upload(List<MultipartFile> multipartFile) {
        List<String> imgUrlList = new ArrayList<>();

        if (multipartFile == null) {
            throw new BusinessException(REQUIRED_IMAGE);
        }

        // forEach 구문을 통해 multipartFile로 넘어온 파일들 하나씩 fileNameList에 추가
        for (MultipartFile file : multipartFile) {
            String fileName = createFileName(file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                s3Client.putObject(
                    new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                //업로드된 이미지의 url 생성하여 리스트에 추가
                String imgUrl = generateImgUrl(fileName);
                imgUrlList.add(imgUrl);
            } catch (IOException e) {
                throw new BusinessException(UPLOAD_ERROR_IMAGE);
            }
        }
        return imgUrlList;
    }

    //파일 이름으로 S3에 업로드된 이미지 URL 생성
    private String generateImgUrl(String filename) {
        return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + filename;
    }

    // 이미지파일명 중복 방지
    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    // 파일 유효성 검사
    private String getFileExtension(String fileName) {
        if (fileName.length() == 0) {
            throw new BusinessException(REQUIRED_IMAGE);
        }
        ArrayList<String> fileValidate = new ArrayList<>();
        fileValidate.add(".jpg");
        fileValidate.add(".jpeg");
        fileValidate.add(".png");
        fileValidate.add(".JPG");
        fileValidate.add(".JPEG");
        fileValidate.add(".PNG");
        String idxFileName = fileName.substring(fileName.lastIndexOf("."));
        if (!fileValidate.contains(idxFileName)) {
            throw new BusinessException(VALID_ERROR_IMAGE);
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

    // DeleteObject를 통해 S3 파일 삭제
    public void deleteFile(String fileName) {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, fileName);
        s3Client.deleteObject(deleteObjectRequest);
    }
}

