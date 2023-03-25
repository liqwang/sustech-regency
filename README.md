# SUSTech-Regency Project

> Liquan Wang 12011619 <br>
> Junhong Zhou 12011801 <br>
> Yige Chen 12011625 <br>
> Haonan Hu 12012702 <br>
> Fan Yang 12010715

## Abstract

In this project, we are going to design and develop a website for a chain hotel. The consumer can broswe the hotels and rooms, then reserve one room and check in. And the user can also register as merchant to manage his or her hotels, check the profit and etc.
Generally, we choose to develop the frontend and backend seperately, which has lower coupling degree and is easy to refactor and make extension. More over, it enables us to deploy the whole application seperately. For technology selection, we choose **Vue** as frontend framework because of its rich, progressively integrated ecosystem and **SpringBoot** as backend framework because of its convenient autowired feature.
    

## Motivation

With the rapid development of economics and people's yearning for a better life, more and more people choose to go on vacation in the holiday. As a result, the hotel bussiness is growing and chain hotels appears. In order to attract customers, chain hotels need exquisite hotel reservation pages. That's why we choose to Project B
Projcet B focus on a web page development. We need to constrcut a web application satisfies the needs both for customer and merchant. So we can to divide the problem to three parts:
1. The functions both customers and merchants need: we need to finish the the functions such as sign up, login in, view basic information of hotels and rooms etc.
2. The functions merchants need: we need finish the functions merchants may use. For example, edit the information of hotel and rooms, view the orders and revenue from all hotels by visualize analitcal data collected from orders.
3. The functions customers need: we need to finish the functions customers require, varying from reserving a room, managing orders and so on.

To solve the requirement mentioned above, we decompose the system into frontend and backend and follow the typical SpringBoot structure. In the code implementation, we divided the system to entity layer, DAO layer, service layer and controller layer. In service layer, as we mentioned in last paragraph, we create publicService, Merchant Service, Customer Service and a HideService which offer the method used in the project but can not open to the users. To further optimize code and coordination between back end and front end, we create Paramter class and Info class in the code and use swagger to make front end more convient to use the API provided by backend.
    

## Feature Discription

### **signup** page

