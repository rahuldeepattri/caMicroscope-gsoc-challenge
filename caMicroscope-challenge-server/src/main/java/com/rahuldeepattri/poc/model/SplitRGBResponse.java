package com.rahuldeepattri.poc.model;

import lombok.Data;

import java.net.URL;

@Data
public class SplitRGBResponse {
    URL red;
    URL green;
    URL blue;
}
