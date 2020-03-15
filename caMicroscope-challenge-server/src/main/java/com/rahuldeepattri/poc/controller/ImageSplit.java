package com.rahuldeepattri.poc.controller;

import com.rahuldeepattri.poc.model.SplitRGBResponse;
import com.rahuldeepattri.poc.service.RGBImageSplitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController()
@RequestMapping(value = "/api")
public class ImageSplit {

    private static final Logger logger = LoggerFactory.getLogger(ImageSplit.class);

    final RGBImageSplitter rgbImageSplitter;

    @Autowired
    public ImageSplit(RGBImageSplitter rgbImageSplitter) {
        this.rgbImageSplitter = rgbImageSplitter;
    }


    @RequestMapping(value = "/splitRGB", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> splitRGB(@RequestParam("image") MultipartFile image) {
        SplitRGBResponse split = null;
        try {
            split = rgbImageSplitter.split(image);
        } catch (IOException e) {
            logger.error("Failed to split", e);
            return new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(split, HttpStatus.OK);
    }


}