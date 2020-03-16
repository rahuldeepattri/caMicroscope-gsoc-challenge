# caMicroscope GSOC Code Challenge

Technologies Used :- <br>
Frontend : Javascript and littlebit of Bootstrap <br>
Backend: Java-Spring Boot <br>
Docker to package everything <br>
<br>
Image Splitting is done at backend in java by looping through all pixels using ImageIO library which comes with JDK and has support for plugins.<br>
Image Merging is done at frontend using by looping through all pixels using canvas html element.
 
## Demo

Please go to http://ec2-13-126-128-14.ap-south-1.compute.amazonaws.com. <br>

Note that the swagger ui is available for local build and run only.


## Run Project Locally

Dependencies: docker & docker-compose<br>
No need to execute build, it is optional.<br>
To run execute ```sudo ./run.sh```  and Go to http://localhost or http://127.0.0.1.<br>
To see result from api use  ```curl -X POST "http://localhost/server/api/splitRGB" -H "accept: */*" -H "Content-Type: multipart/form-data"  -F "image=@test.jpeg;type=image/jpeg" |  json_pp```

test.jpeg is provided for testing.

## Build Project

Dependencies: JDK should be set up in environment. [(steps)](https://blog.knoldus.com/installing-latest-oracle-jdk-on-linux-ec2-instance-centos/)<br>
To build execute ```./build.sh```
