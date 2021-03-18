//package com.test.controller;
//
//import com.test.bean.ChildCategory;
//import com.test.bean.ImageModel;
//import com.test.repo.ChildCategoryRepo;
//import com.test.repo.ImageRepository;
//import com.test.service.ChildCategoryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.RequestEntity;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.http.ResponseEntity.BodyBuilder;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.zip.DataFormatException;
//import java.util.zip.Deflater;
//import java.util.zip.Inflater;
//
//@RestController
//@RequestMapping("api/image")
//public class ImageController {
//
//    @Autowired
//    ImageRepository imageRepository;
//
//    @Autowired
//    ChildCategoryRepo categoryRepo;
//
//    @GetMapping("")
//    public ResponseEntity getAll(){
//       List<ImageModel> list = imageRepository.findAll();
//
//      List<ImageModel> listToSend = new ArrayList<>();
//        for (ImageModel i: list){
//
//            ImageModel img = new ImageModel(i.getName(), i.getType(), decompressBytes(i.getPicByte()) );
//           img.setId(i.getId());
//            listToSend.add(img);
//        }
//       return ResponseEntity.ok(listToSend);
//    }
//
//    @PostMapping("/upload")
//    public ResponseEntity uploadImage(@RequestParam("imageFile") MultipartFile imageFile , @RequestParam("categoryId") Integer categoryId , @RequestParam("title") String title, @RequestParam("description") String description ) throws IOException {
//
//        ChildCategory cat = null;
//        boolean flag  = categoryRepo.existsById(categoryId);
//        if (!flag) {
//            return new ResponseEntity("Category Not Found!",HttpStatus.BAD_REQUEST);
//        }
//        if(imageFile == null){
//            return new ResponseEntity("Invalid Image",HttpStatus.BAD_REQUEST);
//        }
//        cat = categoryRepo.getOne(categoryId);
//        ImageModel img = new ImageModel(imageFile.getOriginalFilename(), imageFile.getContentType() ,compressBytes(imageFile.getBytes()) );
//        ImageModel responseImage =  imageRepository.save(img);
//        return  ResponseEntity.ok(responseImage);
//    }
//
//    @GetMapping("/get/{id}")
//    public ResponseEntity getImage(@PathVariable Integer id) throws IOException {
//
//        ImageModel retrievedImage = imageRepository.getOne(id);
//        if(retrievedImage == null){
//        return new ResponseEntity("Image not found!",HttpStatus.BAD_REQUEST);
//        }
//
//        ImageModel img = new ImageModel(retrievedImage.getName(), retrievedImage.getType(), decompressBytes(retrievedImage.getPicByte()));
//        return ResponseEntity.ok(img);
//
//    }
//
//    @GetMapping(path = { "/getByName/{imageName}" })
//    public  ResponseEntity getImageByName(@PathVariable("imageName") String imageName) throws IOException {
//
//        Optional<ImageModel> retrievedImage = imageRepository.findByName(imageName);
////        final Optional<ImageModel> retrievedImage = imageRepository.findByName(imageName);
//
//        ImageModel img = new ImageModel(retrievedImage.get().getName(), retrievedImage.get().getType(),decompressByteArray(retrievedImage.get().getPicByte()) );
//
//        return ResponseEntity.ok(img);
//
//    }
//
//    @PostMapping(path = { "/update" })
//    public  ResponseEntity updateImage(@RequestParam("id") Integer id ,  @RequestParam(value = "imageFile" , required = false) MultipartFile imageFile , @RequestParam("categoryId") Integer categoryId , @RequestParam("title") String title, @RequestParam("description") String description ) throws IOException {
//
//        if(!imageRepository.existsById(id)){
//            return new ResponseEntity("Image not found!", HttpStatus.BAD_REQUEST);
//        }
//          ImageModel imageDb = imageRepository.getOne(id);
//        if(imageFile != null){
//            System.out.println("in image");
//            imageDb.setPicByte(compressBytes(imageFile.getBytes()));
//            imageDb.setName(imageFile.getOriginalFilename());
//            imageDb.setType(imageFile.getContentType());
//        }
//        if(categoryId != 0){
//          ChildCategory  cat = categoryRepo.getOne(categoryId);
//          imageDb.setCategory(cat);
//        }
//        if(title != null){
//            imageDb.setTitle(title);
//        }
//        if(description != null){
//            imageDb.setDescription(description);
//        }
//        ImageModel savedImage =  imageRepository.save(imageDb);
//
//        return ResponseEntity.ok(savedImage);
//
//    }
//
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity deleteById(@PathVariable Integer id){
//        if(imageRepository.existsById(id)){
//            imageRepository.deleteById(id);
//            return new ResponseEntity("Delete successfully!",HttpStatus.OK);
//        }
//        return new ResponseEntity("Invalid Request!",HttpStatus.NOT_FOUND);
//    }
//
//    @GetMapping("/getByCategory/{id}")
//    public ResponseEntity getByCategoryId(@PathVariable Integer id){
//        List<ImageModel> list = new ArrayList<>();
//        ChildCategory cat = categoryRepo.getOne(id);
//        if (cat == null) {
//            return new ResponseEntity("Category not found!",HttpStatus.BAD_REQUEST);
//        }
//
//        imageRepository.findByCategory(cat).forEach(i -> System.out.println(i.getCategory().getCategoryName()));
//        for (ImageModel i: imageRepository.findByCategory(cat)){
//
//            ImageModel img = new ImageModel(i.getName(), i.getType(), decompressBytes(i.getPicByte()) );
//            list.add(img);
//        }
//
//        return ResponseEntity.ok(list);
//    }
//
//
//    public byte[] decompressByteArray(byte[] bytes){
//
//        ByteArrayOutputStream baos = null;
//        Inflater iflr = new Inflater();
//        iflr.setInput(bytes);
//        baos = new ByteArrayOutputStream();
//        byte[] tmp = new byte[4*1024];
//        try{
//            while(!iflr.finished()){
//                int size = iflr.inflate(tmp);
//                baos.write(tmp, 0, size);
//            }
//        } catch (Exception ex){
//
//        } finally {
//            try{
//                if(baos != null) baos.close();
//            } catch(Exception ex){}
//        }
//
//        return baos.toByteArray();
//    }
//    public static byte[] compressBytes(byte[] data) {
//
//        Deflater deflater = new Deflater();
//        deflater.setInput(data);
//        deflater.finish();
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
//        byte[] buffer = new byte[1024];
//
//        while (!deflater.finished()) {
//            int count = deflater.deflate(buffer);
//            outputStream.write(buffer, 0, count);
//        }
//        try {
//            outputStream.close();
//        } catch (IOException e) {
//        }
//        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
//        return outputStream.toByteArray();
//
//    }
//    public static byte[] decompressBytes(byte[] data) {
//
//        Inflater inflater = new Inflater();
//        inflater.setInput(data);
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
//        byte[] buffer = new byte[1024];
//        try {
//            while (!inflater.finished()) {
//                int count = inflater.inflate(buffer);
//                outputStream.write(buffer, 0, count);
//            }
//            outputStream.close();
//        } catch (IOException ioe) {
//            System.out.println("Error in catch");
//        } catch (DataFormatException e) {
//        }
//        return outputStream.toByteArray();
//    }
//}
