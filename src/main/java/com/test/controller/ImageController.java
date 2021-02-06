package com.test.controller;

import com.test.bean.ChildCategory;
import com.test.bean.ImageModel;
import com.test.repo.ChildCategoryRepo;
import com.test.repo.ImageRepository;
import com.test.service.ChildCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity.BodyBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
@RequestMapping("api/image")
public class ImageController {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ChildCategoryRepo categoryRepo;

    @GetMapping("")
    public ResponseEntity getAll(){
       List<ImageModel> list = imageRepository.findAll();
       return ResponseEntity.ok(list);
    }

    @PostMapping("/upload")
    public ResponseEntity uploadImage(@RequestParam("imageFile") MultipartFile file , @RequestParam("categoryId") Integer categoryId) throws IOException {
        System.out.println("Original Image Byte Size - " + file.getBytes().length);
        ChildCategory cat = categoryRepo.getOne(categoryId);
        if (cat == null) {
            return new ResponseEntity("Category Not Found!",HttpStatus.BAD_REQUEST);
        }
        ImageModel img = new ImageModel(file.getOriginalFilename(), file.getContentType(),compressBytes(file.getBytes()) , cat);
        ImageModel responseImage =  imageRepository.save(img);
        return  ResponseEntity.ok(responseImage);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity getImage(@PathVariable Integer id) throws IOException {

        ImageModel retrievedImage = imageRepository.getOne(id);
//        ChildCategory cat = categoryRepo.getOne(categoryId);
//        if (cat == null) {
//            return new ResponseEntity("Category Not Found!",HttpStatus.BAD_REQUEST);
//        }
        if(retrievedImage == null){
        return new ResponseEntity("Image not found!",HttpStatus.BAD_REQUEST);
        }

        ImageModel img = new ImageModel(retrievedImage.getName(), retrievedImage.getType(), decompressBytes(retrievedImage.getPicByte()) , retrievedImage.getCategory());
        return ResponseEntity.ok(img);

    }

    @GetMapping(path = { "/getByName/{imageName}" })
    public  ResponseEntity getImageByName(@PathVariable("imageName") String imageName) throws IOException {

        Optional<ImageModel> retrievedImage = imageRepository.findByName(imageName);
//        final Optional<ImageModel> retrievedImage = imageRepository.findByName(imageName);

        ImageModel img = new ImageModel(retrievedImage.get().getName(), retrievedImage.get().getType(),
                decompressByteArray(retrievedImage.get().getPicByte()) ,retrievedImage.get().getCategory());

        return ResponseEntity.ok(img);

    }

    @GetMapping("/getByCategory/{id}")
    public ResponseEntity getByCategoryId(@PathVariable Integer id){
        List<ImageModel> list = new ArrayList<>();
        ChildCategory cat = categoryRepo.getOne(id);
        if (cat == null) {
            return new ResponseEntity("Category not found!",HttpStatus.BAD_REQUEST);
        }

        imageRepository.findByCategory(cat).forEach(i -> System.out.println(i.getCategory().getCategoryName()));
        for (ImageModel i: imageRepository.findByCategory(cat)){

            ImageModel img = new ImageModel(i.getName(), i.getType(), decompressBytes(i.getPicByte()) , i.getCategory());
            list.add(img);
        }

        return ResponseEntity.ok(list);
    }


    public byte[] decompressByteArray(byte[] bytes){

        ByteArrayOutputStream baos = null;
        Inflater iflr = new Inflater();
        iflr.setInput(bytes);
        baos = new ByteArrayOutputStream();
        byte[] tmp = new byte[4*1024];
        try{
            while(!iflr.finished()){
                int size = iflr.inflate(tmp);
                baos.write(tmp, 0, size);
            }
        } catch (Exception ex){

        } finally {
            try{
                if(baos != null) baos.close();
            } catch(Exception ex){}
        }

        return baos.toByteArray();
    }
    public static byte[] compressBytes(byte[] data) {

        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];

        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();

    }
    public static byte[] decompressBytes(byte[] data) {

        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
            System.out.println("Error in catch");
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }
}