- contains the **password checking** program. An available password must contains uppercase and lowercase letters, digits and special characters
- contains the **e-mail verifying** function
- the page is **self-adaptation** while the size of window changing
  ![upload_b199910147c6ca6343714d58e4a3d822](https://user-images.githubusercontent.com/90035785/227697510-cb6b45fd-e320-46b6-a5a8-f49083b71777.png)

### **login** page

- e-mail or username login are **both** supposed 
- self-adaptation 
![upload_58b0112df5a5e882e03017aafd0b9ca2](https://user-images.githubusercontent.com/90035785/227698348-79abb200-03b5-4356-88c0-49ddc60a8df1.png)

### page to **modify password**

![upload_6d65a92c747952f244a12692b7e0bbc9](https://user-images.githubusercontent.com/90035785/227698373-17dafedb-1061-40d0-9fd7-d55c382af8c6.png)

### **main page**

- the page for users to **book a hotel**
- **searching hotels** by any specific province, city and region in China
- the **score** given by users is also visible 
![upload_768c17fbe2eba14e02e896c603af4d9f](https://user-images.githubusercontent.com/90035785/227698388-3ddf4583-9b3a-43e8-878f-2bac40246bc7.png)

### **merchant** page

- merchant can **manage** his/her **hotels** in this page, such as modifying the name of hotel
- display the **transaction amount** with a graph
- if he/she has more than one hotel, he/she can **switch different hotel** by clicking the left menu
![upload_0ab8e2bc83dea31a610970d3fddc807f](https://user-images.githubusercontent.com/90035785/227698405-05a3c3fd-81f6-4bf8-a05b-1598f19099dc.png)

### **floor graph**

- **switching to the floor** you want by clicking the blue buttons
- any **rooms** with ID can be **clicked** and displays the detailed information
![upload_8b71119d88b5c2f7b9029332864ede71](https://user-images.githubusercontent.com/90035785/227698422-45e9c9c6-3e55-4d48-883d-fae3ddffba53.png)

## Requirements

### Functional requirements

1. Support visitors to visit all room list and browse the corresponding detail page, the room detail page supports the current room plan display
2. Users/hotel administrators can register their accounts and login to the system
3. Users can collect and book specific hotel rooms, and this process supports online payment function
4. Users can contact the merchant and chat online in the hotel room details page
5. Users/hotel administrators can view and modify order information
6. Visualization of merchant statistics (order quantity, turnover, etc.)

### Non-functional requirements

1. Support 10k visitors to visit the room list at the same time
2. The latency of browsing room details page and booking room is less than 1s, too high latency will affect the user experience
3. Server-side memory is limited to less than 1G

## Design Document

### Database Structure

![upload_4ffeb0268fda8874b74b0fe8a3e11c7d](https://user-images.githubusercontent.com/90035785/227698429-e53ddaef-0496-42e9-a02a-b3759b9bd021.png)

### Class Diagram

![upload_c513a9de3f35133a6d25f249a2b5aa38](https://user-images.githubusercontent.com/90035785/227698433-02f26613-3830-42af-8d7f-806e1f8b7b56.png)

### Sequence Diagram

- Register
  ![upload_7c2332ce8e1f4781a088521bc0a08c8f](https://user-images.githubusercontent.com/90035785/227698443-14618c6c-035f-4979-974a-87a8e6f94462.png)
- Reserve room
  ![upload_5e5fdc09f57e76966c4127adbae69a37](https://user-images.githubusercontent.com/90035785/227698451-6cd4d608-36fe-459f-878c-a6f333614778.png)

## Feasibility

- The maven dependecy has many versions which need every developer to
  use the same version to implement code otherwise the code may be not compatible
- Though we have use many ways to make sure the code reliability, we are not professional programm tester so we can't make sure that the code can solve every illegal cases. If the user uses the web page in some special way that we did not expect, it may cause the website to run abnormally.
- In the project, we use some third party apis. Though it works now perfectly, we are not sure whether there's some potential bugs in it which will lead our program to breakdown.
  Some functions of front-end may not compatible with all browsers.

## Technologies

### Tools

- We use **Git** for version control, needless to say
- We also use **Swagger** to generate the API documentation dynamicly, which makes linkage between frontend and backend easily. Moreover, Swagger enables us to test the backend APIs more conveniently because we don't need to write the params and URLs manually.

### Frontend

- We use **Vue** as frontend framework, **Vite** as the build tool of frontend, **TypeScript** to write the code since it is more readable and maintainable than **JavaScript**.
- We use **Element-Plus** for component library.
- We use **tailwindcss** and **sass** to make it easier to control the style.
- We use **ECharts** to create some charts such as the chart of hotels' turnover.
- To interact with the backend server, we use **axios** to create an asynchronous request to the backend.
- We use **Vue Router** to control the redirect of the page in the frontend.
- Since **Vue** is known as for its component management, it is important to share some same data between different components and we use **Pinia** to share a state across components/pages.

### Backend

- Generally, we use **SpringBoot** as our backend overall framework because of its convenient autowire feature, annotation based extension and thriving ecology. With SpringBoot and **Maven** build tool, we can integrate other module easily.

- For data persistence, we use **MySQL** as our relational database because it has many features like open-source, muture, simple configuration, good stability and excellent performance.

- For persistent layer, we use **Mybatis** as ORM frameowrk. To simplify code, we also use **Mybatis-Plus** extension. Therefore, we don't need to write tedious xml files. However, Mybatis-Plus can't support tables join query, so we use mybatis-plus-join dependency additionally.

  ```xml
  <dependency>
      <groupId>com.github.yulichang</groupId>
      <artifactId>mybatis-plus-join</artifactId>
      <version>1.2.4</version>
  </dependency>
  ```

- To ensure security, we use **SpringSecurity** as our security framework for authentication(认证) and authorization(鉴权), which can implement the API's access control based on permissions. It also provides multiple authentication methods by using filter chains before entering API.

- As for high concurrency, use choose to use **Redis**  as cache. Redis is an open source, in-memory data store used by millions of developers. It is written in C and runs in memory, so it has high performance. According to the official document[^1], it can support million QPS in single server. Here is the flame figure(火焰图) while testing:
  <img src="开题Report.pictures/upload_39aee465522d3ddb44781ef9b9ff3ac1.png" style="zoom: 50%;" />
  Besides cache, we are also going to use Java's protogenesis `java.util.concurrent` package to implement high concurrent performance. The tool classes includes thread safe collections and thread pool builders to manage multi-threads.    

### Deploy

Generally to say, we are going to deploy the frontend and backend seperately. This has lots of advantages, for example:

1. If only the frontend file changes, the workflow can only re-deploy the frontend. Therefore, the backend service will not be affected at all.
2. Due to the frontend's static files(html,js,css,images) are deployed seperately, if the client only requests the static web pages, the backend service needn't to resolve the request, this can help to reduce the backend's load and increase the system's stability.

- We choose to use **GitHub Actions** to implement CI/CD by writing the workflow file in the `/.github/workflows` directory. After push to the repository in Git, the workflow will automately runs, package the source code, upload to the server and deploy.
- For frontend deploy, we use **Nginx** web server. Nginx provides many utility functions, like reverse proxy and load banlance. Besides, Nginx also has excellent performance in concurrency.
- We choose to deploy all the services in **Docker** containers. Using Docker enables us to make environment isolation and dynamic deployent. Beside the convenience, docker's virtual environment in containers is lighter, which can help us to save more server resources. Since we need to deploy several containers, we choose to use **Docker-Compose** to config the containers more conveniently.

## Reference

[^1]: https://redis.io/docs/reference/optimization/benchmarks
