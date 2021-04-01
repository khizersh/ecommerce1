package com.test.utility;



import com.test.bean.category.ChildCategory;
import com.test.dto.childCategoryDto;
import com.test.repo.ChildAttributeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;


@Service
public class GlobalService {
    @Autowired
    private ChildAttributeRepo childAttributeRepo;

    public ResponseEntity getSuccessResponse(Object obj){

        Response res = new Response(1 , "Request successful" , obj);
        return ResponseEntity.ok()
                .header("statusCode", "1")
                .body(res);

    }

    public ResponseEntity getErrorResponse(String msg){

        Response res = new Response(0 , msg , "");
        return ResponseEntity.ok()
                .header("statusCode", "0")
                .body(res);

    }



    public childCategoryDto convertChildCategoryDto(ChildCategory child){

        childCategoryDto dto = new childCategoryDto();


        dto.setTitle(child.getCategoryName());
        dto.setActive(child.getActive());
        dto.setId(child.getId());
        dto.setParentCategoryId(child.getParentCategory().getId());
        dto.setParentCategoryTitle(child.getParentCategory().getCategoryName());


        return dto;

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
