package com.rahuldeepattri.poc.service;

import com.rahuldeepattri.poc.model.SplitRGBResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface RGBImageSplitter {
    SplitRGBResponse split(MultipartFile image) throws IOException;
}
